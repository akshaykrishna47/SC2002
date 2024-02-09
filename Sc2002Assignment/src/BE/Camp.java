package BE;

import java.util.ArrayList;

/**
 * Represents a camp entity with various details and functionalities.
 */
public class Camp {
    /**
     * The name of the camp.
     */
    private String campName;

    /**
     * Dates associated with the camp.
     */
    private ArrayList<String> dates;

    /**
     * Closing date for camp registration.
     */
    private String registrationClosingDate;

    /**
     * User group associated with the camp.
     */
    private String userGroup;

    /**
     * Location of the camp.
     */
    private String location;

    /**
     * Total available slots for camp attendees.
     */
    private int totalSlots;

    /**
     * Total available slots for camp committee members.
     */
    private int TotalcampCommitteeSlots; // Max 10

    /**
     * Description of the camp.
     */
    private String description;

    /**
     * Staff member in charge of the camp.
     */
    private Staff staffInCharge; // A reference to the staff member in charge

    /**
     * List of students attending the camp.
     */
    private ArrayList<Student> campAttendees;

    /**
     * List of students in the camp committee.
     */
    private ArrayList<Student> campCommitteeMembers;

    /**
     * List of suggestions made for the camp.
     */
    private ArrayList<Suggestion> suggestions;

    /**
     * List of inquiries made for the camp.
     */
    private ArrayList<Inquiry> inquiries;

    /**
     * Visibility status of the camp.
     */
    private boolean visible;

    /**
     * Total number of camp members.
     */
    private int campMembers;

    /**
     * Total number of camp committee members.
     */
    private int campCommittees;

    /**
     * List of withdrawn camp attendees.
     */
    private ArrayList<Student> withdrawnAttendees;

    /**
     * Constructs a Camp object with provided details.
     *
     * @param campName               The name of the camp.
     * @param dates                  Dates associated with the camp.
     * @param registrationClosingDate Closing date for camp registration.
     * @param userGroup              User group associated with the camp.
     * @param location               Location of the camp.
     * @param totalSlots             Total available slots for camp attendees.
     * @param description            Description of the camp.
     * @param staffInCharge          Staff member in charge of the camp.
     * @param committeeSlots         Total available slots for camp committee members.
     * @param b                      Visibility status of the camp.
     * @param committee_slots        Total available slots for camp committee members.
     * @param attendee_slots         Total available slots for camp attendees.
     */	
	
    public Camp(String campName, ArrayList<String> dates, String registrationClosingDate, String userGroup, String location,
                int totalSlots,String description, Staff staffInCharge,int committeeSlots, boolean b, int committee_slots, int attendee_slots) {
        this.campName = campName;
        this.dates = dates;
        this.registrationClosingDate = registrationClosingDate;
        this.userGroup = userGroup;
        this.location = location;
        this.totalSlots = totalSlots;
        this.TotalcampCommitteeSlots = committeeSlots;
        this.description = description;
        this.staffInCharge = staffInCharge;
        this.campAttendees = new ArrayList<>();
        this.campCommitteeMembers = new ArrayList<>();
        this.inquiries = new ArrayList<>();
        this.suggestions= new ArrayList<>();
        this.visible =b;
        this.withdrawnAttendees =new ArrayList<>();
    }
   
    /**
     * Retrieves the name of the camp.
     *
     * @return The name of the camp.
     */
    public String getCampName() {
        return campName;
    }

    /**
     * Sets the name of the camp.
     *
     * @param campName The name of the camp.
     */
    public void setCampName(String campName) {
        this.campName = campName;
    }

    /**
     * Retrieves the dates associated with the camp.
     *
     * @return Dates associated with the camp.
     */
    public ArrayList<String> getDates() {
        return dates;
    }

    /**
     * Sets the dates associated with the camp.
     *
     * @param dates Dates to set for the camp.
     */
    public void setDates(ArrayList<String> dates) {
        this.dates = dates;
    }

    /**
     * Retrieves the closing date for camp registration.
     *
     * @return The closing date for camp registration.
     */
    public String getRegistrationClosingDate() {
        return registrationClosingDate;
    }

    /**
     * Sets the closing date for camp registration.
     *
     * @param registrationClosingDate The closing date for camp registration.
     */
    public void setRegistrationClosingDate(String registrationClosingDate) {
        this.registrationClosingDate = registrationClosingDate;
    }

    /**
     * Retrieves the user group associated with the camp.
     *
     * @return The user group associated with the camp.
     */
    public String getUserGroup() {
        return userGroup;
    }

    /**
     * Sets the user group associated with the camp.
     *
     * @param userGroup The user group associated with the camp.
     */
    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    /**
     * Retrieves the location of the camp.
     *
     * @return The location of the camp.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the camp.
     *
     * @param location The location of the camp.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Retrieves the total available slots for camp attendees.
     *
     * @return Total available slots for camp attendees.
     */
    public int getTotalSlots() {
        return totalSlots;
    }

    /**
     * Sets the total available slots for camp attendees.
     *
     * @param totalSlots Total available slots for camp attendees.
     */
    public void setTotalSlots(int totalSlots) {
        this.totalSlots = totalSlots;
    }

    /**
     * Retrieves the total available slots for camp committee members.
     *
     * @return Total available slots for camp committee members.
     */
    public int getTotalCampCommitteeSlots() {
        return TotalcampCommitteeSlots;
    }

    /**
     * Sets the total available slots for camp committee members.
     *
     * @param campCommitteeSlots Total available slots for camp committee members.
     */
    public void setCampCommitteeSlots(int campCommitteeSlots) {
        this.TotalcampCommitteeSlots = campCommitteeSlots;
    }

    /**
     * Retrieves the description of the camp.
     *
     * @return The description of the camp.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the camp.
     *
     * @param description Description of the camp.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the staff member in charge of the camp.
     *
     * @return Staff member in charge of the camp.
     */
    public Staff getStaffInCharge() {
        return staffInCharge;
    }

    /**
     * Sets the staff member in charge of the camp.
     *
     * @param staffInCharge Staff member in charge of the camp.
     */
    public void setStaffInCharge(Staff staffInCharge) {
        this.staffInCharge = staffInCharge;
    }

    /**
     * Retrieves the list of students attending the camp.
     *
     * @return List of students attending the camp.
     */
    public ArrayList<Student> getCampAttendees() {
        return campAttendees;
    }

    /**
     * Retrieves the list of students in the camp committee.
     *
     * @return List of students in the camp committee.
     */
    public ArrayList<Student> getCampCommitteeMembers() {
        return campCommitteeMembers;
    }

    /**
     * Adds a student as a camp attendee if there's space available.
     *
     * @param student Student to add as a camp attendee.
     */
    public void addCampAttendee(Student student) {
        if (campAttendees.size() < totalSlots) {
            campAttendees.add(student);
        } else {
            System.out.println("Camp is full. Cannot add more attendees.");
        }
    }

    /**
     * Controls the visibility status of the camp.
     *
     * @param isVisible Visibility status of the camp.
     */
    public void setCampVisibility(boolean isVisible) {
        this.visible = isVisible;
    }
    
    /**
     * Represents the textual representation of the camp details.
     *
     * @return String representation of camp details.
     */
    @Override
    public String toString() {
        return "Camp Name: " + campName + "\n" +
               "Dates: " + dates + "\n" +
               "Registration Closing Date: " + registrationClosingDate + "\n" +
               "User Group: " + userGroup + "\n" +
               "Location: " + location + "\n" +
               "Total Slots: " + totalSlots + "\n" +
               "Camp Committee Slots: " + TotalcampCommitteeSlots + "\n" +
               "Description: " + description + "\n" +
               "Staff in Charge: " + staffInCharge.getNtuNetworkId() + "\n" +
               "Committee Members: " + campCommitteeMembers.toString() + "\n" +
               "Camp Attendees: " + campAttendees.toString() + "\n"+
               "Withdrawn Attendees: " + withdrawnAttendees.toString() + "\n";
    }
    
    //---------------------Inquiry-----------------------
    
    /**
     * Adds an inquiry related to the camp.
     *
     * @param sender  User sending the inquiry.
     * @param message Inquiry message content.
     */
    public void addInquiry(User sender, String message) {
        Inquiry inquiry = new Inquiry(sender, message, campName);
        inquiries.add(inquiry);
    }

    /**
     * Retrieves all inquiries related to the camp.
     *
     * @return List of inquiries related to the camp.
     */
    public ArrayList<Inquiry> getInquiries() {
        return inquiries;
    }

    /**
     * Replies to a specific inquiry related to the camp.
     *
     * @param inquiry      The inquiry being replied to.
     * @param reply        Reply message content.
     * @param replierName  Name of the replier.
     */
    public void replyToInquiry(Inquiry inquiry, String reply,String replierName) {
        inquiry.setReply(reply,replierName);
    }    
    
    /**
     * Retrieves the available slots in the camp.
     *
     * @return Available slots in the camp.
     */
    public int getAvailableSlots() {
        return totalSlots - campAttendees.size() - campCommitteeMembers.size();
    }
    
    /**
     * Retrieves the number of committee members in the camp.
     *
     * @return Number of committee members in the camp.
     */
    public int getCommitteMembersSize()
    {
    	return campCommitteeMembers.size();
    }

    /**
     * Adds a student as a camp committee member if slots are available.
     *
     * @param student Student to add as a committee member.
     */
    public void addCampCommitteeMember(Student student) {
        if (campCommitteeMembers.size() < TotalcampCommitteeSlots) {
            campCommitteeMembers.add(student);            
        } 
        else {
            System.out.println("Camp ccommittee is full. Cannot add more members.");
        }
    }
    
    /**
     * Retrieves available slots for committee members in the camp.
     *
     * @return Available slots for committee members in the camp.
     */
    public int getAvailableSlotsForCommitteeMember()
    {
    	return TotalcampCommitteeSlots-campCommitteeMembers.size();
    }
    
    /**
     * Removes a camp attendee from the camp.
     *
     * @param student Student to remove from the camp attendees.
     * @return Boolean indicating successful removal.
     */
    public boolean removeCampAttendee(Student student) {
        if (campAttendees.contains(student)) {
            campAttendees.remove(student);
            withdrawnAttendees.add(student);
            incrementAvailableSlots(); // Increment available slots when removing an attendee
            return true;
        }
        return false;
    }
    
    /**
     * Adds a withdrawn attendee to the list of withdrawn attendees.
     *
     * @param student Student who has withdrawn from the camp.
     */
    public void addWithdrawnAttendee(Student student)
    {
    	withdrawnAttendees.add(student);
    }
    
    /**
     * Retrieves the list of withdrawn attendees from the camp.
     *
     * @return List of withdrawn attendees from the camp.
     */
    public ArrayList<Student> getWithdrawnAttendees()
    {
    	return withdrawnAttendees;
    }

    /**
     * Removes a camp committee member from the camp.
     *
     * @param student Student to remove from the camp committee.
     * @return Boolean indicating successful removal.
     */
    public boolean removeCampCommitteeMember(Student student) {
        if (campCommitteeMembers.contains(student)) {
            campCommitteeMembers.remove(student); // Increment available committee slots when removing a committee member
            return true;
        }
        return false;
    }

    /**
     * Increments available slots in the camp.
     */
    public void incrementAvailableSlots() {
    	totalSlots++;
    }
    
    /**
     * Checks if the camp is visible.
     *
     * @return Boolean indicating camp visibility.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets the camp visibility status.
     *
     * @param visible New visibility status for the camp.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

	
    /**
     * Retrieves the total number of camp members.
     *
     * @return Total number of camp members.
     */
	public int getNumberOfMembers() {
        return campMembers;
    }

	/**
     * Retrieves the total number of camp committee members.
     *
     * @return Total number of camp committee members.
     */
    public int getNumberOfCommitteeMember()
    {
    	return campCommittees;
    }
    
    /**
     * Checks if a student is already attending the camp.
     *
     * @param studentName Name of the student to check.
     * @return Boolean indicating if the student is attending the camp.
     */
    public boolean isAlreadyAttending(String studentName) {
        for (Student attendee : campAttendees) {
            if (attendee.getName().equalsIgnoreCase(studentName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a student is a committee member.
     *
     * @param studentName Name of the student to check.
     * @return Boolean indicating if the student is a committee member.
     */
    public boolean isStudentMember(String studentName) {
        for (Student member : campCommitteeMembers) {
            if (member.getName().equalsIgnoreCase(studentName)) {
                return true;
            }
        }
        return false;
    }
    
    //---------------------Suggestions-----------------------
    
    /**
     * Adds a suggestion related to the camp.
     *
     * @param sender  User sending the suggestion.
     * @param message Suggestion message content.
     */
    public void addSuggestion(User sender, String message) {
        Suggestion suggestion = new Suggestion(sender, message, campName,SuggestStatus.pending);
        suggestions.add(suggestion);
    }
    
    /**
     * Retrieves all suggestions related to the camp.
     *
     * @return List of suggestions related to the camp.
     */
    public ArrayList<Suggestion> getSuggestions() {
        return suggestions;
    }

    /**
     * Approves a suggestion related to the camp.
     *
     * @param suggestion Suggestion to approve.
     * @param approve    Approval status of the suggestion.
     */
    public void approveSuggestion(Suggestion suggestion, String approve) {
    	suggestion.setStatus(SuggestStatus.approved);
    }
    
}

