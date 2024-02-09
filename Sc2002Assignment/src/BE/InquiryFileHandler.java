package BE;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

/**
 * Handles reading from and writing to an Inquiry file, managing Inquiry objects.
 */
public class InquiryFileHandler {
    
	/**
     * The name of the file associated with this InquiryFileHandler.
     */
    private String fileName;

    /**
     * The list of users' data used to match inquiries to users.
     */
    private ArrayList<User> usersData;

    /**
     * Constructs an InquiryFileHandler object with a filename and user data.
     *
     * @param fileName   The name of the file to handle.
     * @param usersData  The list of users' data used to match inquiries to users.
     */
    public InquiryFileHandler(String fileName, ArrayList<User> usersData) {
        this.fileName = fileName;
        this.usersData = usersData;
    }

    /**
     * Saves a list of inquiries to the file specified.
     *
     * @param inquiries The list of inquiries to save to the file.
     */
    public void saveInquiriesToFile(List<Inquiry> inquiries) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Inquiry inquiry : inquiries) {
                writer.write("Sender: " + inquiry.getSender().getNtuNetworkId() + "\n");
                writer.write("Message: " + inquiry.getMessage() + "\n");
                writer.write("Camp: " + inquiry.getCamp() + "\n");
                writer.write("Reply: " + inquiry.getReply() + "\n");
                writer.write("Replier: " + inquiry.getReplierID() + "\n");
                writer.write("\n"); // Add a separator between inquiries
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads inquiries from the file and returns them as an ArrayList.
     *
     * @return An ArrayList containing the loaded inquiries.
     * @throws FileNotFoundException If the file to load from is not found.
     */
    public ArrayList<Inquiry> loadInquiriesFromFile() throws FileNotFoundException {
        ArrayList<Inquiry> inquiries = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                String senderIdLine = scanner.nextLine();
                String messageLine = scanner.nextLine();
                String campLine = scanner.nextLine();
                String replyLine = scanner.nextLine();
                String replierNameLine = scanner.nextLine();
                scanner.nextLine(); // Skip the empty line

                // Extract data from lines and create an Inquiry object
                String senderId = senderIdLine.replace("Sender: ", "");
                String message = messageLine.replace("Message: ", "");
                String camp = campLine.replace("Camp: ", "");
                String reply = replyLine.replace("Reply: ", "");
                String replierID = replierNameLine.replace("Replier: ", "");

                // Find the sender Student object based on senderId
                Student sender = findStudentByUsername(usersData, senderId);
                Inquiry inquiry = new Inquiry(sender, message, camp);
                inquiry.setReply(reply, replierID);
                inquiries.add(inquiry);
            }
        } catch (FileNotFoundException e) {
            throw e;
        }
        return inquiries;
    }

    /**
     * Finds a Student object based on the username from the provided user data list.
     *
     * @param usersData The list of users to search for the Student object.
     * @param username  The username to search for.
     * @return The found Student object, or null if not found.
     */
    private Student findStudentByUsername(ArrayList<User> usersData, String username) {
        for (User user : usersData) {
            if (user instanceof Student && user.getNtuNetworkId().equals(username)) {
                return (Student) user; // If found, return the Student object
            }
        }
        return null; // If not found, return null
    }
          
}
