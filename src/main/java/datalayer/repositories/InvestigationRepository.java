package datalayer.repositories;

import buisness.models.Case;
import buisness.models.CaseOfficer;
import buisness.models.Investigation;
import buisness.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvestigationRepository {
    private final String url = "jdbc:mysql://localhost:3306/sda_proj";
    private final String user = "root";
    private final String password = "root";

    public void save(Investigation investigation) {
        String sql = "INSERT INTO investigations (status, case_id, assigned_officer_username) " +
                "VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, investigation.getStatus());
            pstmt.setInt(2, investigation.getCrimeCase().getCaseId()); // Assuming you have the Case object
            pstmt.setString(3, investigation.getAssignedOfficer().getUsername()); // Assuming assignedOfficer is a User

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        investigation.setInvestigationId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saving investigation: " + e.getMessage());
        }
    }

    public List<Investigation> findAll() {
        String sql = "SELECT * FROM investigations";
        List<Investigation> investigations = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Investigation investigation = new Investigation();
                investigation.setInvestigationId(rs.getInt("investigation_id"));
                investigation.setStatus(rs.getString("status"));

                int caseId = rs.getInt("case_id");
                Case crimeCase = new CaseRepository().findById(caseId); // Fetch Case using its repository
                investigation.setCrimeCase(crimeCase);

                // Retrieve assigned officer (User)
                String assignedOfficerUsername = rs.getString("assigned_officer_username");
                CaseOfficer assignedOfficer = new UserRepository().findOfficerByUsername(assignedOfficerUsername); // Fetch User
                investigation.setAssignedOfficer(assignedOfficer);

                investigations.add(investigation);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all investigations: " + e.getMessage());
        }

        return investigations;
    }

    public void update(Investigation investigation) {
        String sql = "UPDATE investigations SET status = ?, assigned_officer_username = ? " +
                "WHERE investigation_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, investigation.getStatus());
            pstmt.setString(2, investigation.getAssignedOfficer().getUsername()); // Assuming you have the User object
            pstmt.setInt(3, investigation.getInvestigationId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating investigation: " + e.getMessage());
        }
    }

    public void delete(int investigationId) {
        String sql = "DELETE FROM investigations WHERE investigation_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, investigationId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting investigation: " + e.getMessage());
        }
    }

    public Investigation findById(int investigationId) {
        String sql = "SELECT * FROM investigations WHERE investigation_id = ?";
        Investigation investigation = null;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, investigationId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    investigation = new Investigation();
                    investigation.setInvestigationId(rs.getInt("investigation_id"));
                    investigation.setStatus(rs.getString("status"));

                    int caseId = rs.getInt("case_id");
                    Case crimeCase = new CaseRepository().findById(caseId); // Fetch Case using its repository
                    investigation.setCrimeCase(crimeCase);

                    // Retrieve assigned officer (User)
                    String assignedOfficerUsername = rs.getString("assigned_officer_username");
                    CaseOfficer assignedOfficer = new UserRepository().findOfficerByUsername(assignedOfficerUsername); // Fetch User
                    investigation.setAssignedOfficer(assignedOfficer);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding investigation: " + e.getMessage());
        }

        return investigation;
    }
}