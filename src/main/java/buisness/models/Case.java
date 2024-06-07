package buisness.models;

public class Case {
    private int caseId;
    private Incident incident;
    private CaseOfficer caseOfficer;
    private Citizen witness;
    private Investigation investigation;
    private CaseStatus status;
    private String finalRemarks;

    //shoaib's first git edit !!!

    // Constructors
    public Case() {
        // Default constructor
    }

    public Case(int caseId, Incident incident, CaseOfficer caseOfficer, Citizen witness,
                Investigation investigation, CaseStatus status, String finalRemarks) {
        this.caseId = caseId;
        this.incident = incident;
        this.caseOfficer = caseOfficer;
        this.witness = witness;
        this.investigation = investigation;
        this.status = status;
        this.finalRemarks = finalRemarks;
    }

    public Case(Incident incident, CaseOfficer caseOfficer, Citizen witness, Investigation investigation) {
    }

    // Getters
    public int getCaseId() {
        return caseId;
    }

    public Incident getCrimeReport() {
        return incident;
    }

    public CaseOfficer getCaseOfficer() {
        return caseOfficer;
    }

    public Citizen getWitness() {
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

    public void setIncident(Incident incident) {
        this.incident = incident;
    }

    public void setCaseOfficer(CaseOfficer caseOfficer) {
        this.caseOfficer = caseOfficer;
    }

    public void setWitness(Citizen witness) {
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

    public void setCrimeReport(Incident incident) {
        this.incident = incident;
    }
}
