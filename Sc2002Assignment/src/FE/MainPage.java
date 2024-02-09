package FE;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import BE.Server;
import BE.Staff;
import BE.Student;
import BE.SuggestStatus;
import BE.Suggestion;
import BE.User;
import BE.Camp;
import BE.Inquiry;

//remember to use server(BE) to generate the server(server will just be arraylist of Users  storing details)

/**
 * Represents the main page of the Campus Application and Management System (CAMs).
 * Handles user login and menu display for different user roles.
 */
public class MainPage {

    private static Server serverInstance;

	 /**
     * The file path to CAMs data files.
     * It is set based on the current working directory of the application.
     */
	private static String FILEPATHs = new File("").getAbsolutePath().replace("\\", "/");
    /**
     * The main method to start the Campus Application and Management System (CAMs).
     *
     * @param args Command-line arguments (not used in this application)
     */
	public static void main(String[] args) 
	{
		Scanner scanner = new Scanner(System.in);
		
		
		while(true) {		//so the app continues and multiple user can login
			
		System.out.println("-------------Camp Application and Management System (CAMs).----------------");
		System.out.println("---------------------------------------------------------------------------");
		System.out.println("UserID:");
		String UserID= scanner.nextLine();
		
		System.out.println("Password:");
		String Password= scanner.nextLine();
		
		serverInstance = new Server(FILEPATHs);
		User loggedInUser = serverInstance.login(UserID, Password);	
		
		if (loggedInUser != null) {
			if (Password.equals("password")) {
	            System.out.println("Your password is set to the default 'password'. Please change it for security reasons.");
	            while(Password.equals("password")) {
	            	System.out.println("Enter new Password:");
	            	Password = scanner.nextLine();
	            }
	            serverInstance.ChangeAndSavePasswords(loggedInUser, Password);
	        }
			if (loggedInUser instanceof Student) {
				//User is Student
				boolean k=true;
				Student currentStudent = (Student) loggedInUser;

		        System.out.println("Login successful. User is a student.Welcome: " + loggedInUser.getName());
		        while(k) 
		        {
		        	System.out.println("\n");
		        	currentStudent.displayMenu();
		        	int choice = scanner.nextInt();
		        	scanner.nextLine();//clear the \n
                	System.out.println("--------------------------------");
		            switch (choice) {
		            case 1:
		                // View available camps by faculty and visibility toggle
		                ArrayList<Camp> availableCamps = serverInstance.getAllAvailableCamp(loggedInUser);
		                		              
		                if (availableCamps.isEmpty()) {
		                    System.out.println("There are no available camps for your faculty.");
		                }
		                else {
		                    for (Camp camp : availableCamps) 
		                    {
			                    if(camp.isVisible())
			                    {
				                    {
				                        System.out.println("Camp Name: " + camp.getCampName());
				                        System.out.println("Dates: " + camp.getDates());
				                        System.out.println("Location: " + camp.getLocation());
				                        System.out.println("Camp attendee Vaccancy: " + camp.getAvailableSlots());
				                        System.out.println("Camp Committee Vaccancy: " + camp.getAvailableSlotsForCommitteeMember());
				                        System.out.println("--------------------------------");
				                    }
			                    }
		                    }
		                }
		                break;
		               
		            case 2:
		            	//Register as Attendee or Member
		            	availableCamps= serverInstance.getAllAvailableCamp(loggedInUser);
		            	System.out.println("Available camps for you:");
		                if (availableCamps.isEmpty()) {
		                    System.out.println("There are no available camps for your faculty.");
		                    break;
		                }
		                else {
		                    for (Camp camp : availableCamps) {
								if(camp.isVisible()){
									System.out.println("Camp Name: " + camp.getCampName());
									System.out.println("Dates: " + camp.getDates());
									System.out.println("Location: " + camp.getLocation());
									System.out.println("Slots (attendee):" + camp.getAvailableSlots()+" (committee):"+camp.getAvailableSlotsForCommitteeMember());
									System.out.println("--------------------------------");
								 }
		                    }
		                }
		            	System.out.println("Enter camp name you wish to join:");
		            	
		            	String campName = scanner.nextLine();
		            	boolean exit = false;
		            	Camp targetcamp = serverInstance.findCampByName(campName);
		            	
		            	//check if student chose camps from those available for him
		            	boolean campExists = false;
		            	for (Camp camp : availableCamps) {
		            	    if (camp.getCampName().equalsIgnoreCase(campName)) {
		            	        campExists = true;
		            	        break;
		            	    }
		            	}
		            	
		            	if(!campExists)
		            	{
		            		System.out.println("You cant register for this camp");
		            		break;
		            	}
		            	
		            	//check if student withdraw from chosen camp before
		            	for (Student s:targetcamp.getWithdrawnAttendees())
		            	{
		            		if(s.getNtuNetworkId().equals(loggedInUser.getNtuNetworkId()))
		            		{
		            			System.out.println("Cannot register for this camp as you have withdrawn from it previously");
		            			exit=true;
		            			break;
		            		}
		            	}
		            	
		            	if (exit)
		            	{
		            		break;
		            	}
		            	
		            	System.out.println("Do you wish to join as a camp attendee or committee member?");
		            	System.out.println("Enter 1 for camp attendee and 0 for committee member");
		            	int ch = scanner.nextInt();
		            	scanner.nextLine(); 
		            	boolean isAlreadyAttending = serverInstance.isStudentAlreadyAttending((Student) loggedInUser, campName);

		            	if (ch == 0) {
		            	    if (currentStudent.isCommitteeMember()) {
		            	        System.out.println("You are already a committee member.");
		            	    }
		            	    else {
		            	        // Handle joining as a committee member
		            	        if (serverInstance.registerAsCommitteeMemeber((Student) loggedInUser, campName)) {
		            	            System.out.println("Registered as Committee Member for Camp " + campName);
		            	            Student student =(Student) loggedInUser;
		            	            student.setCommitteeMember(true);
		            	        }
		            	    }
		            	} 
		            	else if (ch == 1) {
		            	    if (isAlreadyAttending) {
		            	        System.out.println("You are already attending this camp.");
		            	    }
		            	    else {
		            	        // Handle joining as a camp attendee
		            	        if (serverInstance.registerAsCampAttendee((Student) loggedInUser, campName)) {
		            	            System.out.println("\nRegistered as Camp Attendee for Camp " + campName);
		            	        }
		            	    }
		            	} 
		            	else {
		            	    System.out.println("Invalid choice.");
		            	}
		                break;
		            
		            case 3:
		            	//Submit enquiry for a camp
		            	Scanner scanner1 = new Scanner(System.in);
		            	
		            	// Prompt the user to select a camp
		            	System.out.println("Available camps for inquiry:");
		            	
		            	ArrayList<Camp> availableCamps1 = serverInstance.getAllAvailableCamps((Student) loggedInUser);
		            	for (Camp camp : availableCamps1) {
		            	    System.out.println(camp.getCampName());
		            	}
		            	System.out.print("Enter the name of the camp you want to inquire about: ");
		            	String campName1 = scanner1.nextLine();

		            	// Find the selected camp
		            	Camp selectedCamp = null;
		            	for (Camp camp : availableCamps1) {
		            	    if (camp.getCampName().equals(campName1)) {
		            	        selectedCamp = camp;
		            	        break;
		            	    }
		            	}

		            	if (selectedCamp == null) {
		            	    System.out.println("Invalid camp name. Inquiry not created.");
		            	    break;
		            	}

		            	//write enquiry
		            	System.out.print("Enter your inquiry message: ");
		            	String inquiryMessage = scanner1.nextLine();
		            	Inquiry inquiry = new Inquiry(loggedInUser, inquiryMessage, campName1);
		            	selectedCamp.addInquiry(loggedInUser, inquiryMessage);
		            	serverInstance.storeInquiry(inquiry);
		            	if (loggedInUser instanceof Student) {
		            	    currentStudent.addInquiry(inquiry);
		            	}
		            	serverInstance.saveAllStudentInquiriesToFile();
		            	System.out.println("Inquiry added successfully.");
		            	break;

		            case 4:
		            	// View Camps Attending
		            	ArrayList<Camp> attendingCamps = serverInstance.getCampsAttending(currentStudent);
		            	if (attendingCamps.isEmpty()) {
		            	    System.out.println("You are not attending any camps.");
	            	        System.out.println("--------------------------------");
		            	} else {
		            	    System.out.println("Camps you are attending:");
		            	    for (Camp camp : attendingCamps) {
		            	        System.out.println("Camp Name: " + camp.getCampName());
		            	        System.out.println("Dates: " + camp.getDates());
		            	        System.out.println("Location: " + camp.getLocation());
		            	        // Add more camp details as needed
		            	        System.out.println("--------------------------------");
		            	    }
		            	}

		            	// View Camps as Committee Member
		            	ArrayList<Camp> committeeCamps = serverInstance.getCampsAsCommitteeMember(currentStudent);
		            	if (committeeCamps.isEmpty()) {
		            	    System.out.println("You are not a committee member of any camps.");
	            	        System.out.println("--------------------------------");
		            	} else {
		            	    System.out.println("Camps you are a committee member of:");
		            	    for (Camp camp : committeeCamps) {
		            	        System.out.println("Camp Name: " + camp.getCampName());
		            	        System.out.println("Dates: " + camp.getDates());
		            	        System.out.println("Location: " + camp.getLocation());
		            	        System.out.println("--------------------------------");
		            	    }
		            	}
		            	break;
		            case 5:
		            	//View, Edit and delete to enquiries
		            	User currentUser = loggedInUser;
		            	Student currentStudent1 = (Student) currentUser;
		            	System.out.println("--------------");
		                System.out.println("a. View Inquiries");
		                System.out.println("b. Edit an Inquiry");
		                System.out.println("c. Delete an Inquiry");
		                System.out.println("d. Exit");

		                String s = scanner.next();
		                scanner.nextLine();

		                switch (s) {
		                    case "a":
		                        // View Inquiries
		                        int index = 1;
		                        for (Inquiry inquiry1 : currentStudent1.getInquiries()) {
		                            System.out.println(index + ". " + inquiry1.getMessage());
		                            System.out.println("Camp:  " + inquiry1.getCamp());
		                            System.out.println("Reply: " + inquiry1.getReply());
		                            index++;
		                        }
		                        break;

		                    case "b":
		                        // Edit an Inquiry
		                        System.out.println("Enter the index of the inquiry you want to edit:");
		                        int editIndex = scanner.nextInt();
		                        scanner.nextLine();

		                        if (editIndex >= 1 && editIndex <= currentStudent1.getInquiries().size()) {
		                            Inquiry selectedInquiry = currentStudent1.getInquiries().get(editIndex - 1);
		                            
		                            if(!selectedInquiry.getReply().equals("null")&&!selectedInquiry.getReply().equals(null))
		                            {
		                            	System.out.println("cannot edit inquiry");
		                            	break;
		                            }
		                            System.out.println("Enter the updated message:");
		                            String newMessage = scanner.nextLine();
		                            currentStudent1.updateInquiry(selectedInquiry, newMessage);

		    		            	serverInstance.saveAllStudentInquiriesToFile();
		                            System.out.println("Inquiry updated successfully.");
		                        } else {
		                            System.out.println("Invalid index.");
		                        }
		                        break;

		                    case "c":
		                        // Delete an Inquiry
		                        System.out.println("Enter the index of the inquiry you want to delete:");
		                        int deleteIndex = scanner.nextInt();
		                        scanner.nextLine();

		                        if (deleteIndex >= 1 && deleteIndex <= currentStudent1.getInquiries().size()) {
		                            Inquiry selectedInquiry = currentStudent1.getInquiries().get(deleteIndex - 1);
		                            
		                            if(!selectedInquiry.getReply().equals("null")&&!selectedInquiry.getReply().equals(null))
		                            {
		                            	System.out.println("cannot delete inquiry");
		                            	break;
		                            }
		                            System.out.println("Are you sure you want to delete this inquiry? (Y/N)");
		                            String confirm = scanner.nextLine();
		                            if (confirm.equalsIgnoreCase("Y")) {
		                                currentStudent1.removeInquiry(selectedInquiry);
		                                System.out.println("Inquiry deleted.");
		                            }
		                        } else {
		                            System.out.println("Invalid index.");
		                        }

				            	serverInstance.saveAllStudentInquiriesToFile();
		                        break;

		                    case "d":
		                    	//Go back
		                        break;

		                    default:
		                        System.out.println("Invalid choice. Please select a valid option.");
		                        break;
		                }
		            	break;
		            	
		            case 6:
		            	    // Withdraw from attending a camp
		            		Scanner scanner3 = new Scanner(System.in);
		            	    System.out.println("Enter the camp name to withdraw from:");
		            	    String withdrawCampName = scanner3.nextLine();
		            	    
		            	    int p=0;
		            	    ArrayList<Camp> committeeCamps1 = serverInstance.getCampsAsCommitteeMember((Student) loggedInUser);
		                	for (Camp camp : committeeCamps1) {
		            	        if(camp.getCampName().equals(withdrawCampName)){
		            	        		System.out.println("You are a Member of the camp");
		            	        		p=1;
		            	        }
		                	}
		            	    if (p==0&&serverInstance.withdrawFromCampAttendee((Student) loggedInUser, withdrawCampName)) {
		            	        System.out.println("You have successfully withdrawn from the camp.");
		            	    } else {
		            	        System.out.println("Failed to withdraw from the camp. Please check the camp name.");
		            	    }
		            	break;
		            	
		            case 7: 
		            	//Account Menu
		            	int choice2 = 1;
	            		while(choice2!=3&&choice2!=2) {	//Menu displayed until logout or go back
	            		System.out.println("------------- Account Menu -------------");
        				System.out.println("1. Change Password");
        				System.out.println("2. Logout");
        				System.out.println("3. Go Back");
        				choice2 = scanner.nextInt();
        				scanner.nextLine();
        				switch(choice2) {
        					
        					case 1:
        						//Change Password
        						System.out.println("Enter new Password");
                				String password = scanner.nextLine();
                				serverInstance.ChangeAndSavePasswords(loggedInUser, password);
                				break;
                				
        					case 2:
        						//Log out
	            				System.out.println("Logging out");
	            				k=false;
	            				break;
	            				
                			default:
                				break;
        					}
        				}
	            		break;
	            	
		            case 8:
		            	//only visible to Committee Member
		            	if (loggedInUser instanceof Student) {
		            		int choice1 = 1;
		            		while(choice1!=5) {
		            			System.out.println("------------- Committee Member Menu -------------");
		            			System.out.println("1. Submit suggestions");
		            			System.out.println("2. View and Reply enquiry");
		            			System.out.println("3. View, Edit and delete to Suggestions");
		            			System.out.println("4. Generate report");
		            			System.out.println("5. Go back");
		            			choice1 = scanner.nextInt();
		            			
		            			switch(choice1) {
		            			case 1:
		            				//Submit suggestion
		            				Scanner scanner11 = new Scanner(System.in);
		    		            	
		    		            	// Prompt the user to select a camp
		    		            	System.out.println("Available camps for suggestions:");
		    		            	
		    		            	ArrayList<Camp> availableCamps11 = serverInstance.getCampsAsCommitteeMember((Student) loggedInUser);
		    		            	for (Camp camp : availableCamps11) {
		    		            	    System.out.println(camp.getCampName());
		    		            	}
		    		            	System.out.print("Enter the name of the camp you want to submit suggestions about: ");
		    		            	String campName11 = scanner11.nextLine();

		    		            	// Find the selected camp
		    		            	Camp selectedCamp1 = null;
		    		            	for (Camp camp : availableCamps11) {
		    		            	    if (camp.getCampName().equals(campName11)) {
		    		            	        selectedCamp1 = camp;
		    		            	        break;
		    		            	    }
		    		            	}

		    		            	if (selectedCamp1 == null) {
		    		            	    System.out.println("Invalid camp name.");
		    		            	    break;
		    		            	}

		    		            	System.out.print("Enter your Suggestion message: ");
		    		            	String suggestionMessage = scanner11.nextLine();

		    		            	Suggestion suggestion = new Suggestion(loggedInUser, suggestionMessage, campName11,SuggestStatus.pending);
		    		            	selectedCamp1.addSuggestion(loggedInUser, suggestionMessage);
		    		            	serverInstance.storeSuggestion(suggestion);

		    		            	if (loggedInUser instanceof Student) {
		    		            	    
		    		            	    currentStudent.addSuggestion(suggestion);
		    		            	}
		    		            	serverInstance.saveAllStudentSuggestionsToFile();
		    		            	
		    		            	currentStudent.addPointsForSuggestion();
		    		            	serverInstance.updatePointsFile();//(1)

		    		            	System.out.println("Suggestion added successfully.");
		            				break;
		            				
		            			case 2:
		            				//View and reply to enquiries
		            				serverInstance.viewAndReplyToInquiry((Student)loggedInUser);
		                        	serverInstance.saveAllStudentInquiriesToFile();
		                        	serverInstance.updatePointsFile();//(2)
		                            break;
		                            
		            			case 3:
		            				//View, Edit and delete suggestion
		            				User currentUser1 = loggedInUser;
		    		            	Student currentStudent11 = (Student) currentUser1;
		    		            	System.out.println("--------------");
		    		                System.out.println("a. View Suggestion");
		    		                System.out.println("b. Edit an Suggestion");
		    		                System.out.println("c. Delete an Suggestion");
		    		                System.out.println("d. Exit");

		    		                String s1 = scanner.next();
		    		                scanner.nextLine();

		    		                switch (s1) {
		    		                    case "a":
		    		                        // View Suggestions
		    		                        int index = 1;
		    		                        for (Suggestion suggestion1 : currentStudent11.getSuggestions()) {
		    		                            System.out.println(index + ". " + suggestion1.getMessage());
		    		                            System.out.println("Camp:  " + suggestion1.getCamp());
		    		                            System.out.println("Approved: " + suggestion1.getStatus());
		    		                            index++;
		    		                        }
		    		                        break;

		    		                    case "b":
		    		                        // Edit a Suggestion
		    		                        System.out.println("Enter the index of the Suggestion you want to edit:");
		    		                        int editIndex = scanner.nextInt();
		    		                        scanner.nextLine();

		    		                        if (editIndex >= 1 && editIndex <= currentStudent11.getSuggestions().size()) {
		    		                        	Suggestion selectedSuggestion = currentStudent11.getSuggestions().get(editIndex - 1);
		    		                        	
		    		                        	if(!selectedSuggestion.getStatus().equals(SuggestStatus.pending))
		    		                        	{
		    		                        		System.out.println("cannot edit suggestion as its been "+selectedSuggestion.getStatus());
		    		                        		break;
		    		                        	}
		    		                            System.out.println("Enter the updated message:");
		    		                            String newMessage = scanner.nextLine();
		    		                            currentStudent11.updateSuggestion(selectedSuggestion, newMessage);

		    		    		            	serverInstance.saveAllStudentSuggestionsToFile();
		    		                            System.out.println("Suggestion updated successfully.");
		    		                        } else {
		    		                            System.out.println("Invalid index.");
		    		                        }
		    		                        break;

		    		                    case "c":
		    		                        // Delete a Suggestion
		    		                        System.out.println("Enter the index of the Suggestion you want to delete:");
		    		                        int deleteIndex = scanner.nextInt();
		    		                        scanner.nextLine();

		    		                        if (deleteIndex >= 1 && deleteIndex <= currentStudent11.getSuggestions().size()) {
		    		                        	Suggestion selectedSuggestion = currentStudent11.getSuggestions().get(deleteIndex - 1);
		    		                        	if(!selectedSuggestion.getStatus().equals(SuggestStatus.pending))
		    		                        	{
		    		                        		System.out.println("cannot delete suggestion as its been "+selectedSuggestion.getStatus());
		    		                        		break;
		    		                        	}
		    		                            System.out.println("Are you sure you want to delete this Suggestion? (Y/N)");
		    		                            String confirm = scanner.nextLine();
		    		                            if (confirm.equalsIgnoreCase("Y")) {
		    		                                currentStudent11.removeSuggestion(selectedSuggestion);
		    		                                currentStudent.removePointForDeleteSuggestion();
		    		                                System.out.println("Suggestion deleted.");
		    		                            }
		    		                        } else {
		    		                            System.out.println("Invalid index.");
		    		                        }

		    				            	serverInstance.saveAllStudentSuggestionsToFile();
		    				            	serverInstance.updatePointsFile();//(3)
		    		                        break;

		    		                    case "d":
		    		                        break;

		    		                    default:
		    		                        System.out.println("Invalid choice. Please select a valid option.");
		    		                        break;
		    		                }
		    		            	break;
		    		            	
		            			case 4:
		            				//Make report
		            				System.out.println("------------- Report Menu -------------");
		            				System.out.println("1. Generate Camp report");
		            				System.out.println("2. Generate Students Enquiry Report");
		            				System.out.println("3. Go Back");
		            				int rc = scanner.nextInt();            			
		            				switch(rc) {
				                		case 1:
				                			//Camp report
				                			serverInstance.generateCampReport((Student) loggedInUser);
				                			break;
				                			
				                		case 2:
				                			//Enquiry report
				                			serverInstance.generateInquiryCMReport((Student) loggedInUser);
				                			break;
				                			
				                		case 3:
				                			break;
				                			
				                		default:
				                			break;
		            				}
		            			default:
		            				break;
		            			}
		            		}
		            	}
		            	else {
		                    break; 
		                }
		            	break;
		            	
		            }
		            
		        }
		        
			} 
			else if (loggedInUser instanceof Staff) {
				//User is Staff
		        System.out.println("Login successful. User is a staff.Welcome: " + loggedInUser.getName());
				boolean k=true;
				Staff currentStaff = (Staff) loggedInUser;
		        while(k) 
		        {
		        	System.out.println("\n");
		        	currentStaff.displayMenu();
		        	
		        	int choice = scanner.nextInt();
		        	System.out.println("--------------------------------");
		        	String clear = scanner.nextLine();

		            switch (choice) {
		                case 1:
		                	//create new camp
		                	System.out.println("Enter the camp name: ");
		                    String campName = scanner.nextLine();
		                    
		                    System.out.println("Enter the dates (comma-separated): ");
		                    String datesInput = scanner.nextLine();
		                    ArrayList<String> dates = new ArrayList<>(Arrays.asList(datesInput.split(",")));
		                    
		                    System.out.println("Enter the registration closing date: ");
		                    String registrationClosingDate = scanner.nextLine();
		                    
		                    System.out.println("Enter the user group(own school or whole of NTU): ");
		                    String userGroup = scanner.nextLine();
		                    
		                    System.out.println("Enter the location: ");
		                    String location = scanner.nextLine();
		                    
		                    System.out.println("Enter the total slots: ");
		                    int totalSlots = scanner.nextInt();
		                    
		                    System.out.println("Enter the camp committee slots: ");
		                    int campCommitteeSlots = scanner.nextInt();
		                    
		                    scanner.nextLine(); // Consume newline
		                    
		                    System.out.println("Enter the description: ");
		                    String description = scanner.nextLine();
		                    serverInstance.createCamp((Staff) loggedInUser, campName, dates, registrationClosingDate, userGroup, location, totalSlots,description,campCommitteeSlots,true,totalSlots,campCommitteeSlots);
		                    
		                    
		                    break;
		                case 2:
		                    // Edit an existing camp
		                	serverInstance.displayCampInfo(loggedInUser);
		                    System.out.println("Enter the camp name of the camp you want to edit: ");
		                    String campToEdit = scanner.nextLine();
		                    
		                    Camp campEDIT = serverInstance.findCampByName(campToEdit);
		                    if (campEDIT != null) {
		                    	if(campEDIT.getStaffInCharge().equals(loggedInUser))
		                    	{
			                    	System.out.println("Enter the new camp name: ");
			                    	String newCampName = scanner.nextLine();
			                    
			                    	System.out.println("Enter the dates (comma-separated): ");
			                    	String newdatesInput = scanner.nextLine();
			                    	ArrayList<String> newdates = new ArrayList<>(Arrays.asList(newdatesInput.split(",")));
			                    
			                    	System.out.println("Enter the new registration closing date: ");
			                    	String newRegistrationClosingDate = scanner.nextLine();
			                    
			                    	System.out.println("Enter the new user group (own school or whole of NTU): ");
			                    	String newUserGroup = scanner.nextLine();
			                    
			                    	System.out.println("Enter the new location: ");
			                    	String newLocation = scanner.nextLine();
			                    
			                    	System.out.println("Enter the new total slots: ");
			                    	int newTotalSlots = scanner.nextInt();
			                    
			                    	System.out.println("Enter the new camp committee slots: ");
			                    	int newCampCommitteeSlots = scanner.nextInt();
			                    
			                    	scanner.nextLine(); // Consume newline
			                    
			                    	System.out.println("Enter the new description: ");
			                    	String newDescription = scanner.nextLine();
			                    
			                    	serverInstance.editCamp(campEDIT, newCampName, newdates, newRegistrationClosingDate, newUserGroup, newLocation, newTotalSlots, newCampCommitteeSlots, newDescription);
			                    	break;
		                    	}
		                    	else {
		                    		System.out.println("You did not create camp" + campEDIT.getCampName()+" cannot edit it");
		                    		break;
		                    	}
		                    }
		                    else
		                    {
		                    	System.out.println("Camp not found");

		                    	break;
		                    }
		                    
		                case 3:
		                    // Delete a camp
		                	serverInstance.displayCampInfo(loggedInUser);
		                    
		                    System.out.println("Enter the camp name of the camp you want to delete(can only delete camps that do not have camp attendees or committee members registered): ");
		                    String campToDelete = scanner.nextLine();

		                    Camp campDelete = serverInstance.findCampByName(campToDelete);

		                    if (campDelete != null) {
		                    	if(serverInstance.GotPeoplealreadyRegistered(campDelete))
		                    	{
		                    		System.out.println("cannot delete as got people registered already");
		                    		break;
		                    	}
		                        // Camp found, proceed with deletion
		                        boolean deletionResult = serverInstance.deleteCamp(campDelete,loggedInUser);

		                        if (deletionResult) {
		                            System.out.println("Camp deleted successfully.");
		                        } else {
		                            System.out.println("Failed to delete the camp.");
		                        }
		                    } else {
		                        System.out.println("Camp not found.");
		                    }
		                    break;
		                    
		                case 4:
		                    // Toggle camp visibility
		                	serverInstance.displayCampInfo(loggedInUser);
		                	 System.out.println("Enter the camp name of the camp you want to toggle visibility for(can only delete camps that do not have camp attendees or committee members registered): ");
			                    String campToToggleVisibility = scanner.nextLine();

			                    Camp campToToggle = serverInstance.findCampByName(campToToggleVisibility);
			                    if (campToToggle != null) {	
			                    	if(serverInstance.GotPeoplealreadyRegistered(campToToggle))
			                    	{
			                    		System.out.println("cannot toggle visibility as got people registered already");
			                    		break;
			                    	}

			                    	if(!campToToggle.getStaffInCharge().equals(loggedInUser)) {
			                    		System.out.println("Camp not available for you to toggle");
			                    		break;
			                    	}
			                    			                    	
			                    	System.out.println("Current visibility: " + (campToToggle.isVisible() ? "Visible" : "Not Visible"));
			                    
			                        
			                    	System.out.println("Do you want to toggle visibility? (yes/no): ");
			                    	String toggleChoice = scanner.nextLine().toLowerCase();
			                    
			                    	switch (toggleChoice) 
			                    	{
			                        	case "yes":
			                            	serverInstance.toggleVisibility((Staff) loggedInUser, campToToggle);
			                            
			                            	System.out.println("Visibility toggled successfully.");
			                            	break;
			                        	case "no":
			                            	System.out.println("Visibility not changed.");
			                            	break;
			                        	default:
			                            	System.out.println("Invalid choice. Visibility not changed.");
			                            	break;
			                    	}
			                    }
			                    else 
			                    {
			                    System.out.println("Camp not found.");
			                    }
			                break;
			                
		                case 5:
		                    // View all camps
		                    ArrayList<Camp> allCamps = serverInstance.getAllCamps();
		                    
		                    if (allCamps.isEmpty()) {
		                        System.out.println("There are no camps to display.");
		                    } else {
		                        System.out.println("List of all camps:");
		                        for (Camp camp : allCamps) {
		                            System.out.println("Camp Name: " + camp.getCampName());
		                            System.out.println("Dates: " + camp.getDates());
		                            System.out.println("Location: " + camp.getLocation());
		                            // Add more camp details as needed
		                            System.out.println("--------------------------------");
		                        }
		                    }
		                    break;
		                    
		                case 6:
		                    // View camps created by the logged-in user
		                    ArrayList<Camp> userCreatedCamps = serverInstance.getCampsCreatedByUser(loggedInUser);

		                    if (userCreatedCamps.isEmpty()) {
		                        System.out.println("You haven't created any camps yet.");
		                    } else {
		                        System.out.println("Camps created by " + loggedInUser.getName() + ":\n");
		                        for (Camp camp : userCreatedCamps) {
		                            System.out.println("Camp Name: " + camp.getCampName());
		                            System.out.println("Dates: " + camp.getDates());
		                            System.out.println("Location: " + camp.getLocation());
		                            
		                            // Add more camp details as needed
		                            System.out.println("--------------------------------");
		                        }
		                    }
		                    break;
		   
		                case 7:
		                    // View and reply to inquiries
		                	// Assuming loggedInUser is the currently logged-in staff user
		                	serverInstance.viewAndReplyToInquiries((Staff) loggedInUser);
			            	serverInstance.saveAllStudentInquiriesToFile();
		                    break;
		                    
		                case 8:
		                	//View and Approve Suggestions
		                	serverInstance.viewAndApproveSuggestions((Staff) loggedInUser);
			            	serverInstance.saveAllStudentSuggestionsToFile();
			            	serverInstance.updatePointsFile();
			            	break;
			            	
		                case 9:
		                	//Create report
		                	System.out.println("------------- Report Menu -------------");
            				System.out.println("1. Generate Camp report");
            				System.out.println("2. Generate Camp committee performance report ");
            				System.out.println("3. Generate Students Enquiry Report");
            				System.out.println("4. Go Back");
            				int rc = scanner.nextInt();            			
            				switch(rc) {
		                		case 1:
		                			//Camp Report
		                			serverInstance.generateCampReport((Staff) loggedInUser);
		                			break;
		                			
		                		case 2:
		                			//Performance report
		                			serverInstance.generatePerformanceReport((Staff) loggedInUser);
		                			break;
		                			
		                		case 3:
		                			//Enquiry report
		                			serverInstance.generateInquiryReport((Staff) loggedInUser);
		                			break;
		                			
		                		case 4:
		                			//Go back
		                			break;
		                			
		                		default:
		                			break;
            				}
		                    break;
		                    
		                case 10:
		                	int choice2 = 1;
		            		while(choice2!=3&&choice2!=2) {	//Menu displayed until logout or go back
		            		System.out.println("------------- Account Menu -------------");
            				System.out.println("1. Change Password");
            				System.out.println("2. Logout");
            				System.out.println("3. Go Back");
            				choice2 = scanner.nextInt();  
							scanner.nextLine();         			
            				switch(choice2) {
            					case 1:
            						//new password
            						System.out.println("Enter new Password");
                    				String password = scanner.nextLine();
                    				serverInstance.ChangeAndSavePasswords(loggedInUser, password);
                    				break;
                    				
            					case 2:
            						//log out
		            				System.out.println("Logging out");
		            				scanner.nextLine();
		            				k=false;
                    				break;
                    				
            					case 3:
            						//go back
            						break;
            						
                    			default:
                    				break;
            					}
            				}
		            		break;
		            	
		            	default:
		            		break;
		            }
		        }
		    }
		}
		else {
            System.out.println("Login failed. Please check your credentials.");
        }
		for(int i=0;i<10;i++)
			System.out.println("\n");
		}
	}
	
	
	/**
	 * Displays inquiries made by the current user.
	 *
	 * @param currentUser The user whose inquiries need to be displayed.
	 */
	public static void viewUserInquiries(User currentUser) {
	    System.out.println("Your Inquiries:");
	    for (Inquiry inquiry : serverInstance.getInquiries()) {
	        if (inquiry.getSender() == currentUser) {
	            System.out.println("Message: " + inquiry.getMessage());
	            System.out.println("Reply: " + inquiry.getReply());
	            System.out.println("--------------");
	        }
	    }
	}
}

