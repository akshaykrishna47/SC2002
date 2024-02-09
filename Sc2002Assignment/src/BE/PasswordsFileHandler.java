package BE;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles operations related to passwords, such as saving passwords to a file and setting passwords from a passwords file.
 */
public class PasswordsFileHandler {
    private String fileName;
    private ArrayList<User> usersData;

    /**
     * Constructs a PasswordsFileHandler with a given file name and user data.
     *
     * @param fileName   The name of the file to handle passwords.
     * @param usersData  The list of users containing password information.
     */
    public PasswordsFileHandler(String fileName, ArrayList<User> usersData) {
        this.fileName = fileName;
        this.usersData = usersData;
    }

    /**
     * Saves passwords to a file.
     */
    public void SavePasswordsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (User user : usersData) {
                writer.write("UserID: " + user.getNtuNetworkId() + "\n");
                writer.write("Password: " + user.getPassword() + "\n");
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets passwords from a passwords file.
     *
     * @param passwordFile The file containing user IDs and passwords.
     * @throws FileNotFoundException If the passwords file is not found.
     */
    public void setPasswordsFromPasswordsFile(String passwordFile) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(passwordFile))) {
            while (scanner.hasNextLine()) {
                String UID = scanner.nextLine();
                String pass = scanner.nextLine();
                scanner.nextLine(); // Read an extra line to move to the next entry

                // Extract UserID and password from read lines
                String UserID = UID.replace("UserID: ", "");
                String password = pass.replace("Password: ", "");

                // Find the corresponding user and set the password
                for (User u : usersData) {
                    if (u.getNtuNetworkId().equals(UserID)) {
                        u.setPassword(password);
                        break; // Stop searching once the user is found and password is set
                    }
                }
            }
        }
    }
}
