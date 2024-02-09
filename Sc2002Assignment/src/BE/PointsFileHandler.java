package BE;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Handles operations related to points data, such as saving points data to a file.
 */
public class PointsFileHandler {
    private String fileName;
    private ArrayList<Student> comMembers;

    /**
     * Constructs a PointsFileHandler with a given file name and a list of committee members.
     *
     * @param fileName    The name of the file to handle points data.
     * @param comMembers  The list of committee members containing points-related information.
     */
    public PointsFileHandler(String fileName, ArrayList<Student> comMembers) {
        this.fileName = fileName;
        this.comMembers = comMembers;
    }

    /**
     * Saves points data to a file.
     * The file contains information about the total points, number of replies, number of suggestions,
     * and number of approved suggestions for each committee member.
     */
    public void savePointsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Student s : comMembers) {
                // Writing points-related information for each student to the file
                writer.write("UserID: " + s.getNtuNetworkId() + "\n");
                writer.write("Total Points: " + (s.getNumberOfReplies() + s.NumberOfSuggestions() + s.getnumberofApprovedSuggestions()) + "\n");
                writer.write("Number of Replies: " + s.getNumberOfReplies() + "\n");
                writer.write("Number of suggestions: " + s.NumberOfSuggestions() + "\n");
                writer.write("Number of approved suggestions: " + s.getnumberofApprovedSuggestions() + "\n");
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
