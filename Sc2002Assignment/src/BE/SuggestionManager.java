package BE;

import java.util.ArrayList;

/**
 * Manages the suggestions in the system.
 */
public class SuggestionManager {
    private ArrayList<Suggestion> suggestions;

    /**
     * Constructs a SuggestionManager object.
     */
    public SuggestionManager() {
        suggestions = new ArrayList<>();
    }

    /**
     * Stores a suggestion in the manager.
     *
     * @param suggestion The suggestion to be stored.
     */
    public void storeSuggestion(Suggestion suggestion) {
        suggestions.add(suggestion);
    }

    /**
     * Retrieves suggestions made by a specific user.
     *
     * @param user The user for whom suggestions are retrieved.
     * @return An ArrayList of suggestions made by the specified user.
     */
    public ArrayList<Suggestion> getSuggestionsForUser(User user) {
        ArrayList<Suggestion> userSuggestions = new ArrayList<>();
        for (Suggestion suggestion : suggestions) {
            if (suggestion.getSender().equals(user)) {
                userSuggestions.add(suggestion);
            }
        }
        return userSuggestions;
    }

    /**
     * Retrieves all suggestions in the manager.
     *
     * @return An ArrayList containing all stored suggestions.
     */
    public ArrayList<Suggestion> getAllSuggestions() {
        return suggestions;
    }

    /**
     * Updates a suggestion made by a student.
     *
     * @param student       The student who made the suggestion.
     * @param oldSuggestion The original suggestion to be updated.
     * @param newMessage    The new message to update the suggestion.
     */
    public void updateSuggestion(Student student, Suggestion oldSuggestion, String newMessage) {
        ArrayList<Suggestion> userSuggestions = student.getSuggestions();
        for (Suggestion suggestion : userSuggestions) {
            if (suggestion == oldSuggestion) {
                suggestion.updateMessage(newMessage);
                break;
            }
        }
    }

    /**
     * Loads suggestions into the SuggestionManager.
     *
     * @param loadSuggestions The suggestions to load into the manager.
     * @return The loaded suggestions.
     */
    public ArrayList<Suggestion> LoadSuggestion(ArrayList<Suggestion> loadSuggestions) {
        suggestions = loadSuggestions;
        return suggestions;
    }
}
