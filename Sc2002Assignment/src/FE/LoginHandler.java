package FE;
import BE.File_Handler;

/**
 * The LoginHandler class manages login-related functionality.
 */
public class LoginHandler {

    /**
     * The entry point of the application.
     * 
     * @param args The command line arguments.
     */
    public static void main(String[] args) {

        // The file path of the student file
        String studentFilePath = "C:/Users/aksha/eclipse-workspace/Datasc2002/student";

        // Print the contents of the text file at the specified path
        File_Handler.printTextFile(studentFilePath);
    }
}
