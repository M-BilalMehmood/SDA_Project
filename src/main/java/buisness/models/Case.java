package buisness.models;

public class Case {
    private int caseId; // Unique identifier for the case
    private CrimeReport crimeReport; // The reported incident this case is based on
    private CaseOfficer caseOfficer; // The assigned case officer
    private Witness witness; // Witness related to the case (if any)
    private Investigation investigation; // Details and progress of the investigation
    private CaseStatus status; // Current status of the case (e.g., "Open", "Closed", "Pending")
    private String finalRemarks; // Final remarks or notes when closing the case

    // Constructors
    public Case() {
        // Default constructor
    }

    public Case(int caseId, CrimeReport crimeReport, CaseOfficer caseOfficer, Witness witness,
                Investigation investigation, CaseStatus status, String finalRemarks) {
        this.caseId = caseId;
        this.crimeReport = crimeReport;
        this.caseOfficer = caseOfficer;
        this.witness = witness;
        this.investigation = investigation;
        this.status = status;
        this.finalRemarks = finalRemarks;
    }

    public Case(CrimeReport crimeReport, CaseOfficer caseOfficer, Witness witness, Investigation investigation) {
    }

    // Getters
    public int getCaseId() {
        return caseId;
    }

    public CrimeReport getCrimeReport() {
        return crimeReport;
    }

    public CaseOfficer getCaseOfficer() {
        return caseOfficer;
    }

    public Witness getWitness() {
        return witness;
    }

    public Investigation getInvestigation() {
        return investigation;
    }

    public CaseStatus getStatus() {
        return status;
    }

    public String getFinalRemarks() {
        return finalRemarks;
    }

    // Setters
    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public void setIncident(CrimeReport crimeReport) {
        this.crimeReport = crimeReport;
    }

    public void setCaseOfficer(CaseOfficer caseOfficer) {
        this.caseOfficer = caseOfficer;
    }

    public void setWitness(Witness witness) {
        this.witness = witness;
    }

    public void setInvestigation(Investigation investigation) {
        this.investigation = investigation;
    }

    public void setStatus(CaseStatus status) {
        this.status = status;
    }

    public void setFinalRemarks(String finalRemarks) {
        this.finalRemarks = finalRemarks;
    }

    public void setCrimeReport(CrimeReport crimeReport) {
        this.crimeReport = crimeReport;
    }
}
