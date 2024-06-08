package datalayer.repositories;

import buisness.models.Incident;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IncidentRepository {
    private static final String url = "jdbc:mysql://localhost:3306/sda_proj";
    private static final String user = "root";
    private static final String password = "root";

    public static List<Incident> findByReporterUsername(String username) {
        String sql = "SELECT * FROM incidents WHERE reporter_username = ?";
        List<Incident> reports = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Incident report = new Incident();
                    report.setIncidentId(rs.getInt("incidentId"));
                    report.setDescription(rs.getString("description"));
                    report.setLocation(rs.getString("location")); // Changed to location
                    report.setCategory(buisness.models.CaseCategory.valueOf(rs.getString("category")));
                    report.setStatus(rs.getString("status"));

                    int evidenceId = rs.getInt("evidence_id");
                    if (evidenceId != 0) {
                        report.setEvidence(new EvidenceRepository().findById(evidenceId));
                    }

                    reports.add(report);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving incidents: " + e.getMessage());
        }

        return reports;
    }

    public static int getLastEvidenceId() {
        String sql = "SELECT MAX(evidence_id) FROM incidents";
        int lastEvidenceId = 0;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                lastEvidenceId = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving last evidence id: " + e.getMessage());
        }

        return lastEvidenceId;
    }

    public void save(Incident report) {
        String sql = "INSERT INTO incidents (description, location, dateTime, category, reporter_username, evidence_id, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, report.getDescription());
            pstmt.setString(2, report.getLocation());

            // Concatenate date and time into a single string
            String dateTimeString = report.getDateTime().toString();

            pstmt.setString(3, dateTimeString); // Insert the concatenated string
            pstmt.setString(4, report.getCategory().toString());
            pstmt.setString(5, report.getReporter().getUsername());
            pstmt.setInt(6, report.getEvidence() != null ? report.getEvidence().getEvidenceId() : 0);
            pstmt.setString(7, report.getStatus());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        report.setIncidentId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saving incident: " + e.getMessage());
        }
    }


    public Incident findById(int incidentId) {
        String sql = "SELECT * FROM incidents WHERE incidentId = ?";
        Incident report = null;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, incidentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    report = new Incident();
                    report.setIncidentId(rs.getInt("incidentId"));
                    report.setDescription(rs.getString("description"));
                    report.setLocation(rs.getString("location")); // Changed to location
                    report.setCategory(buisness.models.CaseCategory.valueOf(rs.getString("category")));
                    report.setStatus(rs.getString("status"));

                    int evidenceId = rs.getInt("evidence_id");
                    if (evidenceId != 0) {
                        report.setEvidence(new EvidenceRepository().findById(evidenceId));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving incident: " + e.getMessage());
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
                Incident report = new Incident();
                report.setIncidentId(rs.getInt("incidentId"));
                report.setDescription(rs.getString("description"));
                report.setLocation(rs.getString("location")); // Changed to location
                report.setCategory(buisness.models.CaseCategory.valueOf(rs.getString("category")));
                report.setStatus(rs.getString("status"));

                int evidenceId = rs.getInt("evidence_id");
                if (evidenceId != 0) {
                    report.setEvidence(new EvidenceRepository().findById(evidenceId));
                }

                reports.add(report);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving incidents: " + e.getMessage());
        }

        return reports;
    }

    public void update(Incident incident) {
        String sql = "UPDATE incidents SET description = ?, location = ?, dateTime = ?, category = ?, reporter_username = ?, evidence_id = ?, status = ? WHERE incidentId = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, incident.getDescription());
            pstmt.setString(2, incident.getLocation()); // Changed to location
            pstmt.setTimestamp(3, Timestamp.valueOf(incident.getDateTime()));
            pstmt.setString(4, incident.getCategory().toString());
            pstmt.setString(5, incident.getReporter().getUsername());
            pstmt.setInt(6, incident.getEvidence() != null ? incident.getEvidence().getEvidenceId() : 0);
            pstmt.setString(7, incident.getStatus());
            pstmt.setInt(8, incident.getIncidentId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating incident: " + e.getMessage());
        }
    }

    public void delete(int incidentId) {
        String sql = "DELETE FROM incidents WHERE incidentId = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, incidentId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting incident: " + e.getMessage());
        }
    }
}
