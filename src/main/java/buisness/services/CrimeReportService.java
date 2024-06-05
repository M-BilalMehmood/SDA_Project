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

    public CrimeReport createCrimeReport(String description, Location location, CaseCategory category, Citizen citizen, Evidence evidence) {
        CrimeReport report = new CrimeReport(description, location, category, citizen, evidence);
        crimeReportRepository.save(report);
        return report;
    }

    public CrimeReport createCrimeReportAnonymously(String description, Location location, CaseCategory category, Evidence evidence) {
        CrimeReport report = new CrimeReport(description, location, category, evidence);
        crimeReportRepository.save(report);
        return report;
    }


    public CrimeReport findCrimeReportById(int incidentId) {
        return crimeReportRepository.findById(incidentId);
    }

    public List<CrimeReport> findAllCrimeReports() {
        return crimeReportRepository.findAll();
    }

    public void updateCrimeReport(CrimeReport crimeReport) {
        crimeReportRepository.update(crimeReport);
    }

    public void deleteCrimeReport(int incidentId) {
        crimeReportRepository.delete(incidentId);
    }
    // ... (other CRUD operations and business logic related to CrimeReports)
}