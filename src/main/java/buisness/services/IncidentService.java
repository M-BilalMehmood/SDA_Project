package buisness.services;

import buisness.models.*;
import datalayer.repositories.IncidentRepository;

import java.util.List;

// CrimeReportService.java
public class IncidentService {
    private IncidentRepository incidentRepository;

    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    public Incident createCrimeReport(String description, Location location, CaseCategory category, Citizen citizen, Evidence evidence) {
        Incident report = new Incident(description, location, category, citizen, evidence);
        incidentRepository.save(report);
        return report;
    }

    public Incident createCrimeReportAnonymously(String description, Location location, CaseCategory category, Evidence evidence) {
        Incident report = new Incident(description, location, category, evidence);
        incidentRepository.save(report);
        return report;
    }


    public Incident findCrimeReportById(int incidentId) {
        return incidentRepository.findById(incidentId);
    }

    public List<Incident> findAllCrimeReports() {
        return IncidentRepository.findAll();
    }

    public void updateCrimeReport(Incident incident) {
        incidentRepository.update(incident);
    }

    public void deleteCrimeReport(int incidentId) {
        incidentRepository.delete(incidentId);
    }

    public List<Incident> getRecentIncidents() {
        // 1. Fetch all crime reports from the database (or a limited number)
        List<Incident> allReports = IncidentRepository.findAll(); // Use your CrimeReportRepository

        // 2. (Optional) Filter reports based on recency (e.g., last 24 hours, last week)
        // ...

        // 3. Return the list of recent incidents
        return allReports;
    }
    // ... (other CRUD operations and business logic related to CrimeReports)
}