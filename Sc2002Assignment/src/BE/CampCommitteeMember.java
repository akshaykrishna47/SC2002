package BE;

import java.util.ArrayList;

public class CampCommitteeMember extends Student {
    private int points;
    private ArrayList<Inquiry> committeeInquiries;

    public CampCommitteeMember(String name, String ntuNetworkId, String faculty) {
        super(name, ntuNetworkId, faculty);
        this.setCommitteeMember(true); // Set as committee member upon creation
        this.points = 0;
        this.committeeInquiries = new ArrayList<>();
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void incrementPoints() {
        this.points++;
    }

    public void decrementPoints() {
        if (this.points > 0) {
            this.points--;
        }
    }

    public void generateCampAttendanceReport(ArrayList<Camp> camps) {
        // Logic to generate and print the camp attendance report
        System.out.println("Camp Attendance Report for Committee Member: " + this.getName());
        for (Camp camp : camps) {
            if (camp.getCampCommitteeMembers().contains(this)) {
                System.out.println("Camp Name: " + camp.getCampName());
                // Add more details as needed
            }
        }
    }

    public void addCommitteeInquiry(Inquiry inquiry) {
        committeeInquiries.add(inquiry);
    }

    public void removeCommitteeInquiry(Inquiry inquiry) {
        committeeInquiries.remove(inquiry);
    }

    public ArrayList<Inquiry> getCommitteeInquiries() {
        return committeeInquiries;
    }

    
}
