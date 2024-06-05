package buisness.models;

import java.time.LocalDateTime;

public class CrimeReport {
    private int incidentId; // Unique identifier for the crime report
    private String description; // Detailed description of the incident
    private Location location; // Location of the incident (consider using a separate Location class)
    private LocalDateTime dateTime; // Date and time of the incident
    private Category category; // Category of the crime (e.g., theft, assault, vandalism)
    private Citizen reporter; // Citizen who reported the crime (optional if anonymous reporting is allowed)
    private Evidence evidence; // Associated evidence with the report (e.g., photos, videos)
    private String status; // Current status of the report (e.g., "New", "In Progress", "Closed")

    public CrimeReport(int incidentId, String description, Location location, LocalDateTime dateTime, Category category, Citizen reporter, Evidence evidence, String status) {
        this.incidentId = incidentId;
        this.description = description;
        this.location = location;
        this.dateTime = dateTime;
        this.category = category;
        this.reporter = reporter;
        this.evidence = evidence;
        this.status = status;
    }

    public CrimeReport() {
    }

    public CrimeReport(String description, Location location, Category category, Citizen citizen, Evidence evidence) {
        this.description = description;
        this.location = location;
        this.category = category;
        this.reporter = citizen;
        this.evidence = evidence;
    }

    public CrimeReport(String description, Location location, Category category, Evidence evidence) {
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
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

    public void updateLocation(Location newLocation) {
        this.location = newLocation;
    }

    public void updateCategory(Category newCategory) {
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

    public void updateReport(int incidentId, String description, Location location, LocalDateTime dateTime, Category category, Citizen reporter, Evidence evidence, String status) {
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
}