package BE;

import java.util.ArrayList;

/**
 * Represents a Staff user in the system and implements a menu display.
 */
public class Staff extends User implements MenuDisplayable {

    /**
     * Constructs a Staff user with specified details.
     *
     * @param name         The name of the Staff user.
     * @param ntuNetworkId The NTU network ID of the Staff user.
     * @param faculty      The faculty of the Staff user.
     */
    public Staff(String name, String ntuNetworkId, String faculty) {
        super(name, ntuNetworkId, faculty);
    }

    /**
     * Creates a new camp with the provided details.
     *
     * @param campName               The name of the camp.
     * @param dates                  The dates for the camp.
     * @param registrationClosingDate The closing date for camp registration.
     * @param userGroup              The user group associated with the camp.
     * @param location               The location of the camp.
     * @param totalSlots             The total number of slots available in the camp.
     * @param description            The description of the camp.
     * @param TotalcommitteeSlots    The total number of committee slots in the camp.
     * @param b                      The visibility status of the camp.
     * @param committee_slots        The available committee slots in the camp.
     * @param attendee_slots         The available attendee slots in the camp.
     * @return The newly created Camp.
     */
    public Camp createCamp(String campName, ArrayList<String> dates, String registrationClosingDate, String userGroup, String location,
                           int totalSlots, String description, int TotalcommitteeSlots, boolean b, int committee_slots, int attendee_slots) {
        Camp newCamp = new Camp(campName, dates, registrationClosingDate, userGroup, location,
                totalSlots, description, this, TotalcommitteeSlots, b, committee_slots, attendee_slots);
        return newCamp;
    }

    /**
     * Toggles the visibility of a camp.
     *
     * @param camp The camp to toggle visibility for.
     */
    public void toggleCampVisibility(Camp camp) {
        camp.setVisible(!camp.isVisible());
    }

    /**
     * Displays the menu for Staff user options.
     */
    @Override
    public void displayMenu() {
        // Staff menu display logic
        System.out.println("------------- Staff Menu -------------");
        System.out.println("1. Create a new camp");
        System.out.println("2. Edit an existing camp");
        System.out.println("3. Delete a camp");
        System.out.println("4. Toggle camp visibility");
        System.out.println("5. View all camps");
        System.out.println("6. View my created camps");
        System.out.println("7. View and reply to enquiries");
        System.out.println("8. View and approve suggestions");
        System.out.println("9. Generate a report");
        System.out.println("10. Account Manager");
        System.out.print("Please enter the number of your choice: ");
    }
}