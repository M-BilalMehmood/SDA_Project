package datalayer.repositories;
import buisness.models.Incident;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class IncidentRepository {
    private static final String url = "jdbc:mysql://localhost:3306/sda_proj";
    private static final String user = "root";
    private static final String password = "root";

    // ... (other methods)

    public void save(Incident report) {
        String sql = "INSERT INTO incidents (description, latitude, longitude, dateTime, category, reporter_username, evidence_id, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"; // Assuming you have columns for these attributes

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Set parameters (adjust column indices if needed)
            pstmt.setString(1, report.getDescription());
            pstmt.setDouble(2, report.getLocation().getLatitude());
            pstmt.setDouble(3, report.getLocation().getLongitude());
            pstmt.setTimestamp(4, Timestamp.valueOf(report.getDateTime()));
            pstmt.setString(5, report.getCategory().toString()); // Assuming Category is an enum
            pstmt.setString(6, report.getReporter() != null ? report.getReporter().getUsername() : null);
            pstmt.setInt(7, report.getEvidence() != null ? report.getEvidence().getEvidenceId() : 0); // Assuming Evidence has an ID
            pstmt.setString(8, report.getStatus());

            int affectedRows = pstmt.executeUpdate();

            // Get generated ID if needed
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        report.setIncidentId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            // Handle exceptions (e.g., log the error, throw a custom exception)
            System.err.println("Error saving crime report: " + e.getMessage());
        }
    }

    public Incident findById(int incidentId) {
        String sql = "SELECT * FROM incidents WHERE incident_id = ?";
        Incident report = null;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, incidentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Extract data from ResultSet and create CrimeReport object
                    report = new Incident();
                    report.setIncidentId(rs.getInt("incidentId"));
                    // ... set other attributes
                }
            }
        } catch (SQLException e) {
            // Handle exceptions
            System.err.println("Error retrieving crime report: " + e.getMessage());
        }

        return report;
    }

    public static List<Incident> findAll() {
        String sql = "SELECT * FROM incidents";
        List<Incident> reports = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Extract data from ResultSet and create CrimeReport objects
                Incident report = new Incident();
                report.setIncidentId(rs.getInt("incidentId"));
                // ... set other attributes

                reports.add(report);
            }
        } catch (SQLException e) {
            // Handle exceptions
            System.err.println("Error retrieving crime reports: " + e.getMessage());
        }

        return reports;
    }

    public void update(Incident incident) {
        String sql = "UPDATE incidents SET description = ?, latitude = ?, longitude = ?, dateTime = ?, category = ?, reporter_username = ?, evidence_id = ?, status = ? WHERE incidentId = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set parameters (adjust column indices if needed)
            pstmt.setString(1, incident.getDescription());
            pstmt.setDouble(2, incident.getLocation().getLatitude());
            pstmt.setDouble(3, incident.getLocation().getLongitude());
            pstmt.setTimestamp(4, Timestamp.valueOf(incident.getDateTime()));
            pstmt.setString(5, incident.getCategory().toString());
            pstmt.setString(6, incident.getReporter() != null ? incident.getReporter().getUsername() : null);
            pstmt.setInt(7, incident.getEvidence() != null ? incident.getEvidence().getEvidenceId() : 0);
            pstmt.setString(8, incident.getStatus());
            pstmt.setInt(9, incident.getIncidentId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            // Handle exceptions
            System.err.println("Error updating crime report: " + e.getMessage());
        }
    }

    public void delete(int incidentId) {
        String sql = "DELETE FROM incidents WHERE incidentId = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, incidentId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // Handle exceptions
            System.err.println("Error deleting crime report: " + e.getMessage());
        }
    }
}