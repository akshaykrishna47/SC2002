package BE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Student user, inheriting from the User class and implementing MenuDisplayable.
 */
public class Student extends User implements MenuDisplayable {
    
    /**
     * Indicates if the student is a committee member.
     */
    private boolean isCommitteeMember;

    /**
     * List of camps the student is attending.
     */
    private ArrayList<Camp> campsAttending;

    /**
     * List of inquiries made by the student.
     */
    private ArrayList<Inquiry> inquiries;

    /**
     * List of suggestions made by the student.
     */
    private ArrayList<Suggestion> suggestions;

    /**
     * Total points earned by the student.
     */
    private int points;

    /**
     * Number of replies made by the student.
     */
    private int numberOfReplies;

    /**
     * Points earned from replies made by the student.
     */
    private int replyPoints;

    /**
     * Points earned from suggestions made by the student.
     */
    private int suggestionPoints;

    /**
     * Number of approved suggestions made by the student.
     */
    private int NumberOfApprovedSuggestions;

    /**
     * Map indicating the withdrawal status of camps for the student.
     */
    private Map<Camp, Boolean> withdrawalStatus;

    /**
     * Constructs a Student object with the given parameters and initializes lists and values.
     *
     * @param name         The name of the student.
     * @param ntuNetworkId The NTU network ID of the student.
     * @param faculty      The faculty of the student.
     */	
	public Student(String name, String ntuNetworkId, String faculty) {
		super(name, ntuNetworkId, faculty);
		this.isCommitteeMember = false;
        this.setCampsAttending(new ArrayList<>());
        this.inquiries = new ArrayList<>();
        this.suggestions = new ArrayList<>();
        this.points = 0;
        this.withdrawalStatus = new HashMap<>();
	}
	
	/**
     * Withdraws the student from a camp by marking the withdrawal status for the given camp.
     *
     * @param camp The camp to withdraw from.
     */
	public void withdrawFromCamp(Camp camp)
	{
	        withdrawalStatus.put(camp, true);
	        
	}

	/**
     * Checks if the student has withdrawn from the specified camp previously.
     *
     * @param camp The camp to check for withdrawal status.
     * @return True if the student has previously withdrawn from the camp, otherwise false.
     */
	public boolean hasWithdrawnFromCamp(Camp camp) 
	{
		return withdrawalStatus.getOrDefault(camp, false);
	}
	
	/**
     * Finds a student by username within the given list of user data.
     *
     * @param usersData The list of users' data to search within.
     * @param username  The username to search for.
     * @return The found Student object, or null if not found.
     */
	public Student findStudentByUsername(ArrayList<User> usersData, String username) {
	    for (User user : usersData) {
	        if (user instanceof Student && user.getNtuNetworkId().equals(username)) {
	            return (Student) user; // If found, return the Student object
	        }
	    }
	    return null; // If not found, return null
	}
	
	/**
     * Indicates whether the student is a committee member.
     *
     * @return True if the student is a committee member, otherwise false.
     */
	public boolean isCommitteeMember() {
        return isCommitteeMember;
    }

	/**
     * Sets the committee membership status for the student.
     *
     * @param committeeMember True to set the student as a committee member, otherwise false.
     */
    public void setCommitteeMember(boolean committeeMember) {
        isCommitteeMember = committeeMember;
    }
    
    /**
     * Retrieves the total points earned by the student.
     *
     * @return The total points earned by the student.
     */
    public int getPoints() {
        return points;
    }
    
    //--------------------------Replies------------------------------
	
    /**
     * Sets the number of replies made by the student.
     *
     * @param n The number of replies to set.
     */
	public void setNumberOfReplies(int n)
	{
		this.numberOfReplies =n;
	}
	
	/**
     * Retrieves the number of replies made by the student.
     *
     * @return The number of replies made by the student.
     */
	public int getNumberOfReplies()
	{
		return numberOfReplies;
	}
    
	/**
     * Sets the points earned from replies made by the student.
     *
     * @param p The reply points to set.
     */
    public void setReplyPoints(int p)
    {
    	this.replyPoints = p;
    	this.points = this.points + p;
    }
    
    /**
     * Retrieves the points earned from replies made by the student.
     *
     * @return The points earned from replies made by the student.
     */
    public int getReplyPoints() {
    	return replyPoints;
    }
    
    //------------------------Inquiry-----------------------------
    
    /**
     * Adds an inquiry to the student's inquiries list.
     *
     * @param inquiry The inquiry to be added.
     */
    public void addInquiry(Inquiry inquiry) {
        inquiries.add(inquiry);
    }
    
    /**
     * Removes an inquiry from the student's inquiries list.
     *
     * @param inquiry The inquiry to be removed.
     */
    public void removeInquiry(Inquiry inquiry) {
        inquiries.remove(inquiry);
    }
    
    /**
     * Updates the message of a specific inquiry.
     *
     * @param inquiry    The inquiry to be updated.
     * @param newMessage The new message to update the inquiry with.
     */
    public void updateInquiry(Inquiry inquiry, String newMessage) {
        if (inquiries.contains(inquiry)) {
            inquiry.updateMessage(newMessage);
        }
    }
    
    /**
     * Retrieves the list of inquiries made by the student.
     *
     * @return The list of inquiries made by the student.
     */
    public ArrayList<Inquiry> getInquiries() {
        return inquiries;
    }
    
    
    public void setInquiries(ArrayList<Inquiry> inquiries) {
        this.inquiries = inquiries;
    }
    
    /**
     * Sets the list of inquiries made by the student.
     *
     * @param inquiries The list of inquiries to be set.
     */
    public void addPointsForEnquiryReply() {
        this.points++;
        this.numberOfReplies++;
    }

    //---------------Suggestion------------------------
    
    /**
     * Retrieves the list of suggestions made by the student.
     *
     * @return The list of suggestions made by the student.
     */
    public ArrayList<Suggestion> getSuggestions() {
        return suggestions;
    }

    /**
     * Sets the list of suggestions made by the student.
     *
     * @param suggestions The list of suggestions to be set.
     */
    public void setSuggestions(ArrayList<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

    /**
     * Adds a suggestion to the student's list of suggestions.
     *
     * @param suggestion The suggestion to be added.
     */
    public void addSuggestion(Suggestion suggestion) {
        suggestions.add(suggestion);
    }

    /**
     * Removes a suggestion from the student's list of suggestions.
     *
     * @param suggestion The suggestion to be removed.
     */
    public void removeSuggestion(Suggestion suggestion) {
        suggestions.remove(suggestion);
    }

    /**
     * Updates the message of a specific suggestion made by the student.
     *
     * @param suggestion The suggestion to be updated.
     * @param newMessage The new message to update the suggestion with.
     */
    public void updateSuggestion(Suggestion suggestion, String newMessage) {
        if (suggestions.contains(suggestion)) {
            suggestion.updateMessage(newMessage);
        }
    }

    /**
     * Adds points for submitting a suggestion.
     * Increases both total points and suggestion points.
     */
    public void addPointsForSuggestion() {
        this.points++;
        this.setSuggestionPoints(this.getSuggestionPoints() + 1);
    }

    /**
     * Removes points for deleting a suggestion.
     * Decreases both total points, suggestion points, and the count of approved suggestions.
     */
    public void removePointForDeleteSuggestion() {
        this.points--;
        this.setSuggestionPoints(this.getSuggestionPoints() - 1);
    }

    /**
     * Adds points for an accepted suggestion.
     * Increases total points and the count of approved suggestions.
     */
    public void addPointsForAcceptedSuggestion() {
        this.points++;
        this.NumberOfApprovedSuggestions++;
    }
    
    /**
     * Retrieves the total number of suggestions made by the student.
     *
     * @return The total number of suggestions made by the student.
     */
    public int NumberOfSuggestions() {
        return suggestions.size();
    }

    /**
     * Retrieves the number of approved suggestions made by the student.
     *
     * @return The number of approved suggestions made by the student.
     */
    public int getnumberofApprovedSuggestions() {
        return NumberOfApprovedSuggestions;
    }

    /**
     * Sets the number of approved suggestions made by the student.
     *
     * @param number The number of approved suggestions to be set.
     */
    public void setnumberofApprovedSuggestions(int number) {
        NumberOfApprovedSuggestions = number;
    }

    /**
     * Retrieves the total suggestion points earned by the student.
     *
     * @return The total suggestion points earned by the student.
     */
    public int getSuggestionPoints() {
        return suggestionPoints;
    }

    /**
     * Sets the total suggestion points earned by the student.
     *
     * @param suggestionPoints The total suggestion points to be set.
     */
    public void setSuggestionPoints(int suggestionPoints) {
        this.suggestionPoints = suggestionPoints;
    }

    /**
     * Returns a string representation of the student object.
     *
     * @return A string representing the name and student ID of the student.
     */
    @Override
    public String toString() {
        return "Name: " + getName() + ", Student ID: " + getNtuNetworkId();
    }
	
    /**
     * Displays the menu based on the student's role (Student or Committee Member).
     * This method shows different options depending on the student's role.
     */
    @Override
    public void displayMenu() {
        // Student menu display logic
        if (isCommitteeMember()) {
            System.out.println("------------- Committee Member Menu -------------");
            System.out.println("1. View available camps");
            System.out.println("2. Select camps to register as camp attendee or committee");
            System.out.println("3. Submit an enquiry for a camp");
            System.out.println("4. View registered camps");
            System.out.println("5. View, Edit and delete to enquiries");
            System.out.println("6. Request to withdraw from camps");
            System.out.println("7. Account Manager");
            System.out.println("8. Access Committee Member Page");
        } else {
            System.out.println("------------- Student Menu -------------");
            System.out.println("1. View available camps");
            System.out.println("2. Select camps to register as camp attendee or committee");
            System.out.println("3. Submit an enquiry for a camp");
            System.out.println("4. View registered camps");
            System.out.println("5. View, Edit and delete to enquiries");
            System.out.println("6. Request to withdraw from camps");
            System.out.println("7. Account Manager");
        }
        System.out.print("Please enter the number of your choice: ");
    }

    /**
     * Retrieves the list of camps the student is attending.
     *
     * @return The list of camps the student is attending.
     */
    public ArrayList<Camp> getCampsAttending() {
        return campsAttending;
    }

    /**
     * Sets the list of camps the student is attending.
     *
     * @param campsAttending The list of camps to be set for the student.
     */
    public void setCampsAttending(ArrayList<Camp> campsAttending) {
        this.campsAttending = campsAttending;
    }

    
}
