package buisness.models;

import java.util.List;

public class Investigation {
    private int investigationId; // Unique identifier
    private String status; // "Ongoing", "Completed", etc.
    private Case crimeCase; // Reference to the case this investigation belongs to
    private List<Note> notes; // Notes or updates added during the investigation
    private CaseOfficer assignedOfficer;

    // Constructors
    public Investigation() {
        // Default constructor
    }

    public Investigation(int investigationId, String status, Case crimeCase, List<Note> notes, CaseOfficer assignedOfficer) {
        this.investigationId = investigationId;
        this.status = status;
        this.crimeCase = crimeCase;
        this.notes = notes;
        this.assignedOfficer = assignedOfficer;
    }

    // Getters
    public int getInvestigationId() {
        return investigationId;
    }

    public String getStatus() {
        return status;
    }

    public Case getCrimeCase() {
        return crimeCase;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public CaseOfficer getAssignedOfficer() {
        return assignedOfficer;
    }

    // Setters
    public void setInvestigationId(int investigationId) {
        this.investigationId = investigationId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCrimeCase(Case crimeCase) {
        this.crimeCase = crimeCase;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public void setAssignedOfficer(CaseOfficer assignedOfficer) {
        this.assignedOfficer = assignedOfficer;
    }
}
