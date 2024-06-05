package datalayer.repositories;
import buisness.models.CrimeReport;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CrimeReportRepository {
    private final String url = "jdbc:mysql://localhost:3306/sda_proj";
    private final String user = "root";
    private final String password = "root";

    // ... (other methods)

    public void save(CrimeReport report) {
        String sql = "INSERT INTO crime_reports (description, latitude, longitude, dateTime, category, reporter_username, evidence_id, status) " +
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

    public CrimeReport findById(int incidentId) {
        String sql = "SELECT * FROM crime_reports WHERE incidentId = ?";
        CrimeReport report = null;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, incidentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Extract data from ResultSet and create CrimeReport object
                    report = new CrimeReport();
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

    public List<CrimeReport> findAll() {
        String sql = "SELECT * FROM crime_reports";
        List<CrimeReport> reports = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Extract data from ResultSet and create CrimeReport objects
                CrimeReport report = new CrimeReport();
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

    public void update(CrimeReport crimeReport) {
        String sql = "UPDATE crime_reports SET description = ?, latitude = ?, longitude = ?, dateTime = ?, category = ?, reporter_username = ?, evidence_id = ?, status = ? WHERE incidentId = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set parameters (adjust column indices if needed)
            pstmt.setString(1, crimeReport.getDescription());
            pstmt.setDouble(2, crimeReport.getLocation().getLatitude());
            pstmt.setDouble(3, crimeReport.getLocation().getLongitude());
            pstmt.setTimestamp(4, Timestamp.valueOf(crimeReport.getDateTime()));
            pstmt.setString(5, crimeReport.getCategory().toString());
            pstmt.setString(6, crimeReport.getReporter() != null ? crimeReport.getReporter().getUsername() : null);
            pstmt.setInt(7, crimeReport.getEvidence() != null ? crimeReport.getEvidence().getEvidenceId() : 0);
            pstmt.setString(8, crimeReport.getStatus());
            pstmt.setInt(9, crimeReport.getIncidentId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            // Handle exceptions
            System.err.println("Error updating crime report: " + e.getMessage());
        }
    }

    public void delete(int incidentId) {
        String sql = "DELETE FROM crime_reports WHERE incidentId = ?";

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