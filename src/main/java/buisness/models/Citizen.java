package buisness.models;

public class Citizen extends User {
    // Citizen-specific attributes, if any (e.g., address, community involvement)
    private String address;
    private String communityInvolvement;

    // Constructors
    public Citizen() {
        // Default constructor
    }

    public Citizen(String username, String password, UserRole role, ContactInformation contactInformation,
                   String address, String communityInvolvement) {
        super(username, password, role, contactInformation);
        this.address = address;
        this.communityInvolvement = communityInvolvement;
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
