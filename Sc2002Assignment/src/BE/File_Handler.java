package BE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Scanner;

/**
 * Represents a Committee Member, extending the functionality of a Student.
 * Committee members are individuals participating in specific contexts as part of a committee.
 */
public class File_Handler {
    
	/**
     * Constructs a Committee Member with the provided details.
     *
     * @param name         The name of the committee member.
     * @param ntuNetworkId The NTU network ID of the committee member.
     * @param faculty      The faculty of the committee member.
     */
	public static void printTextFile(String filePath) {
         try {
            FileReader fileReader = new FileReader(filePath);
            
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
    }
    
	/**
     * Extracts file content into separate ArrayLists.
     *
     * @param filePath The path of the file to extract data from.
     * @return An ArrayList containing separate ArrayLists for names, emails, faculties, and userIDs.
     * @throws FileNotFoundException If the specified file path is not found.
     */
    public static ArrayList<ArrayList<String>>PutFileContentIntoArray(String filePath) throws FileNotFoundException {
        Scanner s = new Scanner(new File(filePath));
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> emails = new ArrayList<>();
        ArrayList<String> faculties = new ArrayList<>();
        ArrayList<String> userIDs= new ArrayList<>();
        
        // Read the file line by line and extract relevant information
        while (s.hasNextLine()) {
            String line = s.nextLine();
            String[] parts = line.split("\t");
            if (parts.length >= 3) {
                String name = parts[0].trim();
                String email = parts[1].trim();
                String faculty = parts[2].trim();

                names.add(name);
                emails.add(email);
                faculties.add(faculty);
                
                // Extract userID from email and add to userIDs ArrayList
                String[] emailParts = email.split("@");
                String userID = emailParts[0];
                userIDs.add(userID);
            }
        }
        ArrayList<ArrayList<String>> allArrays = new ArrayList<>();
        allArrays.add(names);
        allArrays.add(emails);
        allArrays.add(faculties);
        allArrays.add(userIDs);
        
        return allArrays;

    }
    

}
