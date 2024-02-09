package BE;

/**
 * Represents an inquiry within a camp, typically initiated by a user.
 */
public class Inquiry {
	
	/**
     * The name of the sender.
     */
    private User sender;
    
    /**
     * The message.
     */
    private String message;
    
    /**
     * The reply.
     */
    private String reply;
    
    /**
     * The name of the camp.
     */
    private String camp;
    
    /**
     * The name of the replier.
     */
    private String replier;
   
    /**
     * Constructs an Inquiry object with sender, message, and camp.
     *
     * @param sender  The user initiating the inquiry.
     * @param message The content of the inquiry message.
     * @param camp    The associated camp for the inquiry.
     */
    public Inquiry(User sender, String message, String camp) {
        this.sender = sender;
        this.message = message;
        this.reply = null;
        this.setCamp(camp);
        this.replier = null;
    }

    /**
     * Retrieves the sender of the inquiry.
     *
     * @return The user initiating the inquiry.
     */
    public User getSender() {
        return sender;
    }

    /**
     * Retrieves the content of the inquiry message.
     *
     * @return The message content of the inquiry.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Retrieves the reply to the inquiry message.
     *
     * @return The reply to the inquiry message.
     */
    public String getReply() {
        return reply;
    }

    /**
     * Retrieves the ID of the user who replied to the inquiry.
     *
     * @return The ID of the user who replied.
     */
    public String getReplierID() {
        return replier;
    }

    /**
     * Sets the reply and the ID of the user who replied.
     *
     * @param reply      The reply content.
     * @param replierID  The ID of the user who replied.
     */
    public void setReply(String reply, String replierID) {
        this.reply = reply;
        this.replier = replierID;
    }

    /**
     * Updates the message content of the inquiry.
     *
     * @param newMessage The updated message content.
     */
    public void updateMessage(String newMessage) {
        this.message = newMessage;
    }

    /**
     * Retrieves the associated camp of the inquiry.
     *
     * @return The camp associated with the inquiry.
     */
    public String getCamp() {
        return camp;
    }

    /**
     * Sets the associated camp of the inquiry.
     *
     * @param camp The camp associated with the inquiry.
     */
    public void setCamp(String camp) {
        this.camp = camp;
    }

    /**
     * Provides a string representation of the Inquiry object.
     *
     * @return String representation of the Inquiry object.
     */
    @Override
    public String toString() {
        return "Inquiry{" +
                "sender=" + sender +
                ", message='" + message + '\'' +
                ", reply='" + reply + '\'' +
                ", camp='" + camp + '\'' +
                '}';
    }
}
