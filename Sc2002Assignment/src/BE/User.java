package BE;

/**
 * Represents a user in the system.
 */
public class User {
    private String name;
    private String ntuNetworkId;
    private String password;
    private String faculty;

    /**
     * Constructs a User object with specified name, NTU network ID, and faculty.
     *
     * @param name         The name of the user.
     * @param ntuNetworkId The NTU network ID of the user.
     * @param faculty      The faculty of the user.
     */
    public User(String name, String ntuNetworkId, String faculty) {
        this.name = name;
        this.ntuNetworkId = ntuNetworkId;
        this.password = "password"; // Default password
        this.faculty = faculty;
    }

    /**
     * Retrieves the name of the user.
     *
     * @return The name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name The name of the user to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the NTU network ID of the user.
     *
     * @return The NTU network ID of the user.
     */
    public String getNtuNetworkId() {
        return ntuNetworkId;
    }

    /**
     * Sets the NTU network ID of the user.
     *
     * @param ntuNetworkId The NTU network ID of the user to set.
     */
    public void setNtuNetworkId(String ntuNetworkId) {
        this.ntuNetworkId = ntuNetworkId;
    }

    /**
     * Retrieves the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The password of the user to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieves the faculty of the user.
     *
     * @return The faculty of the user.
     */
    public String getFaculty() {
        return faculty;
    }

    /**
     * Sets the faculty of the user.
     *
     * @param faculty The faculty of the user to set.
     */
    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}
