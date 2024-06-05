package buisness.models;

public class Evidence {
    private int evidenceId; // Unique identifier
    private String description; // Brief description of the evidence
    private String filePath; // Path or URL to the evidence file
    private CrimeReport incident; // The incident this evidence is associated with

    // Constructors
    public Evidence() {
        // Default constructor
    }

    public Evidence(int evidenceId, String description, String filePath, CrimeReport incident) {
        this.evidenceId = evidenceId;
        this.description = description;
        this.filePath = filePath;
        this.incident = incident;
    }

    // Getters
    public int getEvidenceId() {
        return evidenceId;
    }

    public String getDescription() {
        return description;
    }

    public String getFilePath() {
        return filePath;
    }

    public CrimeReport getIncident() {
        return incident;
    }

    // Setters
    public void setEvidenceId(int evidenceId) {
        this.evidenceId = evidenceId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setIncident(CrimeReport incident) {
        this.incident = incident;
    }
}