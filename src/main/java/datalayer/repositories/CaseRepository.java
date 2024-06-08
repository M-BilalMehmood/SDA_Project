package datalayer.repositories;
import buisness.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CaseRepository {
    private final String url = "jdbc:mysql://localhost:3306/sda_proj";
    private final String user = "root";
    private final String password = "root";

    public void save(Case crimeCase) {
        String sql = "INSERT INTO cases (crime_report_id, case_officer_username, witness_username, investigation_id, status, final_remarks) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, crimeCase.getCrimeReport().getIncidentId());
            pstmt.setString(2, crimeCase.getCaseOfficer().getUsername());
            pstmt.setString(3, crimeCase.getWitness() != null ? crimeCase.getWitness().getUsername() : null); // Set witness username
            pstmt.setInt(4, crimeCase.getInvestigation() != null ? crimeCase.getInvestigation().getInvestigationId() : 0);
            pstmt.setString(5, crimeCase.getStatus().toString());
            pstmt.setString(6, crimeCase.getFinalRemarks());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        crimeCase.setCaseId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saving case: " + e.getMessage());
        }
    }

    public void update(Case crimeCase) {
        String sql = "UPDATE cases SET crime_report_id = ?, case_officer_username = ?, witness_username = ?, investigation_id = ?, status = ?, final_remarks = ? " +
                "WHERE case_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, crimeCase.getCrimeReport().getIncidentId());
            pstmt.setString(2, crimeCase.getCaseOfficer().getUsername());
            pstmt.setString(3, crimeCase.getWitness() != null ? crimeCase.getWitness().getUsername() : null);
            pstmt.setInt(4, crimeCase.getInvestigation() != null ? crimeCase.getInvestigation().getInvestigationId() : 0);
            pstmt.setString(5, crimeCase.getStatus().toString());
            pstmt.setString(6, crimeCase.getFinalRemarks());
            pstmt.setInt(7, crimeCase.getCaseId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating case: " + e.getMessage());
        }
    }

    public Case findById(int caseId) {
        String sql = "SELECT * FROM cases WHERE case_id = ?";
        Case crimeCase = null;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, caseId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    crimeCase = new Case();
                    crimeCase.setCaseId(rs.getInt("case_id"));

                    // Retrieve associated objects (CrimeReport, CaseOfficer, etc.)
                    int crimeReportId = rs.getInt("crime_report_id");
                    Incident incident = new IncidentRepository().findById(crimeReportId); // Fetch CrimeReport
                    crimeCase.setCrimeReport(incident);

                    String caseOfficerUsername = rs.getString("case_officer_username");
                    User caseOfficer = new UserRepository().findByUsername(caseOfficerUsername); // Fetch CaseOfficer
                    crimeCase.setCaseOfficer((CaseOfficer) caseOfficer); // Assuming CaseOfficer is a User subclass

                    String witnessUsername = rs.getString("witness_username");
                    if (witnessUsername != null) {
                        User witness = new UserRepository().findByUsername(witnessUsername); // Fetch Citizen (Witness)
                        crimeCase.setWitness((Citizen) witness); // Assuming Citizen is a User subclass
                    }

                    // Fetch and set Investigation
                    int investigationId = rs.getInt("investigation_id");
                    if (investigationId != 0) { // Assuming 0 means no investigation is associated
                        Investigation investigation = new InvestigationRepository().findById(investigationId);
                        crimeCase.setInvestigation(investigation);
                    }

                    crimeCase.setStatus(CaseStatus.valueOf(rs.getString("status")));
                    crimeCase.setFinalRemarks(rs.getString("final_remarks"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving case by ID: " + e.getMessage());
        }

        return crimeCase;
    }

    public List<Case> findAll() {
        String sql = "SELECT * FROM cases";
        List<Case> cases = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Case crimeCase = new Case();
                crimeCase.setCaseId(rs.getInt("case_id"));

                // Retrieve associated objects
                int crimeReportId = rs.getInt("crime_report_id");
                Incident incident = new IncidentRepository().findById(crimeReportId);
                crimeCase.setCrimeReport(incident);

                String caseOfficerUsername = rs.getString("case_officer_username");
                User caseOfficer = new UserRepository().findByUsername(caseOfficerUsername);
                crimeCase.setCaseOfficer((CaseOfficer) caseOfficer);

                String witnessUsername = rs.getString("witness_username");
                if (witnessUsername != null) {
                    User witness = new UserRepository().findByUsername(witnessUsername); // Fetch Citizen (Witness)
                    crimeCase.setWitness((Citizen) witness);
                }

                int investigationId = rs.getInt("investigation_id");
                if (investigationId != 0) {
                    Investigation investigation = new InvestigationRepository().findById(investigationId);
                    crimeCase.setInvestigation(investigation);
                }

                crimeCase.setStatus(CaseStatus.valueOf(rs.getString("status")));
                crimeCase.setFinalRemarks(rs.getString("final_remarks"));

                cases.add(crimeCase);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all cases: " + e.getMessage());
        }

        return cases;
    }
}