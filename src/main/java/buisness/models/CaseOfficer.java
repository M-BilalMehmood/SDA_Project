package buisness.models;

public class CaseOfficer extends User {
    private String badgeNumber;
    private String rank;

    // Constructors
    public CaseOfficer() {
        // Default constructor
    }

    public CaseOfficer(String username, String password, UserRole role, String email, String phoneNumber, String badgeNumber, String rank) {
        super(username, password, role, email, phoneNumber);
        this.badgeNumber = badgeNumber;
        this.rank = rank;
    }

    // Getters
    public String getBadgeNumber() {
        return badgeNumber;
    }

    public String getRank() {
        return rank;
    }

    // Setters
    public void setBadgeNumber(String badgeNumber) {
        this.badgeNumber = badgeNumber;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}