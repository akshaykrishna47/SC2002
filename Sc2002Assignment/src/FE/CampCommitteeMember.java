package FE;

import java.util.ArrayList;

import BE.Camp;
import BE.Inquiry;
import BE.Student;

public class CampCommitteeMember extends Student {
    
    public CampCommitteeMember(String name, String ntuNetworkId, String faculty) {
        super(name, ntuNetworkId, faculty);
        setCommitteeMember(true);
    }

    public void setCommitteeMember(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public void viewCampDetails(Camp camp) {
        // Logic to view camp details
        System.out.println(camp.toString());
    }

    public void submitSuggestion(Camp camp, String suggestion) {
        // Logic to submit suggestions to staff
        // You may want to have a Staff class to handle staff-related functionalities
        // Staff.receiveSuggestion(this, camp, suggestion);
    }

    public void viewAndReplyToEnquiries(Camp camp) {
        // Logic to view and reply to inquiries related to the camp
        ArrayList<Inquiry> inquiries = camp.getInquiries();
        for (Inquiry inquiry : inquiries) {
            System.out.println("Camp Name: " + camp.getCampName());
            System.out.println("Inquiry: " + inquiry.getMessage());
            // Provide options to reply to the inquiry
        }
    }

    public void viewEditDeleteSuggestions() {
        // Logic to view, edit, and delete suggestions made by the committee member
        // You may want to have a Staff class to handle staff-related functionalities
        // Staff.viewEditDeleteSuggestions(this);
    }

    public void generateReport(Camp camp, String role) {
        // Logic to generate a report for the list of students attending the camp
        // You can filter by role (attendee, camp committee, etc.)
        // Generate the report in either txt or csv format
    }

    public void earnPointsForActivities() {
        // Logic to earn points for replying to inquiries and giving suggestions
        // You may want to have a PointsSystem class to handle points-related functionalities
        // PointsSystem.earnPoints(this, activities);
    }

    // Additional methods specific to Camp Committee Members
}
