package BE;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//creates the data storage when app starts so can check login credentials
//delete camps from the server
//put it inside MainPage,so when run menu,it will generate out data to compare the userID and password
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the server managing various data and functionalities.
 */
public class Server {
	
	//------------------------------File Paths--------------------------------------------
	/**
	 * The file path for the student data file.
     * It is initialized based on the provided filepath during the construction of the Server object.
	 */
	public static String StudentFilePath;

	/**
	 * The file path for the staff data file.
     * It is initialized based on the provided filepath during the construction of the Server object.
	 */
	public static String StaffFilePath;

	/**
	 * The file path for the camp data file.
     * It is initialized based on the provided filepath during the construction of the Server object.
	 */
	public static String CampsFilePath;

	/**
	 * The file path for the camp data with members file.
     * It is initialized based on the provided filepath during the construction of the Server object.
	 */
	public static String CampsFilePathWithMembers;

	/**
	 * The file path for the inquiries file.
     * It is initialized based on the provided filepath during the construction of the Server object.
	 */
	public static String InquirysFilePath;

	/**
	 * The file path for the suggestions file.
     * It is initialized based on the provided filepath during the construction of the Server object.
	 */
	public static String SuggestionFilePath;

	/**
	 * The file path for the report files.
     * It is initialized based on the provided filepath during the construction of the Server object.
	 */
	public static String filePathToReport;

	/**
	 * The file path for the performance report files.
     * It is initialized based on the provided filepath during the construction of the Server object.
	 */
	public static String filePathToCMReport;

	/**
	 * The file path for the passwords.
     * It is initialized based on the provided filepath during the construction of the Server object.
	 */
	public static String filePathToPasswords;

	/**
	 * The file path for the points.
     * It is initialized based on the provided filepath during the construction of the Server object.
	 */
	public static String filePathToPoints;

	/**
	 * The file path for the inquiries directory.
     * It is initialized based on the provided filepath during the construction of the Server object.
	 */
	public static String filePathToInquiry;

	//------------------------------------------------------------------------------------------
	private static ArrayList<String> staff_names;
    private static ArrayList<String> faculties;
    private static ArrayList<String> userIDs;
    private static ArrayList<User> usersData;
    private static ArrayList<Camp> camps; // Maintain a list of camps
    private static ArrayList<Inquiry> Inquiries;
	private static InquiryManager inquiryManager;
    private static ArrayList<Inquiry> inquiries;
    private static ArrayList<Suggestion> Suggestions;
	private static SuggestionManager suggestionManager;
    private static ArrayList<Suggestion> suggestions;
	private static ArrayList<String> student_names;
	private static ArrayList<Student> comMembers;//store all the committee members
	private static ArrayList<String> repliers;
	private static ArrayList<String> emails;
	private static Map<String, Integer> replyCounts;
	
	/**
	 * Constructs a Server object and initiates the initialization process.
	 * The Server object manages various data structures and functionalities for the application.
     * @param filepath The base filepath used to construct the file paths for data files.
	 */
	public Server(String filepath) {
        // Initialize file paths using the provided filepath
        StudentFilePath = filepath + "/src/Datasc2/student";
        StaffFilePath = filepath + "/src//Datasc2/staff";
        CampsFilePath = filepath + "/src//Datasc2/campData";
        CampsFilePathWithMembers = filepath + "/src//Datasc2/campDataWithMembers";
        InquirysFilePath = filepath + "/src/Datasc2/Inquiries";
        SuggestionFilePath = filepath + "/src/Datasc2/suggestion";
        filePathToReport = filepath + "/src/Datasc2/Report/";
        filePathToCMReport = filepath + "/src/Datasc2/PerformanceReport/";
        filePathToPasswords = filepath + "/src/Datasc2/passwords/";
        filePathToPoints = filepath + "/src/Datasc2/Points/";
        filePathToInquiry = filepath + "/src/Datasc2/Inquiry/";
        start();
    }
    
    /**
     * Initializes the data structures used by the server.
     */
    private static void initializeDataStructures() {
        staff_names = new ArrayList<>();
        faculties = new ArrayList<>();
        userIDs = new ArrayList<>();
        usersData = new ArrayList<>();
        inquiries = new ArrayList<>();
        suggestions = new ArrayList<>();
        student_names = new ArrayList<>();
        comMembers = new ArrayList<>();
        repliers = new ArrayList<>();
        emails = new ArrayList<>();
        replyCounts = new HashMap<>();
    }
    
    /**
     * Loads student data from a file and populates the system with the retrieved information.
     * 
     * @throws IOException If there are issues with file input/output operations.
     */
    private static void loadStudenData() throws IOException
    {
    	ArrayList<ArrayList<String>> dataFromFileHandler = new ArrayList<>();
    	dataFromFileHandler=File_Handler.PutFileContentIntoArray(StudentFilePath);
    	student_names = dataFromFileHandler.get(0);
        emails = dataFromFileHandler.get(1);
        faculties = dataFromFileHandler.get(2);
        userIDs = dataFromFileHandler.get(3);
        for (int i = 0; i < student_names.size(); i++) {
            Student student = new Student(student_names.get(i), userIDs.get(i), faculties.get(i));            
            usersData.add(student);
        }
       
    }
    
    /**
     * Loads staff data from a file and populates the system with the retrieved information.
     * 
     * @throws FileNotFoundException If the specified file for staff data is not found.
     */
    private static void loadStaffData() throws FileNotFoundException
    {
    	ArrayList<ArrayList<String>> dataFromFileHandler = new ArrayList<>();

        dataFromFileHandler=File_Handler.PutFileContentIntoArray(StaffFilePath);
        staff_names = dataFromFileHandler.get(0);
        emails =dataFromFileHandler.get(1);
        faculties = dataFromFileHandler.get(2);
        userIDs = dataFromFileHandler.get(3);
        
        //server is an arraylist of User objects       
        for (int i = 0; i < staff_names.size(); i++) {
            Staff staff = new Staff(staff_names.get(i), userIDs.get(i), faculties.get(i));
            usersData.add(staff);
        }
    }
    
    /**
     * Initializes and starts the system by loading necessary data, managing inquiries,
     * suggestions, points, passwords, camps, and other relevant information.
     * 
     * This method orchestrates various operations such as loading student and staff data,
     * handling inquiries and suggestions, calculating and updating suggestion points,
     * managing passwords, camps, committee members, and reply points. It populates the
     * system with loaded data and stores calculated points into respective files.
     * 
     * The method handles exceptions related to file operations and data loading, printing
     * stack traces in case of errors without interrupting the system's operation.
     */
    public static void start()
	{
		ArrayList<ArrayList<String>> dataFromFileHandler = new ArrayList<>();
		initializeDataStructures();
        
		try {
            loadStudenData();
            loadStaffData();
            
            try {
				Inquiries = LoadAllStudentInquiries();
				
				/////////////////////Start from here to load back and count points////////////////////////
				//count the points starting from inquiries file
				//suggestion counted as it is loaded back into the student
				
				//count the points for replies
				for (Inquiry i:Inquiries)
				{
					repliers.add(i.getReplierID());//first create array of strings for all the IDs
				}
				for (String replier : repliers) {
		            replyCounts.put(replier, replyCounts.getOrDefault(replier, 0) + 1);
		        }  
				
			} catch (Exception e) {
				e.printStackTrace();
			} // Load your inquiries as needed
            inquiryManager = new InquiryManager();
            inquiryManager.LoadInquiries(Inquiries);
            for (User user : usersData) {
                if (user instanceof Student) {
                    Student student = (Student) user;
                    ArrayList<Inquiry> StudentIquiries = inquiryManager.getInquiriesForUser(student);
                    student.setInquiries(StudentIquiries);  
                }
            }
            
            try {
				Suggestions = LoadAllStudentSuggestions();
			} catch (Exception e) {
				e.printStackTrace();
			}
            // Load your inquiries as needed
            suggestionManager = new SuggestionManager();
            suggestionManager.LoadSuggestion(Suggestions);
            for (User user : usersData) {
                if (user instanceof Student) {
                    Student student = (Student) user;
                    ArrayList<Suggestion> StudentSuggestions = suggestionManager.getSuggestionsForUser(student);
                    student.setSuggestions(StudentSuggestions);  
                }
            }
            
            /////LOAD SUGGESTION POINTS/////
            for (User user : usersData)
            {
            	int approved_suggestions=0;
            	if (user instanceof Student)
            	{
            		Student student = (Student) user;
            		for (Suggestion suggestion : student.getSuggestions())
            			
            		{
            			student.addPointsForSuggestion();
            			if (suggestion.getStatus()==SuggestStatus.approved)
            			{
            				approved_suggestions++;
            				student.addPointsForAcceptedSuggestion();
            			}
            		}
            		student.setnumberofApprovedSuggestions(approved_suggestions);
            	}
            }
            
            
            PasswordsFileHandler PassFileHandler = new PasswordsFileHandler(filePathToPasswords, usersData);
            PassFileHandler.setPasswordsFromPasswordsFile(filePathToPasswords);
            
            camps = loadTxTtoCamps(CampsFilePathWithMembers, usersData);
            //Add to comMembers
            for (User user : usersData) {
            	if (user instanceof Student) {
            		Student student = (Student) user;
            		student.setCommitteeMember(isStudentAlreadyCommitteeMember(student));
            		comMembers.add(student);
            	}
            }
	        ////LOAD REPLIES POINTS

            for (Map.Entry<String, Integer> e : replyCounts.entrySet())
            {
            	for(Student comStudent:comMembers)
            	{
            		if(comStudent.getNtuNetworkId().equalsIgnoreCase(e.getKey()))//find the ID in com Members
            		{
            			comStudent.setReplyPoints(e.getValue());//load the number of replies
            			comStudent.setNumberOfReplies(e.getValue());
            		}
            	}
            }
                      
            ////load the points calculated to points file
            
            
            PointsFileHandler pointsFileHanlder = new PointsFileHandler(filePathToPoints, comMembers); 
            
            pointsFileHanlder.savePointsToFile();
            
        } catch(IOException e){
        }
	}
    
    /**
     * Changes the password for the provided user and saves it to the passwords file.
     * 
     * @param user      The user for whom the password needs to be changed.
     * @param password  The new password to be set for the user.
     */
    public void ChangeAndSavePasswords(User user,String password)
    {
    	user.setPassword(password);
    	PasswordsFileHandler pfh = new PasswordsFileHandler(filePathToPasswords, usersData);
    	pfh.SavePasswordsToFile();	
    }
    
    /**
     * Updates the points file with the current committee members' points data.
     * This method saves the points information to the respective file.
     */
	public void updatePointsFile()
	{
		PointsFileHandler pfh = new PointsFileHandler(filePathToPoints, comMembers);
		pfh.savePointsToFile();
	}
	
	/**
	 * Authenticates a user based on the provided userID and password.
	 * 
	 * @param userID    The ID of the user trying to log in.
	 * @param password  The password provided by the user for login.
	 * @return          The User object if login is successful; null otherwise.
	 */
	public User login(String userID, String password) {
	    for (User user : usersData) {
	    	
	        if (user.getNtuNetworkId().equals(userID) && user.getPassword().equals(password)) {
	            if (user instanceof Student) {
	                // User is a student
	            	
	                return user; 
	            } else if (user instanceof Staff) {
	                // User is a staff
	                return user; 
	            }
	        }
	    }
	    
	    return null; // Login failed
	}
	
	//---------------------------------------Camp------------------------------------------------
	
	/**
	 * Creates a new camp with the provided details and adds it to the list of camps.
	 * 
	 * @param staff                  The staff member creating the camp.
	 * @param campName               The name of the camp.
	 * @param dates                  The dates associated with the camp.
	 * @param registrationClosingDate The closing date for camp registration.
	 * @param userGroup              The user group for the camp.
	 * @param location               The location of the camp.
	 * @param totalSlots             The total available slots for the camp.
	 * @param description            The description of the camp.
	 * @param TotalcommitteeSlots    The total slots for committee members.
	 * @param v                      A boolean value (unclear purpose).
	 * @param committeeslots         The available committee slots.
	 * @param memberslots            The available member slots.
	 * @return                       The newly created camp.
	 */
	public Camp createCamp(Staff staff, String campName, ArrayList<String> dates, String registrationClosingDate, String userGroup, String location,
            int totalSlots, String description,int TotalcommitteeSlots,boolean v,int committeeslots,int memberslots) {
        // Create a new camp using the provided details
        Camp newCamp = staff.createCamp(campName, dates, registrationClosingDate, userGroup, location, totalSlots,description,TotalcommitteeSlots, false, committeeslots, memberslots);

        // Add the new camp to the list of camps
        camps.add(newCamp);
        System.out.println("Camp created!");
        saveCampsToTxtFile(CampsFilePathWithMembers);
        return newCamp;
    }
	
	/**
	 * Deletes the provided camp from the list of camps.
	 * 
	 * @param camp The camp to be deleted.
	 * @return     True if deletion is successful, false otherwise.
	 */
	public boolean deleteCamp(Camp camp, User user) {
        // Check if the camp exists in the list of camps
        if (camps.contains(camp)&&camp.getStaffInCharge()==user) {
            // Remove the camp from the list
            camps.remove(camp);
            saveCampsToTxtFile(CampsFilePathWithMembers);
            return true; // Deletion successful
        }

        return false; // Camp not found
    }
	

	/**
	 * Updates the details of the provided camp.
	 * 
	 * @param camp                  The camp to be edited.
	 * @param campName              The updated camp name.
	 * @param dates                 The updated dates for the camp.
	 * @param registrationClosingDate The updated registration closing date.
	 * @param userGroup             The updated user group for the camp.
	 * @param location              The updated location of the camp.
	 * @param totalSlots            The updated total available slots for the camp.
	 * @param campCommitteeSlots    The updated available committee slots for the camp.
	 * @param description           The updated description of the camp.
	 * @return                      True if edit is successful, false if the camp is not found.
	 */
	public boolean editCamp(Camp camp, String campName, ArrayList<String> dates, String registrationClosingDate, String userGroup, String location,
            int totalSlots, int campCommitteeSlots, String description) {
        // Check if the camp exists in the list of camps
        if (camps.contains(camp)) {
            // Update the camp details
            camp.setCampName(campName);
            camp.setDates(dates);
            camp.setRegistrationClosingDate(registrationClosingDate);
            camp.setUserGroup(userGroup);
            camp.setLocation(location);
            camp.setTotalSlots(totalSlots);
            camp.setCampCommitteeSlots(campCommitteeSlots);
            camp.setDescription(description);
            saveCampsToTxtFile(CampsFilePathWithMembers);
            System.out.println("Camp Edited!");
            return true; // Edit successful
        }

        return false; // Camp not found
    }
	
	/**
	 * Finds a camp by its name.
	 * 
	 * @param campName The name of the camp to search for.
	 * @return         The camp if found, otherwise null.
	 */
	public Camp findCampByName(String campName) {
	    for (Camp camp : camps) {
	        if (camp.getCampName().equalsIgnoreCase(campName)) {
	            return camp;
	        }
	    }
	    return null; // Camp not found
	}
	
	/**
	 * Retrieves a list containing all camps.
	 * 
	 * @return An ArrayList containing all camps.
	 */
	public ArrayList<Camp> getAllCamps(){
		return camps;
	}
	
	/**
	 * Retrieves all available camps for a given user based on available slots, user group, and registration status.
	 * 
	 * @param user The user for whom available camps are being retrieved.
	 * @return     An ArrayList containing available camps for the user.
	 */
	public ArrayList<Camp> getAllAvailableCamp(User user) {
        ArrayList<Camp> availableCamps = new ArrayList<>();
        for (Camp camp : camps) {
            // Check if the camp has available slots and the user's faculty matches the camp's user group
            if (camp.getAvailableSlots() > 0 &&
                (camp.getUserGroup().equalsIgnoreCase(user.getFaculty()) ||
                 camp.getUserGroup().equalsIgnoreCase("NTU"))) {
                if (!camp.getCampCommitteeMembers().contains(user)) {
                    if (CalendarExample.isDateEqualOrAfterCurrentDate(camp.getRegistrationClosingDate())) {
                        availableCamps.add(camp);
                    }
                }
            }
        }
        return availableCamps;
    }
	
	/**
	 * Retrieves all camps created by a specific user (staff member).
	 * 
	 * @param user The user for whom camps created are being retrieved.
	 * @return     An ArrayList containing camps created by the user.
	 */
	public ArrayList<Camp> getAllAvailableCamps(User user) {
        ArrayList<Camp> availableCamps = new ArrayList<>();
        for (Camp camp : camps) {
            // Check if the camp has available slots and the user's faculty matches the camp's user group
            if (camp.getAvailableSlots() > 0 &&
                (camp.getUserGroup().equalsIgnoreCase(user.getFaculty()) ||
                 camp.getUserGroup().equalsIgnoreCase("NTU"))) {
                if (!camp.getCampCommitteeMembers().contains(user)) {
                       availableCamps.add(camp);
                }
            }
        }
        return availableCamps;
    }
	
	/**
	 * Retrieves all camps created by a specific user (staff member).
	 *
	 * @param user The user for whom camps created are being retrieved.
	 * @return An ArrayList containing camps created by the user.
	 */
	public ArrayList<Camp> getCampsCreatedByUser(User user) {	//must check if User is staff(add)
	    ArrayList<Camp> userCreatedCamps = new ArrayList<>();
	    for (Camp camp : camps) {
	        if (camp.getStaffInCharge().equals(user)) {
	            userCreatedCamps.add(camp);
	        }
	    }
	    return userCreatedCamps;
	}
	
	/**
	 * Saves the list of camps to a text file.
	 * 
	 * @param filePath The file path where the camps will be saved.
	 */
	public void saveCampsToTxtFile(String filePath) {
        CampFileHandler.saveCampsToTextFile_2(camps, filePath);
    }
	
	/**
	 * Loads camps from a text file into the system.
	 * 
	 * @param filePath   The file path from which camps will be loaded.
	 * @param usersData  The list of users' data required for camp loading.
	 * @return           An ArrayList containing camps loaded from the file.
	 */
	public static ArrayList<Camp> loadTxTtoCamps(String filePath,ArrayList<User> usersData)
	{
		try {
			System.out.println("loading txt file into server...");
			camps = CampFileHandler.loadCampsFromFile(filePath, usersData, CampsFilePathWithMembers);
			return camps;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
    //------------------------------------Register as attendee or Member------------------------------------------

	/**
	 * Registers a student as a committee member for a specific camp.
	 * 
	 * @param student   The student to be registered as a committee member.
	 * @param campName  The name of the camp where the student will be registered.
	 * @return          A boolean indicating if the registration was successful.
	 */
	public boolean registerAsCommitteeMemeber(Student student,String CampName)
	{
		Camp TargetCamp = findCampByName(CampName);
		if((TargetCamp.getAvailableSlotsForCommitteeMember()>0))
		{			
			TargetCamp.addCampCommitteeMember(student);
			comMembers.add(student);
			
			ArrayList<Camp> attendingCamps = getCampsAttending(student);
			for (Camp camp : attendingCamps) {
				for (String date : TargetCamp.getDates()) {
		    		for(String campDate : camp.getDates()) {
		    			if (campDate.equals(date)) {
	                        withdrawFromCampAttendee(student, camp.getCampName());
	                    
	                	}
		    		}
		        }
		    }
			
			saveCampsToTxtFile(CampsFilePathWithMembers);
			return true;
		}
	    else
	    {
	    	System.out.println("No more vacancy");
	    }
		return false;	
	}
	
	/**
	 * Registers a student as an attendee for a specific camp.
	 * 
	 * @param student   The student to be registered as an attendee.
	 * @param campName  The name of the camp where the student will be registered.
	 * @return          A boolean indicating if the registration was successful.
	 */
	public boolean registerAsCampAttendee(Student student, String campName) {
	    ArrayList<Camp> attendingCamps = getCampsAttending(student);
	    Camp targetCamp = findCampByName(campName);
	    ArrayList<Camp> committeeCamps = getCampsAsCommitteeMember(student);

	    if (targetCamp.getCampCommitteeMembers().contains(student)) {
            return false; // The student is already a member of the chosen camp
        }
	    
	    // Check if camp is available for their faculty
	    if (!targetCamp.getUserGroup().equals(student.getFaculty())&&targetCamp.getUserGroup().equalsIgnoreCase("NULL")) {
	        System.out.println("Sorry, this camp isn't available for you");
	        return false;
	    }
	    
	    for (Camp camp : committeeCamps) {
	    	for (String date : targetCamp.getDates()) {
	    		for(String campDate : camp.getDates()) {
	    			if (campDate.equals(date)) {
	    				System.out.println("You are already attending a camp on that day");
	    				return false;
	    			}
	    		}
	        }
	    }

	    for (Camp camp : attendingCamps) {
	    	for (String date : targetCamp.getDates()) {
	    		for(String campDate : camp.getDates()) {
	    			if (campDate.equals(date)) {
	    				System.out.println("You are already attending a camp on that day");
	    				return false;
	    			}
	    		}
	        }
	    }

	    if (targetCamp.getAvailableSlots() > 0) {
	        targetCamp.addCampAttendee(student);
	        saveCampsToTxtFile(CampsFilePathWithMembers);
	        return true;
	    }
	    else
	    {
	    	System.out.println("No more vacancy");
	    }

	    System.out.println("Camp attendee member slot is full.");
	    return false;
	}

	/**
	 * Checks if the given student is already a committee member for any camp.
	 *
	 * @param student The student to check for committee membership
	 * @return {@code true} if the student is already a committee member, otherwise {@code false}
	 */
	public static boolean isStudentAlreadyCommitteeMember(Student student) {
        // Get the camp object by its name
		for (Camp camp : camps) {
	        ArrayList<Student> committeeMembers = camp.getCampCommitteeMembers();
	        for (Student committeeMember : committeeMembers) {
	            if (committeeMember.equals(student)) {
	                return true; // The student is already a committee member
	            }
	        }
	    }
	    return false; // The student is not a committee member
    }
	
	/**
	 * Checks if a student is already attending a specific camp.
	 *
	 * @param student   The student to check for attendance
	 * @param campName  The name of the camp to check attendance for
	 * @return {@code true} if the student is attending the camp, otherwise {@code false}
	 */
	public boolean isStudentAlreadyAttending(Student student, String campName) {
	    for (Camp camp : camps) {
	        if (camp.getCampName().equals(campName) && camp.getCampAttendees().contains(student)) {
	            return true; // The student is already attending the chosen camp
	        }
	    }
	    return false; // The student is not attending the chosen camp
	}

	/**
	 * Retrieves a camp object by its name.
	 *
	 * @param campName The name of the camp to retrieve
	 * @return The Camp object if found, otherwise {@code null}
	 */
	public Camp getCampByName(String campName) {
	        for (Camp camp : camps) {
	            if (camp.getCampName().equals(campName)) {
	                return camp;
	            }
	        }
	        return null; // Camp not found
	}
    
	/**
	 * Retrieves the camps where the given student is a committee member.
	 *
	 * @param student The student to check for committee membership
	 * @return The list of camps where the student is a committee member
	 */
    public ArrayList<Camp> getCampsAsCommitteeMember(Student student) {
        ArrayList<Camp> committeeCamps = new ArrayList<>();
        for (Camp camp : camps) {
            if (camp.getCampCommitteeMembers().contains(student)) {
                committeeCamps.add(camp);
            }
        }
        return committeeCamps;
    }
    
    /**
     * Retrieves the list of camps that the given student is attending.
     *
     * @param student The student whose attended camps are being retrieved
     * @return The list of camps the student is attending
     */
    public ArrayList<Camp> getCampsAttending(Student student) {
        ArrayList<Camp> attendingCamps = new ArrayList<>();
        for (Camp camp : camps) {
            if (camp.getCampAttendees().contains(student)) {
                attendingCamps.add(camp);
            }
        }
        return attendingCamps;
    }

    /**
     * Withdraws the given student from a specific camp attendee list.
     *
     * @param student   The student to withdraw from the camp
     * @param campName  The name of the camp from which the student is withdrawing
     * @return {@code true} if withdrawal was successful, otherwise {@code false}
     */
    public boolean withdrawFromCampAttendee(Student student, String campName) {
        Camp targetCamp = findCampByName(campName);
        if (targetCamp != null) {
            if (targetCamp.removeCampAttendee(student)) {
                // Update the vacancy
                targetCamp.incrementAvailableSlots();
                saveCampsToTxtFile(CampsFilePathWithMembers);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if there are attendees or committee members registered for a specific camp.
     *
     * @param camp The camp to check for registered attendees or committee members
     * @return {@code true} if there are registered attendees or committee members, otherwise {@code false}
     */
    public boolean GotPeoplealreadyRegistered(Camp camp)
    {
    	if(camp.getCampAttendees().size() != 0 )
    	{
    		System.out.println("there are " + camp.getCampAttendees().size()+ " camp attendees and " + camp.getCampCommitteeMembers().size() +" committee member registered for camp "+camp.getCampName());
    		return true;
    	}
    	else if(camp.getCampCommitteeMembers().size() != 0)
    	{
    		System.out.println("there are " + camp.getCampAttendees().size()+ " attendees and " + camp.getCampCommitteeMembers().size() +"registered for camp "+camp.getCampName());
    		return true;
    	}
    	return false;
    }
    
    ////////////////////Display Camp Info/////////////////////////
    
    /**
     * Displays the camp information created by the logged-in user.
     *
     * @param loggedInUser The user logged into the system
     */
    public void displayCampInfo(User loggedInUser)
    {
    	ArrayList<Camp> campsInCharge= getCampsCreatedByUser(loggedInUser);
    	if (campsInCharge.isEmpty()) {
            System.out.println("You haven't created any camps yet.");
            
        } else {
            System.out.println("Camps created by " + loggedInUser.getName() + ":");
            for (Camp camp : campsInCharge) {
                System.out.println("Camp Name: " + camp.getCampName());
                System.out.println("Dates: " + camp.getDates());
                System.out.println("Location: " + camp.getLocation());
                System.out.println("Camp Attendees: " + camp.getCampAttendees());
                System.out.println("Camp Committee Members: " + camp.getCampCommitteeMembers());
                // Add more camp details as needed
                System.out.println("--------------------------------");
            }
        }
    }
    //-------------------------------Inquiries----------------------------------------
	
    /**
     * Stores an inquiry in the system.
     *
     * @param inquiry The inquiry to be stored
     */
    public void storeInquiry(Inquiry inquiry) {
			inquiries.add(inquiry);
	}

    /**
     * Retrieves all inquiries stored in the system.
     *
     * @return An ArrayList containing all stored inquiries
     */
    public ArrayList<Inquiry> getInquiries() {
        return inquiries;
    }
    
    /**
     * Stores an inquiry for a user in the system using the InquiryManager.
     *
     * @param inquiry The inquiry to be stored
     */
    public void storeInquiries(Inquiry inquiry) {
        inquiryManager.storeInquiries(inquiry);
    }

    /**
     * Retrieves inquiries for a specific user from the InquiryManager.
     *
     * @param user The user for whom inquiries need to be retrieved
     * @return An ArrayList containing inquiries for the specified user
     */
    public ArrayList<Inquiry> getInquiriesForUser(User user) {
        return inquiryManager.getInquiriesForUser(user);
    }

    /**
     * Retrieves all inquiries stored in the system using the InquiryManager.
     *
     * @return An ArrayList containing all stored inquiries
     */
    public ArrayList<Inquiry> getAllInquiries() {
        return inquiryManager.getAllInquiries();
    }
	
    /**
     * Updates the inquiries for a specific user.
     *
     * @param user            The user whose inquiries are to be updated
     * @param updatedInquiries The updated list of inquiries for the user
     */
    public void updateInquiries(User user, ArrayList<Inquiry> updatedInquiries) {
        if (user instanceof Student) {
            Student student = (Student) user;
            student.setInquiries(updatedInquiries);
        }
    }    
    
    /**
     * Retrieves all inquiries from students in the system.
     *
     * @return An ArrayList containing all inquiries from students
     */
    public ArrayList<Inquiry> getAllStudentInquiries() {
        ArrayList<Inquiry> studentInquiries = new ArrayList<>();
        for (User user : usersData) {
            if (user instanceof Student) {
                Student student = (Student) user;
                studentInquiries.addAll(student.getInquiries());
            }
        }
        return studentInquiries;
    }
    
    /**
     * Saves all student inquiries to a file.
     */
    public void saveAllStudentInquiriesToFile() {
        ArrayList<Inquiry> studentInquiries = getAllStudentInquiries();
        InquiryFileHandler inquiryFileHandler = new InquiryFileHandler(InquirysFilePath,usersData);
		inquiryFileHandler.saveInquiriesToFile(studentInquiries);
    }
    
    /**
     * Loads all student inquiries from a file.
     *
     * @return An ArrayList containing all loaded student inquiries
     */
    public static ArrayList<Inquiry> LoadAllStudentInquiries()
    {	
    	InquiryFileHandler inquiryFileHandlerLoader = new InquiryFileHandler(InquirysFilePath,usersData);
    	try {
			return inquiryFileHandlerLoader.loadInquiriesFromFile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
    }

    /**
     * Displays and allows staff to reply to inquiries related to camps they have created.
     *
     * @param staffUser The staff user viewing and replying to inquiries
     */
    public void viewAndReplyToInquiries(Staff staffUser) {
        System.out.println("------------- View and Reply to Inquiries -------------");

        // Load inquiries from the file
        ArrayList<Inquiry> allInquiries = getAllInquiries();

        // Get the camps created by the staff user
        ArrayList<Camp> staffCreatedCamps = getCampsCreatedByUser(staffUser);

        // Iterate through inquiries and display relevant information
        for (Inquiry inquiry : allInquiries) {
            for (Camp camp : staffCreatedCamps) {
                // Check if the camp names match
                if (inquiry.getCamp().equals(camp.getCampName())) {
                    // Check if the reply is currently null
                    if (inquiry.getReply() == null || "null".equals(inquiry.getReply())) {
                        // Display relevant information
                        System.out.println("Sender: " + inquiry.getSender().getName());
                        System.out.println("Message: " + inquiry.getMessage());
                        System.out.println("Camp Name: " + camp.getCampName());
                        System.out.println("--------------------------");

                        // Now, prompt the staff user to enter a reply
                        System.out.print("Enter your reply: ");
                        Scanner scanner = new Scanner(System.in);
                        String reply = scanner.nextLine();
                      
                        if(!reply.equalsIgnoreCase("null"))
                        {
                        	// Update the inquiry with the reply
                        	inquiry.setReply(reply,staffUser.getNtuNetworkId());

                        	// Save the updated inquiries to the file
                        	try (BufferedWriter writer = new BufferedWriter(new FileWriter(InquirysFilePath, true))) {
                        		// Append the new reply to the file
                            	writer.write("Sender: " + inquiry.getSender().getNtuNetworkId() + "\n");
                            	writer.write("Message: " + inquiry.getMessage() + "\n");
                            	writer.write("Camp: " + inquiry.getCamp() + "\n");
                            	writer.write("Reply: " + reply + "\n");
                            	writer.write("\n"); // Add a separator between inquiries
                            	System.out.println("Reply saved successfully.");
                            
                        	} catch (IOException e) {
                            	e.printStackTrace();
                            	System.out.println("Error saving reply.");
                        	}
                        }
                    }
                }
            }
        }
        System.out.println("No inquiries to display.");
    }
    
    /**
     * Displays inquiries for a committee member student and allows them to reply to these inquiries.
     * Points are awarded for replying to inquiries.
     *
     * @param studentUser The student user viewing and replying to inquiries
     */
    public void viewAndReplyToInquiry(Student studentUser) {
        System.out.println("------------- View and Reply to Inquiries -------------");

        // Load inquiries from the file
        ArrayList<Inquiry> allInquiries = getAllInquiries();

        // Get the camps where the student is a member
        ArrayList<Camp> studentMemberCamps = getCampsForStudent(studentUser);

        // Iterate through inquiries and display relevant information
        for (Inquiry inquiry : allInquiries) {
            for (Camp camp : studentMemberCamps) {
                // Check if the camp names match
                if (inquiry.getCamp().equals(camp.getCampName())) {
                    // Check if the reply is currently null
                    if (inquiry.getReply() == null || "null".equals(inquiry.getReply())) {
                        // Display relevant information
                        System.out.println("Sender: " + inquiry.getSender().getName());
                        System.out.println("Message: " + inquiry.getMessage());
                        System.out.println("Camp Name: " + camp.getCampName());
                        System.out.println("--------------------------");

                        // Now, prompt the student user to enter a reply
                        System.out.print("Enter your reply: ");
                        Scanner scanner = new Scanner(System.in);
                        String reply = scanner.nextLine();

                        if(!reply.equalsIgnoreCase("null")) {
                        	// Update the inquiry with the reply
                        	inquiry.setReply(reply,studentUser.getNtuNetworkId());
                        
                        	studentUser.addPointsForEnquiryReply();
                        
                        	// Save the updated inquiries to the file
                        	try (BufferedWriter writer = new BufferedWriter(new FileWriter(InquirysFilePath, true))) {
                        		// Append the new reply to the file
                            	writer.write("Sender: " + inquiry.getSender().getNtuNetworkId() + "\n");
                            	writer.write("Message: " + inquiry.getMessage() + "\n");
                            	writer.write("Camp: " + inquiry.getCamp() + "\n");
                            	writer.write("Reply: " + reply + "\n");
                            	writer.write("\n"); // Add a separator between inquiries
                            	System.out.println("Reply saved successfully.");
                            
                        	} catch (IOException e) {
                            	e.printStackTrace();
                            	System.out.println("Error saving reply.");
                        	}
                        }
                    }
                }
            }
        }
        System.out.println("No inquiries to display.");
    }

    /**
     * Retrieves the camps where the student is a committee member.
     *
     * @param student The student whose committee member camps are being retrieved
     * @return An ArrayList containing camps where the student is a committee member
     */
    private ArrayList<Camp> getCampsForStudent(Student student) {
        ArrayList<Camp> studentMemberCamps = new ArrayList<>();
        ArrayList<Camp> committeeCamps = getCampsAsCommitteeMember(student);
        // Iterate through all camps
        for (Camp camp : committeeCamps) {
                studentMemberCamps.add(camp);
        }
        return studentMemberCamps;
    }
    
    //------------------------------Visibility fo camps--------------------------------------------
    
    /**
     * Toggles the visibility of a camp for a staff member.
     * 
     * @param staff      The staff member toggling the camp visibility
     * @param campToToggle The camp to toggle visibility
     */
    public void toggleVisibility(Staff staff,Camp campToToggle)
	{
		staff.toggleCampVisibility(campToToggle);
		saveCampsToTxtFile(CampsFilePathWithMembers);
	}
    
    //------------------------------------Suggestions---------------------------------------------
    
    /**
     * Stores a suggestion in the system.
     * 
     * @param suggestion The suggestion to store
     */
    public void storeSuggestion(Suggestion suggestion) {
    	suggestions.add(suggestion);
    }

    /**
     * Retrieves all stored suggestions.
     * 
     * @return An ArrayList containing all stored suggestions
     */
    public ArrayList<Suggestion> getSuggestions() {
    	return suggestions;
    }
    
    /**
     * Stores a suggestion in the system.
     * 
     * @param suggestion The suggestion to store
     */
    public void storeSuggestions(Suggestion suggestion) {
    	suggestionManager.storeSuggestion(suggestion);
    }
    
    /**
     * Retrieves suggestions made by a specific user.
     * 
     * @param user The user for whom suggestions are being retrieved
     * @return An ArrayList containing suggestions made by the user
     */
    public ArrayList<Suggestion> getSuggestionsForUser(User user) {
    	return suggestionManager.getSuggestionsForUser(user);
    }
    
    /**
     * Retrieves all stored suggestions.
     * 
     * @return An ArrayList containing all stored suggestions
     */
    public ArrayList<Suggestion> getAllSuggestions() {
    	return suggestionManager.getAllSuggestions();
    }	
    
    /**
     * Updates a user's suggestions with the provided updated suggestions.
     * 
     * @param user              The user whose suggestions need updating
     * @param updatedSuggestions The updated suggestions to be set for the user
     */
    public void updateSuggestions(User user, ArrayList<Suggestion> updatedSuggestions) {
    	if (user instanceof Student) {
    		Student student = (Student) user;
    		student.setSuggestions(updatedSuggestions);
    	}
    }
    
    /**
     * Retrieves all suggestions made by students.
     * 
     * @return An ArrayList containing all suggestions made by students
     */
    public ArrayList<Suggestion> getAllStudentSuggestions() {
        ArrayList<Suggestion> studentSuggestions = new ArrayList<>();
        for (User user : usersData) {
            if (user instanceof Student) {
                Student student = (Student) user;
                studentSuggestions.addAll(student.getSuggestions());
            }
        }
        return studentSuggestions;
    }
    

    /**
 	* Saves all student suggestions to a file.
 	*/
    public void saveAllStudentSuggestionsToFile() {
        ArrayList<Suggestion> studentSuggestions = getAllStudentSuggestions();
        SuggestionFileHandler suggestionFileHandler = new SuggestionFileHandler(SuggestionFilePath,usersData);
        suggestionFileHandler.saveSuggestionsToFile(studentSuggestions);
    }
    
    /**
     * Loads all student suggestions from a file.
     * 
     * @return An ArrayList containing all loaded student suggestions
     */
    public static ArrayList<Suggestion> LoadAllStudentSuggestions()
    {	
    	SuggestionFileHandler suggestionFileHandlerLoader = new SuggestionFileHandler(SuggestionFilePath,usersData);
    	try {
			return suggestionFileHandlerLoader.loadSuggestionFromFile();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    
    /**
     * Allows a staff member to view and approve suggestions made for camps created by them.
     * 
     * @param staffUser The staff member who wants to view and approve suggestions
     */
    public void viewAndApproveSuggestions(Staff staffUser) {
        System.out.println("------------- View and Approve to Suggestions -------------");

        // Load inquiries from the file
        ArrayList<Suggestion> allSuggestions = getAllSuggestions();

        // Get the camps created by the staff user
        ArrayList<Camp> staffCreatedCamps = getCampsCreatedByUser(staffUser);

        // Iterate through inquiries and display relevant information
        for (Suggestion suggestion : allSuggestions) {
            for (Camp camp : staffCreatedCamps) {
                // Check if the camp names match
                if (suggestion.getCamp().equals(camp.getCampName())) {
                    // Check if the reply is currently null
                    if (suggestion.getStatus() == SuggestStatus.pending || "pending".equals(suggestion.getStatus())) {
                        // Display relevant information
                        System.out.println("Sender: " + suggestion.getSender().getName());
                        System.out.println("Message: " + suggestion.getMessage());
                        System.out.println("Camp Name: " + camp.getCampName());
                        System.out.println("--------------------------");

                        // Now, prompt the staff user to enter a reply
                        System.out.print("Do you want to approve this Suggestion: (yes/no)");
                        Scanner scanner = new Scanner(System.in);
                        String approve = scanner.nextLine();
                       
                        if(approve.equalsIgnoreCase("yes")){
                            suggestion.setStatus(SuggestStatus.approved);
                            Student s = (Student) suggestion.getSender();
                            s.addPointsForAcceptedSuggestion();
                       }
                       else if(approve.equalsIgnoreCase("no")){
                            suggestion.setStatus(SuggestStatus.rejected);
                       }
                       else{
                    	   suggestion.setStatus(SuggestStatus.pending);
                       }

                        // Save the updated inquiries to the file
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SuggestionFilePath, true))) {
                            // Append the new reply to the file
                            writer.write("Sender: " + suggestion.getSender().getNtuNetworkId() + "\n");
                            writer.write("Message: " + suggestion.getMessage() + "\n");
                            writer.write("Camp: " + suggestion.getCamp() + "\n");
                            writer.write("Reply: " + approve + "\n");
                            writer.write("\n"); // Add a separator between inquiries
                            System.out.println("Approval saved successfully.");
                            
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("Error saving Approval.");
                        }
                    }
                }
            }
        }
        System.out.println("No suggestions to display.");
    }
    
    //--------------------------------------------Report--------------------------------------------
  
    ////////////////////////////camp report by staff///////////////////////////////////////
    
    /**
     * Allows a staff user to generate various reports related to the camps they have created. 
     * Reports can be generated based on different criteria such as all camps created, specific camps, 
     * camps on particular dates, camps from specific locations, and camps involving specific students.
     * 
     * @param loggedInUser The staff user initiating the report generation
     */
    public void generateCampReport(Staff loggedInUser) {
    	Scanner scanner = new Scanner(System.in);
    	ArrayList<Camp> userCreatedCamps = getCampsCreatedByUser(loggedInUser);
    	if (userCreatedCamps.isEmpty()) {
    		System.out.println("No camps available to write reports on");
    		return;
    	}
    	
    	System.out.println("Choose an option:");
    	System.out.println("1. Create a report for all camps you created");
    	System.out.println("2. Choose a specific camp to create a report for");
    	System.out.println("3. Create a report for camps based on specific dates");
    	System.out.println("4. Create a report for camps from a certain location");
    	System.out.println("5. Create a report for camps involving a specific student");
    	System.out.println("6. Go Back");
    
    	int option = scanner.nextInt();
    	
    	switch (option) {
    		case 1:
    			//Create a report for all camps you created
            	int i=0;
            	//Create a report file path
            	while(true)
            	{
            		try {
            			File myObj = new File(filePathToReport + loggedInUser.getName() + i + "_All_Camps_report.txt");
            			if (myObj.createNewFile()) {
            				System.out.println("File created: " + myObj.getName());
            				break;
            			} else {
            				i++;
            			}
            		} catch (IOException e) {
            			System.out.println("An error occurred.");
            			e.printStackTrace();
            		}
            	}
            	String filePath = filePathToReport + loggedInUser.getName() + i + "_All_Camps_report.txt";
            	try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {	
            		for (Camp camp : userCreatedCamps) {
                		// Write camp data to the TXT file
                    	writeCampReportToFile(writer, camp);
                	} 
            	}catch (IOException e) {
                    System.err.println("Error saving camp data to TXT: " + e.getMessage());
                }
            	System.out.println("Report generated successfully.");
            	break;
    		
    		case 2:
    			//create report for a specific camp
    			for (Camp camp : userCreatedCamps) {
    				System.out.println(camp.getCampName());
    			}
    			System.out.print("Enter the Camp to write a report: ");
    			scanner.nextLine(); // Consume newline character
    			String campToReport = scanner.nextLine();
    			Camp specificCamp = findCampByName(campToReport);
    			if (specificCamp != null) {
    				int i1=0;
    				// Create a report file path
    				while(true)
    				{
    					try {
    						File myObj = new File(filePathToReport + loggedInUser.getName()+"_"+specificCamp.getCampName()+i1+ "_report.txt");
    						if (myObj.createNewFile()) {
    							System.out.println("File created: " + myObj.getName());
    							break;
    						} else {
    							i1++;
    						}
    					} catch (IOException e) {
    						System.out.println("An error occurred.");
    						e.printStackTrace();
    					}
    				}
    				String filePath1 = filePathToReport +  loggedInUser.getName()+"_"+specificCamp.getCampName() +i1+ "_report.txt";
    				
    				// Implement the report generation logic similar to your existing code
    				try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath1))) {
    					// Write camp data to the TXT file
    					writeCampReportToFile(writer, specificCamp);
    					System.out.println("Report generated successfully.");
    				} catch (IOException e) {
    					System.err.println("Error saving camp data to TXT: " + e.getMessage());
    				}
    			} else {
    				System.out.println("Camp not found.");
    			}
    			break;
    		
    		case 3:
    			//Create a report for camps based on specific dates
    			System.out.println("Enter dates (comma-separated): ");
    			scanner.nextLine(); // Consume newline character
    			String datesInput = scanner.nextLine();
    			String[] datesArray = datesInput.split(",");
    			int k=0;
    			for (Camp camp : userCreatedCamps) {
    				if (containsAnyDate(camp, datesArray)) {
    					k=1;
    				}
    			}
    			if(k==0)
    			{
    				System.out.println("No camps on that day");
    				return;
    			}
    			int j=0;
    			// Create a report file path
    			while(true)
    			{
    				try {
    					File myObj = new File(filePathToReport + loggedInUser.getName() + j + "_Date_report.txt");
    					if (myObj.createNewFile()) {
    						System.out.println("File created: " + myObj.getName());
    						break;
    					} else {
    						j++;
    					}
    				} catch (IOException e) {
    					System.out.println("An error occurred.");
    					e.printStackTrace();
    				}
    			}
    			String filePath1 = filePathToReport + loggedInUser.getName() + j + "_Date_report.txt";
    			try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath1))) {
    				for (Camp camp : userCreatedCamps) {
    					if (containsAnyDate(camp, datesArray)) {
    						writeCampReportToFile(writer, camp);
    					}
    				}
    			}catch (IOException e) {
                       System.err.println("Error saving camp data to TXT: " + e.getMessage());
    			}
    			break;
    		
    		case 4:
    			//Create a report for camps based on specific location
    			System.out.print("Enter location: ");
    			scanner.nextLine(); // Consume newline character
    			String location = scanner.nextLine();
    			int j1=0,f=0;
    			// Create a report file path
    			
    			for (Camp camp : userCreatedCamps) {
					if (camp.getLocation().equalsIgnoreCase(location)) {
						f=1;
					}
				}
    			if(f==0)
    			{
					System.out.println("No camps in that location");

    			}
    			
    			while(f==1)
    			{
    				try {
    					File myObj = new File(filePathToReport + loggedInUser.getName() + j1 + "_Location_report.txt");
    					if (myObj.createNewFile()) {
    						System.out.println("File created: " + myObj.getName());
    						break;
    					} else {
    						j1++;
    					}
    				} catch (IOException e) {
    					System.out.println("An error occurred.");
    					e.printStackTrace();
    				}
    			}
    			String filePath11 = filePathToReport + loggedInUser.getName() + j1 + "_Location_report.txt";
    			try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath11))) {
    				for (Camp camp : userCreatedCamps) {
    					if (camp.getLocation().equalsIgnoreCase(location)) {
    						writeCampReportToFile(writer, camp);
    					}
    				}
    			}catch (IOException e) {
    				System.err.println("Error saving camp data to TXT: " + e.getMessage());
    			}
    			break;
    		
    		case 5:
    			//Create a report for camps involving a specific student
    			System.out.println("Choose an option:");
    			System.out.println("a. Student attending camps");	// if the student is an attendee
    			System.out.println("b. Student as a member of camps");	//if student is a committee member
    			System.out.println("c. Go Back");
    			char choice = scanner.next().charAt(0);
    			
    			if(choice=='c')
    				return;
    			System.out.print("Enter student's name: ");
    			scanner.nextLine(); // Consume newline character
    			String studentName = scanner.nextLine();
    			
    			boolean isAttending = false;
    			boolean isMember = false;
    			
    			for (Camp camp : userCreatedCamps) {
    				switch (choice) {
    					case 'a':
    						if (camp.isAlreadyAttending(studentName)) {
    							isAttending = true;
    						}
    						break;
    					case 'b':
    						if (camp.isStudentMember(studentName)) {
    							isMember = true;
    						}
    						break;
    					default:
    						System.out.println("Invalid choice.");
    						return;
    				}
    			}
    			
    			if (!isAttending && !isMember) {
    				System.out.println("The student is not attending or a member of any camps.");
    				return;
    			}
    			
    			try {
    				int i1 = 0;
    				String fileName = filePathToReport + loggedInUser.getName() + "_" + studentName + "_report.txt";
    				while (true) {
    					File myObj = new File(fileName);
    					if (myObj.createNewFile()) {
    						System.out.println("File created: " + myObj.getName());
    						break;
    					} else {
    						fileName = filePathToReport + loggedInUser.getName() + "_" + studentName + "_" + i1 + "_report.txt";
    						i1++;
    					}
    				}
    				
    				try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
    					for (Camp camp : userCreatedCamps) {
    						switch (choice) {
    						case 'a':
    							if (camp.isAlreadyAttending(studentName)) {
    								writeCampReportToFile(writer, camp);
    							}
    							break;
    						case 'b':
                                if (camp.isStudentMember(studentName)) {
                                    writeCampReportToFile(writer, camp);
                                	}
                                	break;
    						}
    					}
    				} catch (IOException e) {
    					System.err.println("Error saving camp data to TXT: " + e.getMessage());
    				}
    				
    				System.out.println("Report generated successfully.");
    			} catch (IOException e) {
    				System.err.println("An error occurred while creating the report file: " + e.getMessage());
    			}
    			break;
    		
    		case 6:
    			//go back
    			break;
    		
    		default:
    			System.out.println("Invalid option.");
    			break;
    	}
    }

    /**
     * Checks if there are any matching dates in the given camp and date array.
     *
     * @param camp       The camp to check for matching dates.
     * @param datesArray The array of dates to match against the camp's dates.
     * @return True if there are matching dates, otherwise false.
     */
    private boolean containsAnyDate(Camp camp, String[] datesArray) {
    	for (String date : datesArray) {
    		if (camp.getDates().contains(date.trim())) {
    			return true;
    		}
    	}
    	return false;
    }

    /**
     * Writes camp details to a file.
     *
     * @param writer The BufferedWriter used to write to the file.
     * @param camp   The camp whose details are being written.
     * @throws IOException If an I/O error occurs while writing the details.
     */
    private void writeCampReportToFile(BufferedWriter writer, Camp camp) throws IOException {
    	writer.write("Camp Name: " + camp.getCampName() + "\n");
    	writer.write("Dates: " + camp.getDates() + "\n");
    	writer.write("Registration Closing Date: " + camp.getRegistrationClosingDate() + "\n");
    	writer.write("User Group: " + camp.getUserGroup() + "\n");
    	writer.write("Location: " + camp.getLocation() + "\n");
    	writer.write("Total Slots: " + camp.getTotalSlots() + "\n");
    	writer.write("Camp Committee Slots: " + camp.getTotalCampCommitteeSlots() + "\n");
    	writer.write("Description: " + camp.getDescription() + "\n");
    	writer.write("Staff in Charge: " + camp.getStaffInCharge().getNtuNetworkId() + "\n");
    	writer.write("Camp Attendees: " + camp.getCampAttendees().toString() + "\n");
    	writer.write("Camp Members: " + camp.getCampCommitteeMembers().toString() + "\n");
    	writer.write("Available Camp Member Slots: " + camp.getAvailableSlots() + "\n");
    	writer.write("Available Camp Committee Slots: " + camp.getAvailableSlotsForCommitteeMember() + "\n");
    	writer.write("\n"); // Add a separator between camps
    }
    
    //////////////////////////////Performance report by Staff///////////////////////////////////////  
    
    /**
     * Generates a performance report for a specific camp created by a staff user.
     * This method prompts the staff user to select a camp from the list of camps they've created
     * and generates a performance report for all members of that camp.
     *
     * @param loggedInUser The staff user generating the performance report.
     */
    public void generatePerformanceReport(Staff loggedInUser) {
        Scanner scanner = new Scanner(System.in);

        // Get the list of camps created by the staff
        ArrayList<Camp> userCreatedCamps = getCampsCreatedByUser(loggedInUser);

        if (userCreatedCamps.isEmpty()) {
            System.out.println("No camps available to generate a performance report.");
            return;
        }

        // Display a list of camps
        System.out.println("List of Camps Created by " + loggedInUser.getName() + ":");
        for (Camp camp : userCreatedCamps) {
            System.out.println(camp.getCampName());
        }

        // Prompt the staff to select a camp
        System.out.print("Enter the name of the camp to generate a performance report: ");
        String campName = scanner.nextLine();

        // Find the selected camp
        Camp selectedCamp = findCampByName(campName, userCreatedCamps);

        if (selectedCamp == null) {
            System.out.println("Camp not found.");
            return;
        }

        // Generate and display the performance report for all camp members
        if(displayCampPerformance(selectedCamp))
        {

        // Save the performance report to a file
        saveCampPerformanceReportToFile(selectedCamp);
        }
    }

    /**
     * Finds a camp by its name in the provided list of camps.
     *
     * @param campName The name of the camp to search for.
     * @param camps    The list of camps to search within.
     * @return The Camp object if found, otherwise null.
     */
    private Camp findCampByName(String campName, ArrayList<Camp> camps) {
        for (Camp camp : camps) {
            if (camp.getCampName().equalsIgnoreCase(campName)) {
                return camp;
            }
        }
        return null;
    }

    /**
     * Displays the performance report for a specific camp on the screen.
     * This method shows performance metrics for each member of the camp committee.
     *
     * @param camp The Camp object for which the performance report is generated.
     * @return True if the performance report was displayed, false otherwise.
     */
    private boolean displayCampPerformance(Camp camp) {
        System.out.println("Performance Report for Camp: " + camp.getCampName());

        // Get all members of the camp
        ArrayList<Student> campMembers = camp.getCampCommitteeMembers();

        if (campMembers.isEmpty()) {
            System.out.println("No members in the camp to generate a performance report.");
            return false;
        }

        // Display performance report for each camp member
        for (Student member : campMembers) {
            System.out.println("------------------------------------------------------");
            System.out.println("Member: " + member.getName() + " (" + member.getNtuNetworkId() + ")");
            System.out.println("Total Points: " + member.getPoints());
            System.out.println("Number of Replies: " + member.getNumberOfReplies());
            System.out.println("Number of Suggestions: " + member.NumberOfSuggestions());
            System.out.println("Number of Approved Suggestions: " + member.getnumberofApprovedSuggestions());
            // Add any other performance metrics you want to include
        }
        return true;
    }

    /**
     * Saves the performance report for a specific camp to a TXT file.
     * The report contains performance metrics for each member of the camp committee.
     *
     * @param camp The Camp object for which the performance report is generated and saved.
     */
    private void saveCampPerformanceReportToFile(Camp camp) {
        // Create a report file path
    	int i=0;
    	// Create a report file path
        while(true)
        {
        	try {
        		File myObj = new File(filePathToCMReport + camp.getCampName()+i+ "_report.txt");
        		if (myObj.createNewFile()) {
        			System.out.println("File created: " + myObj.getName());
        			break;
        		} else {
        			i++;
        		}
        	} catch (IOException e) {
        		System.out.println("An error occurred.");
        		e.printStackTrace();
        	}	
        }
        String filePath = filePathToCMReport + camp.getCampName() +i+ "_report.txt";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write camp performance data to the TXT file
            writer.write("Performance Report for Camp: " + camp.getCampName() + "\n");

            // Get all members of the camp
            ArrayList<Student> campMembers = camp.getCampCommitteeMembers();

            if (campMembers.isEmpty()) {
                writer.write("No members in the camp to generate a performance report.\n");
            } else {
                // Write performance report for each camp member to the file
                for (Student member : campMembers) {
                    writer.write("------------------------------------------------------\n");
                    writer.write("Member: " + member.getName() + " (" + member.getNtuNetworkId() + ")\n");
                    writer.write("Total Points: " + member.getPoints() + "\n");
                    writer.write("Number of Replies: " + member.getNumberOfReplies() + "\n");
                    writer.write("Number of Suggestions: " + member.NumberOfSuggestions() + "\n");
                    writer.write("Number of Approved Suggestions: " + member.getnumberofApprovedSuggestions() + "\n");
                    // Add any other performance metrics you want to include
                }
            }
            System.out.println("Performance report generated and saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving camp performance data to TXT: " + e.getMessage());
        }
    }
    
    ////////////////////////////Inquiry report by Staff///////////////////////////////////////  
    
    /**
     * Generates an inquiry report for a selected camp created by a staff member.
     * The report is displayed on the screen and can be saved to a file.
     *
     * @param loggedInUser The Staff user generating the inquiry report.
     */
    public void generateInquiryReport(Staff loggedInUser) {
        Scanner scanner = new Scanner(System.in);

        // Get the list of camps created by the staff
        ArrayList<Camp> userCreatedCamps = getCampsCreatedByUser(loggedInUser);

        if (userCreatedCamps.isEmpty()) {
            System.out.println("No camps available to generate an inquiry report.");
            return;
        }

        // Display a list of camps
        System.out.println("List of Camps Created by " + loggedInUser.getName() + ":");
        for (Camp camp : userCreatedCamps) {
            System.out.println(camp.getCampName());
        }

        // Prompt the staff to select a camp
        System.out.print("Enter the name of the camp to generate an inquiry report: ");
        String campName = scanner.nextLine();

        // Find the selected camp
        Camp selectedCamp = findCampByName(campName, userCreatedCamps);

        if (selectedCamp == null) {
            System.out.println("Camp not found.");
            return;
        }

        // Generate and display the inquiry report for the selected camp
        if(displayCampInquiryReport(selectedCamp)==1)
            saveCampInquiryReportToFile(selectedCamp);

        // Save the inquiry report to a file
    }

    /**
     * Displays the inquiry report for a specific camp on the screen.
     * This method shows inquiries made for the specified camp.
     *
     * @param camp The Camp object for which the inquiry report is generated.
     * @return 1 if the inquiry report was displayed, 0 otherwise.
     */
    private int displayCampInquiryReport(Camp camp) {
        System.out.println("Inquiry Report for Camp: " + camp.getCampName());

        // Get all inquiries for the camp
        ArrayList<Inquiry> allInquiries = getAllInquiries();

        if (allInquiries.isEmpty()) {
            System.out.println("No inquiries for the camp.");
            return 0;
        }
        int j=1;
        // Display inquiry report for each inquiry
        for (Inquiry inquiry : allInquiries) {
        	if(inquiry.getCamp().equals(camp.getCampName())) {
            System.out.println("------------------------------------------------------");
            System.out.println("Inquiry ID: " + j++);
            System.out.println("User: " + inquiry.getSender().getName() + " (" + inquiry.getSender().getNtuNetworkId() + ")");
            System.out.println("Message: " + inquiry.getMessage());
            // Add any other details you want to include
        	}
        }
        
        if(j==1)
        {
        	System.out.println("no Inquiries yet");
        	return 0;
        }
        return 1;
    }

    /**
     * Saves the inquiry report for a specific camp to a text file.
     * The report includes details of inquiries made for the camp, including sender information,
     * messages, replies (if any), and details of the replier.
     *
     * @param camp The Camp object for which the inquiry report is generated and saved.
     */
    private void saveCampInquiryReportToFile(Camp camp) {
    	int i=0;
        
        while(true)
        {
        	try {
        		File myObj = new File(filePathToCMReport + camp.getCampName()+i+ "_report.txt");
        		if (myObj.createNewFile()) {
        			System.out.println("File created: " + myObj.getName());
        			break;
        		} else {
        			i++;
        		}
        	} catch (IOException e) {
        		System.out.println("An error occurred.");
        		e.printStackTrace();
        	}
        }
        
        String filePath = filePathToInquiry + camp.getCampName() +i+ "_report.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write camp inquiry data to the TXT file
            writer.write("Inquiry Report for Camp: " + camp.getCampName() + "\n");

            ArrayList<Inquiry> allInquiries = getAllInquiries();

            if (allInquiries.isEmpty()) {
                writer.write("No inquiries for the camp.\n");
            } else {
            	int j=1;
                // Write inquiry report for each inquiry to the file
                for (Inquiry inquiry : allInquiries) {
                	if(inquiry.getCamp().equals(camp.getCampName())) {
                    writer.write("------------------------------------------------------\n");
                    writer.write("Inquiry ID: " + j++ + "\n");
                    writer.write("User: " + inquiry.getSender().getName() + "\n");
                    writer.write("Message: " + inquiry.getMessage() + "\n");
                    writer.write("Replied By: "+ inquiry.getReplierID() + "\n");
                    writer.write("Reply: "+ inquiry.getReply() + "\n");
                	}
                }
            }

            System.out.println("Inquiry report generated and saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving camp inquiry data to TXT: " + e.getMessage());
        }
    }
    
    ////////////////////////////camp report by Committee Member///////////////////////////////////////    
    
    /**
     * Generates a camp report for the logged-in student user based on their committee memberships.
     * It prompts the user to select a camp to generate a detailed report.
     *
     * @param loggedInUser The Student object representing the logged-in user.
     */
    public void generateCampReport(Student loggedInUser) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Camp> userCreatedCamps = getCampsAsCommitteeMember(loggedInUser);
    	if(userCreatedCamps.isEmpty())
    	{
    		System.out.println("No camps available to write reports on");
    		return;
    	}
    	for(Camp camp : userCreatedCamps)
    	{
    		System.out.println(camp.getCampName());
    	}
    	System.out.print("Enter the Camp to write a report: ");
    	String campToReport = scanner.nextLine();
        Camp campREPORT = findCampByName(campToReport);

        while(campREPORT==null){
            System.out.println("Invalid input, please enter a valid input");
            campToReport = scanner.nextLine();
            campREPORT = findCampByName(campToReport);
        }
    	
        generateTxtReport(campREPORT);
    }

    /**
     * Generates a text-based report for a specific camp based on various filtering options.
     * The report includes details such as camp information, attendees, or committee members as per the selected filter.
     * The user selects the filter option to generate the report accordingly.
     *
     * @param camp The Camp object for which the report needs to be generated.
     */
    private void generateTxtReport(Camp camp) {
    	Scanner scanner = new Scanner(System.in);
    	System.out.print("0. Generate the whole report\n");
    	System.out.print("Filter by:\n");
    	System.out.print("1. Camp Attendees\n");
    	System.out.print("2. Camp Members\n");

    	int op = scanner.nextInt();

        while(op!=1&&op!=2&&op!=0){
            System.out.println("Invalid input, please enter a valid input");
            op=scanner.nextInt();
        }
    	int i=0;
    	// Create a report file path
        while(true)
        {
        	try {
        		File myObj = new File(filePathToReport + camp.getCampName()+i+ "_report.txt");
        		if (myObj.createNewFile()) {
        			System.out.println("File created: " + myObj.getName());
        			break;
        		} else {
        			i++;
        		}
        	} catch (IOException e) {
        		System.out.println("An error occurred.");
        		e.printStackTrace();
        	}
        }
        String filePath = filePathToReport + camp.getCampName() +i+ "_report.txt";
        
        // Implement the report generation logic similar to your existing code
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write camp data to the TXT file
            writer.write("Camp Name: " + camp.getCampName() + "\n");
            writer.write("Dates: " + camp.getDates() + "\n");
            writer.write("Registration Closing Date: " + camp.getRegistrationClosingDate() + "\n");
            writer.write("User Group: " + camp.getUserGroup() + "\n");
            writer.write("Location: " + camp.getLocation() + "\n");
            writer.write("Total Slots: " + camp.getTotalSlots() + "\n");
            writer.write("Camp Committee Slots: " + camp.getTotalCampCommitteeSlots()+ "\n");
            writer.write("Description: " + camp.getDescription() + "\n");
            writer.write("Staff in Charge: " + camp.getStaffInCharge().getNtuNetworkId() + "\n");

            switch(op) {
            case 1:
            	writer.write("Camp Attendees: " + camp.getCampAttendees().toString() + "\n");
            	break;
            case 2:
            	writer.write("Camp Members: " + camp.getCampCommitteeMembers().toString() + "\n");
            	break;
            default:
            	writer.write("Camp Attendees: " + camp.getCampAttendees().toString() + "\n");
            	writer.write("Camp Members: " + camp.getCampCommitteeMembers().toString() + "\n");
            	break;
            }
            writer.write("Available Camp Member Slots: " + camp.getAvailableSlots() +"\n");
            writer.write("Available Camp Committee Slots: " + camp.getAvailableSlotsForCommitteeMember() +"\n");
            writer.write("\n"); // Add a separator between camps
            System.out.println("Report generated successfully.");
        } catch (IOException e) {
            System.err.println("Error saving camp data to TXT: " + e.getMessage());
        }
    }
    
    ////////////////////////////Inquiry report by Committee Member///////////////////////////////////////  
    
    /**
     * Generates an inquiry report for the logged-in student user based on their committee memberships.
     * It prompts the user to select a camp from their committee memberships to generate an inquiry report.
     * The report displays inquiries related to the selected camp by the user.
     *
     * @param loggedInUser The Student object representing the logged-in user.
     */
    public void generateInquiryCMReport(Student loggedInUser) {
        Scanner scanner = new Scanner(System.in);

        // Get the list of camps created by the staff
        ArrayList<Camp> userCreatedCamps = getCampsAsCommitteeMember(loggedInUser);

        if (userCreatedCamps.isEmpty()) {
            System.out.println("No camps available to generate an inquiry report.");
            return;
        }

        // Display a list of camps
        System.out.println("List of Camps Created by " + loggedInUser.getName() + ":");
        for (Camp camp : userCreatedCamps) {
            System.out.println(camp.getCampName());
        }

        // Prompt the staff to select a camp
        System.out.print("Enter the name of the camp to generate an inquiry report: ");
        String campName = scanner.nextLine();

        // Find the selected camp
        Camp selectedCamp = findCampByName(campName, userCreatedCamps);

        if (selectedCamp == null) {
            System.out.println("Camp not found.");
            return;
        }

        // Generate and display the inquiry report for the selected camp
        if(displayCampInquiryCMReport(selectedCamp)==1)
            saveCampInquiryReportToCMFile(selectedCamp);

        // Save the inquiry report to a file
    }

    /**
     * Displays the inquiry report for a specific camp on the console.
     *
     * @param camp The Camp object for which the inquiry report is generated.
     * @return 0 if there are no inquiries, 1 otherwise.
     */
    private int displayCampInquiryCMReport(Camp camp) {
        System.out.println("Inquiry Report for Camp: " + camp.getCampName());

        // Get all inquiries for the camp
        ArrayList<Inquiry> allInquiries = getAllInquiries();

        if (allInquiries.isEmpty()) {
            System.out.println("No inquiries for the camp.");
            return 0;
        }
        int j=1;
        // Display inquiry report for each inquiry
        for (Inquiry inquiry : allInquiries) {
        	if(inquiry.getCamp().equals(camp.getCampName())) {
        		System.out.println("------------------------------------------------------");
        		System.out.println("Inquiry ID: " + j++);
        		System.out.println("User: " + inquiry.getSender().getName() + " (" + inquiry.getSender().getNtuNetworkId() + ")");
        		System.out.println("Message: " + inquiry.getMessage());
        		// Add any other details you want to include
        	}
        }

        if(j==1)
        {
        	System.out.println("no Inquiries yet");
        	return 0;
        }
        
        return 1;
        
    }

    /**
     * Saves the camp's inquiry report to a file.
     *
     * @param camp The Camp object for which the inquiry report is generated and saved.
     */
    private void saveCampInquiryReportToCMFile(Camp camp) {
    	int i=0;
        
        while(true)
        {
        	try {
        		File myObj = new File(filePathToCMReport + camp.getCampName()+i+ "_report.txt");
        		if (myObj.createNewFile()) {
        			System.out.println("File created: " + myObj.getName());
  	        	break;
        		} else {
        			i++;
        		}
        	} catch (IOException e) {
        		System.out.println("An error occurred.");
        		e.printStackTrace();
        	}
        	
        }
        String filePath = filePathToInquiry + camp.getCampName() +i+ "_report.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write camp inquiry data to the TXT file
            writer.write("Inquiry Report for Camp: " + camp.getCampName() + "\n");

            ArrayList<Inquiry> allInquiries = getAllInquiries();

            if (allInquiries.isEmpty()) {
                writer.write("No inquiries for the camp.\n");
            } else {
            	int j=1;
                // Write inquiry report for each inquiry to the file
                for (Inquiry inquiry : allInquiries) {
                	if(inquiry.getCamp().equals(camp.getCampName())) {
                    writer.write("------------------------------------------------------\n");
                    writer.write("Inquiry ID: " + j++ + "\n");
                    writer.write("User: " + inquiry.getSender().getName() + "\n");
                    writer.write("Message: " + inquiry.getMessage() + "\n");
                    writer.write("Replied By: "+ inquiry.getReplierID() + "\n");
                    writer.write("Reply: "+ inquiry.getReply() + "\n");
                	}
                }
            }

            System.out.println("Inquiry report generated and saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving camp inquiry data to TXT: " + e.getMessage());
        }
    }
}







