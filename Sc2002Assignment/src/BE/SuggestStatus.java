package BE;

/**
 * Enum representing the status of a suggestion.
 * It includes three possible states: PENDING, APPROVED, and REJECTED.
 */
public enum SuggestStatus {
    
	/**
     * The suggestion is pending review or consideration.
     */
	pending,
	
	/**
     * The suggestion has been approved.
     */
    approved,
	
    /**
     * The suggestion has been rejected.
     */
    rejected
}