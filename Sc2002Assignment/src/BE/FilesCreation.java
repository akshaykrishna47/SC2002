package BE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Utility class to handle file operations like copying files.
 */
public class FilesCreation {
	
	/**
     * Prints a message indicating file reading.
     */
	public static void Logger()
	{
		// Print a message indicating that the file is being read
		System.out.println("reading file....");
	}
	
	/**
     * Copies a file from a source path to a destination path.
     *
     * @param sourcePath      The path of the source file.
     * @param destinationPath The path of the destination file.
     * @throws IOException If an I/O error occurs during file copying.
     */
	public static void copyFile(String sourcePath, String destinationPath) throws IOException {

		// Define the source and destination paths
		Path source = Path.of(sourcePath);
        Path destination = Path.of(destinationPath);

        // Create the directories if they don't exist
        Files.createDirectories(destination.getParent());

        // Copy the file to the destination
        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
    }
	
}

