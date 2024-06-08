package buisness.models;

public class Citizen extends User {
    private String address;
    private String communityInvolvement;

    // Constructors
    public Citizen() {
        // Default constructor
    }

    public Citizen(String username, String password, UserRole role, String email, String phoneNumber, String address) {
        super(username, password, role, email, phoneNumber);
        this.address = address;
    }

    // Getters
    public String getAddress() {
        return address;
    }

    public String getCommunityInvolvement() {
        return communityInvolvement;
    }

    // Setters
    public void setAddress(String address) {
        this.address = address;
    }

    public void setCommunityInvolvement(String communityInvolvement) {
        this.communityInvolvement = communityInvolvement;
    }
}
