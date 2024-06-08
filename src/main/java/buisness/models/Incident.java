package buisness.models;

import java.time.LocalDateTime;

public class Incident {
    private int incidentId; // Unique identifier for the crime report
    private String description; // Detailed description of the incident
    private String location; // Location of the incident (consider using a separate Location class)
    private LocalDateTime dateTime; // Date and time of the incident
    private CaseCategory category; // Category of the crime (e.g., theft, assault, vandalism)
    private Citizen reporter; // Citizen who reported the crime (optional if anonymous reporting is allowed)
    private Evidence evidence; // Associated evidence with the report (e.g., photos, videos)
    private String status; // Current status of the report (e.g., "New", "In Progress", "Closed")

    public Incident(int incidentId, String description, String location, LocalDateTime dateTime, CaseCategory category, Citizen reporter, Evidence evidence, String status) {
        this.incidentId = incidentId;
        this.description = description;
        this.location = location;
        this.dateTime = dateTime;
        this.category = category;
        this.reporter = reporter;
        this.evidence = evidence;
        this.status = status;
    }

    public Incident() {
    }

    public Incident(String description, String location, CaseCategory category, Citizen citizen, Evidence evidence) {
        this.description = description;
        this.location = location;
        this.category = category;
        this.reporter = citizen;
        this.evidence = evidence;
    }

    public Incident(String description, String location, CaseCategory category, Evidence evidence) {
        this.description = description;
        this.location = location;
        this.category = category;
        this.evidence = evidence;
    }


    public int getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(int incidentId) {
        this.incidentId = incidentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public CaseCategory getCategory() {
        return category;
    }

    public void setCategory(CaseCategory category) {
        this.category = category;
    }

    public Citizen getReporter() {
        return reporter;
    }

    public void setReporter(Citizen reporter) {
        this.reporter = reporter;
    }

    public Evidence getEvidence() {
        return evidence;
    }

    public void setEvidence(Evidence evidence) {
        this.evidence = evidence;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    public void addEvidence(Evidence newEvidence) {
        this.evidence = newEvidence;
    }

    public void removeEvidence() {
        this.evidence = null;
    }

    public void assignReporter(Citizen newReporter) {
        this.reporter = newReporter;
    }

    public void removeReporter() {
        this.reporter = null;
    }

    public void updateDescription(String newDescription) {
        this.description = newDescription;
    }

    public void updateLocation(String newLocation) {
        this.location = newLocation;
    }

    public void updateCategory(CaseCategory newCategory) {
        this.category = newCategory;
    }

    public void updateDateTime(LocalDateTime newDateTime) {
        this.dateTime = newDateTime;
    }

    public void closeReport() {
        this.status = "Closed";
    }

    public void reopenReport() {
        this.status = "New";
    }

    public void inProgressReport() {
        this.status = "In Progress";
    }

    public void updateReport(int incidentId, String description, String location, LocalDateTime dateTime, CaseCategory category, Citizen reporter, Evidence evidence, String status) {
        this.incidentId = incidentId;
        this.description = description;
        this.location = location;
        this.dateTime = dateTime;
        this.category = category;
        this.reporter = reporter;
        this.evidence = evidence;
        this.status = status;
    }

    public void deleteReport() {
        this.incidentId = 0;
        this.description = null;
        this.location = null;
        this.dateTime = null;
        this.category = null;
        this.reporter = null;
        this.evidence = null;
        this.status = null;
    }

    public void printReport() {
        System.out.println("Incident ID: " + incidentId);
        System.out.println("Description: " + description);
        System.out.println("Location: " + location);
        System.out.println("Date and Time: " + dateTime);
        System.out.println("Category: " + category);
        System.out.println("Reporter: " + reporter);
        System.out.println("Evidence: " + evidence);
        System.out.println("Status: " + status);
    }

    public String getSeverity() {
        return switch (this.category) {
            case RAPE, VANDALISM, ASSAULT -> "1";
            case THEFT -> "2";
            default -> "3";
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Incident ID: ").append(incidentId).append("\n");
        sb.append("Location: ").append(location).append("\n");
        sb.append("Date Time: ").append(dateTime).append("\n");
        sb.append("Category: ").append(category).append("\n");
        return sb.toString();
    }
}