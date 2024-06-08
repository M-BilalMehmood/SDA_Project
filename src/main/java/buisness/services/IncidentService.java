package buisness.services;

import buisness.models.*;
import datalayer.repositories.IncidentRepository;

import java.util.ArrayList;
import java.util.List;

// CrimeReportService.java
public class IncidentService {
    private IncidentRepository incidentRepository;
    public static User currentUser;

    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    public Incident createCrimeReport(String description, String location, CaseCategory category, Citizen citizen, Evidence evidence) {
        Incident report = new Incident(description, location, category, citizen, evidence);
        incidentRepository.save(report);
        return report;
    }

    public Incident createCrimeReportAnonymously(String description, String location, CaseCategory category, Evidence evidence) {
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

    // In CrimeReportService.java
    public List<Incident> getIncidentsForCurrentUser() {
        // 1. Get the currently logged-in user (you'll need to manage user sessions)
        User currentUser = getCurrentUser(); // Implement this method to get the logged-in user

        // 2. Fetch incidents reported by the current user from the database
        if (currentUser != null) {
            return IncidentRepository.findByReporterUsername(currentUser.getUsername()); // Implement this method in your repository
        } else {
            // Handle the case where there's no logged-in user (e.g., return an empty list)
            return new ArrayList<>();
        }
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static Citizen getCurrentUser() {
        User user = currentUser;
        if (user instanceof Citizen) {
            Citizen citizen = (Citizen) user;
        }
        return null;
    }
}