package buisness.models;

public class User {
    private String username;
    private String password;
    private UserRole role;
    private ContactInformation contactInformation;

    // Constructors
    public User() {
        // Default constructor
    }

    public User(String username, String password, UserRole role, ContactInformation contactInformation) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.contactInformation = contactInformation;
        // Initialize other common attributes here
    }

    public User(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
        // Initialize other common attributes here
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }
}
