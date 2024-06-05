package buisness.models;

public class Witness {
    private int witnessId; // Unique identifier
    private String name; // Full name of the witness (or alias if anonymity is required)
    private ContactInformation contactInformation; // Contact details of the witness
    private Case crimeCase; // The case this witness is related to

    // Constructors
    public Witness() {
        // Default constructor
    }

    public Witness(int witnessId, String name, ContactInformation contactInformation, Case crimeCase) {
        this.witnessId = witnessId;
        this.name = name;
        this.contactInformation = contactInformation;
        this.crimeCase = crimeCase;
    }

    // Getters
    public int getWitnessId() {
        return witnessId;
    }

    public String getName() {
        return name;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public Case getCrimeCase() {
        return crimeCase;
    }

    // Setters
    public void setWitnessId(int witnessId) {
        this.witnessId = witnessId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }

    public void setCrimeCase(Case crimeCase) {
        this.crimeCase = crimeCase;
    }
}
