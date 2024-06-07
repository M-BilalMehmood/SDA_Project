package buisness.services;

import buisness.models.*;
import datalayer.repositories.CrimeReportRepository;

import java.util.List;

// CrimeReportService.java
public class CrimeReportService {
    private CrimeReportRepository crimeReportRepository;

    public CrimeReportService(CrimeReportRepository crimeReportRepository) {
        this.crimeReportRepository = crimeReportRepository;
    }

    public Incident createCrimeReport(String description, Location location, CaseCategory category, Citizen citizen, Evidence evidence) {
        Incident report = new Incident(description, location, category, citizen, evidence);
        crimeReportRepository.save(report);
        return report;
    }

    public Incident createCrimeReportAnonymously(String description, Location location, CaseCategory category, Evidence evidence) {
        Incident report = new Incident(description, location, category, evidence);
        crimeReportRepository.save(report);
        return report;
    }


    public Incident findCrimeReportById(int incidentId) {
        return crimeReportRepository.findById(incidentId);
    }

    public List<Incident> findAllCrimeReports() {
        return crimeReportRepository.findAll();
    }

    public void updateCrimeReport(Incident incident) {
        crimeReportRepository.update(incident);
    }

    public void deleteCrimeReport(int incidentId) {
        crimeReportRepository.delete(incidentId);
    }
    // ... (other CRUD operations and business logic related to CrimeReports)
}