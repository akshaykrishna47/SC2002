package BE;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CampFileHandler class handles file operations for camps and user data.
 */
public class CampFileHandler {
    /**
     * ArrayList containing camp objects.
     */
    private static ArrayList<Camp> camps;

    /**
     * ArrayList containing camp names.
     */
    private static ArrayList<String> names;

    /**
     * ArrayList containing faculties' information.
     */
    private static ArrayList<String> faculties;

    /**
     * ArrayList containing user IDs.
     */
    private static ArrayList<String> userIDs;

    /**
     * ArrayList containing user objects.
     */
    private static ArrayList<User> usersData;
	
    /**
     * Saves camp data to a text file with detailed information.
     *
     * @param camps    List of camps to be saved.
     * @param filePath Path to the file where the camp data will be saved.
     */
	public static void saveCampsToTextFile_2(ArrayList<Camp> camps, String filePath) {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
	        for (Camp camp : camps) {
	        	writer.write("Visibility: " + camp.isVisible() + "\n");
	            writer.write("Camp Name: " + camp.getCampName() + "\n");
	            writer.write("Dates: " + String.join(",", camp.getDates()) + "\n");
	            writer.write("Registration Closing Date: " + camp.getRegistrationClosingDate() + "\n");
	            writer.write("User Group: " + camp.getUserGroup() + "\n");
	            writer.write("Location: " + camp.getLocation() + "\n");
	            writer.write("Total Slots: " + camp.getTotalSlots() + "\n");
	            writer.write("Camp Committee Slots: " + camp.getTotalCampCommitteeSlots()+ "\n");
	            writer.write("Description: " + camp.getDescription() + "\n");
	            writer.write("Staff in Charge: " + camp.getStaffInCharge().getNtuNetworkId() + "\n");
	            
           	    writer.write("Camp Members: " + camp.getCampCommitteeMembers().toString()+"\n");
	            writer.write("Camp Attendees: " + camp.getCampAttendees().toString()+"\n");
            
	            writer.write("Available Camp Member Slots: " + camp.getAvailableSlots() +"\n");
	            writer.write("Available Camp Committee Slots: " + camp.getAvailableSlotsForCommitteeMember() +"\n");
	            writer.write("Withdrawn Attendees: " + camp.getWithdrawnAttendees() + "\n");	            
	            writer.write("\n"); // Add a separator between camps
	        }
	    } catch (IOException e) {
	        System.err.println("Error saving camp data to TXT: " + e.getMessage());
	    }
	}
    
	/**
	 * Loads camp data from a text file, populating camp information including attendees, committee members, and withdrawn attendees.
	 *
	 * @param filePath               Path to the file containing camp data.
	 * @param usersData              List of all users' data including students and staff.
	 * @param CampsFilePathWithMembers Path to the file that contains camp information with member details.
	 * @return ArrayList of camps loaded from the file.
	 * @throws FileNotFoundException If the file to be loaded is not found.
	 */
	public static  ArrayList<Camp>loadCampsFromFile(String filePath,ArrayList<User> usersData,String CampsFilePathWithMembers) throws FileNotFoundException {
    	    try {
    	        Scanner s = new Scanner(new File(CampsFilePathWithMembers));
    	        ArrayList<Camp> camps = new ArrayList<>();
    	        while (s.hasNext()) {
    	        	String visibility = s.nextLine().replace("Visibility: ", "");
    	        	String campName = s.nextLine().replace("Camp Name: ", "");
    	        	ArrayList<String> dates = new ArrayList<>(List.of(s.nextLine().replace("Dates: ", "").split(",")));
    	            String registrationClosingDate = s.nextLine().replace("Registration Closing Date: ", "");
    	            String userGroup = s.nextLine().replace("User Group: ", "");
    	            String location = s.nextLine().replace("Location: ", "");
    	            String totalSlotsLine = s.nextLine().replace("Total Slots: ", "");
    	            String campCommitteeSlotsLine = s.nextLine().replace("Camp Committee Slots: ", "");
    	            int totalSlots = Integer.parseInt(totalSlotsLine);
    	            int campCommitteeSlots = Integer.parseInt(campCommitteeSlotsLine);
    	            String description = s.nextLine().replace("Description: ", "");
    	            String staffInCharge = s.nextLine().replace("Staff in Charge: ", "");
    	            String CM = s.nextLine().replace("Committee Members: ", "");
    	            String CA = s.nextLine().replace("Committee Attendees: ", "");    	            
    	            
    	            String AvailableSlots_CA = s.nextLine().replace("Available Camp Member Slots: ","");
    	            String AvailableSlots_CM = s.nextLine().replace("Available Camp Committee Slots: ","");
    	            String WithdrawnCA = s.nextLine().replace("Withdrawn Attendees: ", "");
    	            int Attendee_slots = Integer.parseInt(AvailableSlots_CA);
    	            int Committee_slots = Integer.parseInt(AvailableSlots_CM);
    	          
    	            s.nextLine();
    	            
    	            for (User user : usersData) {
    	                if (user instanceof Staff && user.getNtuNetworkId().equals(staffInCharge)) {
    	                    Staff staff = (Staff) user; // Cast the user to Staff
    	                    
    	                    Camp camp = staff.createCamp(campName, dates, registrationClosingDate, userGroup, location, totalSlots, description, campCommitteeSlots,Boolean.parseBoolean(visibility),Committee_slots,Attendee_slots);
    	                    
    	                    // Add camp attendees to the camp
    	                    List<String> CA_IDs = new ArrayList<>();
    	                    List<String> CM_IDs = new ArrayList<>();
    	                    List<String> WCA_IDs = new ArrayList<>();//withdrawn camp attendee IDs
    	                    for (String campAttendee : CA.split(",")) {
    	                        String CA_ID = extractStudentID(campAttendee);
    	                        CA_IDs.add(CA_ID);
    	                    }
    	                    for (String campMem : CM.split(",")) {
    	                        String CM_ID = extractStudentID(campMem);
    	                        CM_IDs.add(CM_ID);
    	                    }
    	                    for (String wca : WithdrawnCA.split(","))
    	                    {
    	                    	 String WCA_ID = extractStudentID(wca);
    	                    	 WCA_IDs.add(WCA_ID);
    	                    }

    	                    // Print the array of Student IDs
    	                    for(String withdrawnID:WCA_IDs)
    	                    {
    	                    	for (User user1 : usersData) {
    	                    		
    	                    		if (user1 instanceof Student && user1.getNtuNetworkId().equals(withdrawnID))
    	                    		{
    	                    			Student attendee = (Student) user1; // Cast to Student
    	                                camp.addWithdrawnAttendee(attendee);
    	                    		}
    	                    		
    	                    	}
    	                    }
    	                   
    	                    for (String MemberID : CM_IDs) {
    	                    	for (User user1 : usersData) {
    	                    	    if (user1 instanceof Student && user1.getNtuNetworkId().equals(MemberID)) 
    	                    	    {
    	                    	        Student mem = (Student) user1; 
    	                    	        camp.addCampCommitteeMember(mem);
    	                    	        break; 
    	                    	    }
    	                    	}

    	                    }
    	                    
    	                    for (String AttendeeID : CA_IDs) {
    	                        for (User user1 : usersData) {
    	                            if (user1 instanceof Student && user1.getNtuNetworkId().equals(AttendeeID))
    	                            {
    	                                Student attendee = (Student) user1; // Cast to Student
    	                                camp.addCampAttendee(attendee);
    	                                break;
    	                            }
    	                        }
    	                    }
    	                   

    	                    camps.add(camp);
    	                }
    	            }
    	        }
    	        
    	        s.close(); 
    	        return camps;
    	    } 
    	    catch (FileNotFoundException e) 
    	    {
    	        System.err.println("Error loading camp data from file: " + e.getMessage());
    	    }
			return camps;
    }
    
	/**
	 * Extracts the Student ID from a string input.
	 *
	 * @param input The input string containing Student ID information.
	 * @return The extracted Student ID.
	 */
    public static String extractStudentID(String input) {
        String[] parts = input.split("Student ID: ");

        if (parts.length > 1) {
            String studentID = parts[1].trim().replace("]", "");
           
            return studentID;
        } else {
            return "Student ID not found";
        }
    }
   
    /**
     * Searches for a User object by name within an ArrayList of Users.
     *
     * @param usersData The ArrayList of User objects to search within.
     * @param name      The name to search for.
     * @return The User object if found, otherwise returns null.
     */
    public static User getObj(ArrayList<User> usersData,String name)
    {
    	for (User user:usersData)
        {
        	if(user instanceof Student && user.getName().equalsIgnoreCase(name))
        	{
        		return user;
        	}
        }
    	return null;
    }
}