package BE;

import java.util.ArrayList;

/**
 * Manages inquiries made by users, storing and retrieving them.
 */
public class InquiryManager {
    /**
     * The list of inquiries managed by the InquiryManager.
     */
    private ArrayList<Inquiry> inquiries;

    /**
     * Constructs an InquiryManager with an empty list of inquiries.
     */
    public InquiryManager() {
        inquiries = new ArrayList<>();
    }

    /**
     * Stores an inquiry in the InquiryManager.
     *
     * @param inquiry The inquiry to store.
     */
    public void storeInquiries(Inquiry inquiry) {
        inquiries.add(inquiry);
    }

    /**
     * Retrieves inquiries made by a specific user.
     *
     * @param user The user whose inquiries are to be retrieved.
     * @return An ArrayList containing inquiries made by the specified user.
     */
    public ArrayList<Inquiry> getInquiriesForUser(User user) {
        ArrayList<Inquiry> userInquiries = new ArrayList<>();
        for (Inquiry inquiry : inquiries) {
            if (inquiry.getSender().equals(user)) {
                userInquiries.add(inquiry);
            }
        }
        return userInquiries;
    }

    /**
     * Retrieves all inquiries stored in the InquiryManager.
     *
     * @return An ArrayList containing all stored inquiries.
     */
    public ArrayList<Inquiry> getAllInquiries() {
        return inquiries;
    }

    /**
     * Updates an inquiry's message for a specific student.
     *
     * @param student     The student whose inquiry is to be updated.
     * @param oldInquiry  The inquiry to be updated.
     * @param newMessage  The new message to replace the old message.
     */
    public void updateInquiry(Student student, Inquiry oldInquiry, String newMessage) {
        ArrayList<Inquiry> userInquiries = student.getInquiries();
        for (Inquiry inquiry : userInquiries) {
            if (inquiry == oldInquiry) {
                inquiry.updateMessage(newMessage);
                break;
            }
        }
    }

    /**
     * Loads a list of inquiries into the InquiryManager.
     *
     * @param loadInquiries The inquiries to load into the InquiryManager.
     * @return The ArrayList of inquiries loaded into the InquiryManager.
     */
    public ArrayList<Inquiry> LoadInquiries(ArrayList<Inquiry> loadInquiries) {
        inquiries = loadInquiries;
        return inquiries;
    }
}
