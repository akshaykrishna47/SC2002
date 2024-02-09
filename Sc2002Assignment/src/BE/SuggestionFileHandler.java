package BE;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Handles saving and loading suggestions to and from a file.
 */
public class SuggestionFileHandler {
    /**
     * The file name associated with the suggestion file.
     */
    private String fileName;

    /**
     * The list of users' data.
     */
    private ArrayList<User> usersData;

    /**
     * Constructs a SuggestionFileHandler object with a file name and user data.
     *
     * @param fileName   The file name to handle suggestions.
     * @param usersData  The list of users' data.
     */
    public SuggestionFileHandler(String fileName, ArrayList<User> usersData) {
        this.fileName = fileName;
        this.usersData = usersData;
    }

    /**
     * Saves a list of suggestions to a file.
     *
     * @param suggestions The list of suggestions to save.
     */
    public void saveSuggestionsToFile(List<Suggestion> suggestions) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Suggestion suggestion : suggestions) {
                writer.write("Sender: " + suggestion.getSender().getNtuNetworkId() + "\n");
                writer.write("Message: " + suggestion.getMessage() + "\n");
                writer.write("Camp: " + suggestion.getCamp() + "\n");
                writer.write("Status: " + suggestion.getStatus() + "\n");
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads suggestions from a file and returns them as a list.
     *
     * @return The list of loaded suggestions.
     * @throws FileNotFoundException If the file is not found.
     */
    public ArrayList<Suggestion> loadSuggestionFromFile() throws FileNotFoundException {
        ArrayList<Suggestion> suggestions = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                String senderIdLine = scanner.nextLine();
                String messageLine = scanner.nextLine();
                String campLine = scanner.nextLine();
                String approvedLine = scanner.nextLine();
                scanner.nextLine(); // Skip the empty line

                String senderId = senderIdLine.replace("Sender: ", "");
                String message = messageLine.replace("Message: ", "");
                String camp = campLine.replace("Camp: ", "");
                String approved = approvedLine.replace("Approved: ", "");

                Student sender = findStudentByUsername(usersData, senderId);

                int colonIndex = approved.indexOf(":");

                String statusValue = approved.substring(colonIndex + 2);
                SuggestStatus stat = SuggestStatus.valueOf(statusValue);

                Suggestion suggestion = new Suggestion(sender, message, camp, stat);
                suggestion.setStatus(stat);
                suggestions.add(suggestion);
            }
        } catch (FileNotFoundException e) {
            throw e;
        }

        return suggestions;
    }

    /**
     * Finds a student based on the username.
     *
     * @param usersData The list of users' data.
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
