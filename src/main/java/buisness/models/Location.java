package buisness.models;

public class Location {
    private String city;
    private String address; // Optional: More human-readable address

    // Constructors
    public Location() {
        // Default constructor
    }

    public Location(String city, String address) {
        this.city = city;
        this.address = address;
    }

    public Location(String city) {
        this.city = city;
    }

    // Getters
    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    // Setters
    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
