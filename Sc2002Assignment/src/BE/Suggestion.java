package BE;

/**
 * Represents a suggestion made by a user regarding a camp.
 */
public class Suggestion {

    /**
     * The user who sent the suggestion.
     */
    private User sender;

    /**
     * The content of the suggestion.
     */
    private String message;

    /**
     * The camp associated with the suggestion.
     */
    private String camp;

    /**
     * The status of the suggestion (e.g., pending, approved).
     */
    private SuggestStatus status;


    /**
     * Constructs a Suggestion object with the provided details.
     *
     * @param sender  The user who sent the suggestion.
     * @param message The content of the suggestion.
     * @param camp    The camp associated with the suggestion.
     * @param status  The status of the suggestion (e.g., pending, approved).
     */
    public Suggestion(User sender, String message, String camp, SuggestStatus status) {
        this.sender = sender;
        this.message = message;
        this.status = status;
        this.setCamp(camp);
    }

    /**
     * Gets the sender of the suggestion.
     *
     * @return The sender of the suggestion.
     */
    public User getSender() {
        return sender;
    }

    /**
     * Sets the sender of the suggestion.
     *
     * @param sender The user who sent the suggestion.
     */
    public void setSender(User sender) {
        this.sender = sender;
    }

    /**
     * Gets the message content of the suggestion.
     *
     * @return The message content of the suggestion.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message content of the suggestion.
     *
     * @param message The content of the suggestion.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the associated camp of the suggestion.
     *
     * @return The camp associated with the suggestion.
     */
    public String getCamp() {
        return camp;
    }

    /**
     * Sets the associated camp of the suggestion.
     *
     * @param camp The camp associated with the suggestion.
     */
    public void setCamp(String camp) {
        this.camp = camp;
    }

    /**
     * Gets the status of the suggestion.
     *
     * @return The status of the suggestion.
     */
    public SuggestStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the suggestion.
     *
     * @param approved The status of the suggestion (e.g., approved, pending).
     */
    public void setStatus(SuggestStatus approved) {
        this.status = approved;
    }

    /**
     * Updates the message content of the suggestion.
     *
     * @param newMessage The updated content of the suggestion.
     */
    public void updateMessage(String newMessage) {
        this.message = newMessage;
    }
}
