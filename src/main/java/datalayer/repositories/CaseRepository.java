package datalayer.repositories;
import buisness.models.*;
import datalayer.jdbc.CrimeReportRepositoryImpl;
import datalayer.jdbc.InvestigationRepositoryImpl;
import datalayer.jdbc.UserRepositoryImpl;
import datalayer.jdbc.WitnessRepositoryImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CaseRepository {
    private final String url = "jdbc:mysql://localhost:3306/sda_proj";
    private final String user = "root";
    private final String password = "root";

    public void save(Case crimeCase) {
        String sql = "INSERT INTO cases (crime_report_id, case_officer_username, witness_id, investigation_id, status, final_remarks) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, crimeCase.getCrimeReport().getIncidentId());
            pstmt.setString(2, crimeCase.getCaseOfficer().getUsername()); // Assuming CaseOfficer is a User subclass
            pstmt.setInt(3, crimeCase.getWitness() != null ? crimeCase.getWitness().getWitnessId() : 0);
            pstmt.setInt(4, crimeCase.getInvestigation() != null ? crimeCase.getInvestigation().getInvestigationId() : 0);
            pstmt.setString(5, crimeCase.getStatus().toString()); // Assuming CaseStatus is an enum
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
        String sql = "UPDATE cases SET crime_report_id = ?, case_officer_username = ?, witness_id = ?, investigation_id = ?, status = ?, final_remarks = ? " +
                "WHERE case_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, crimeCase.getCrimeReport().getIncidentId());
            pstmt.setString(2, crimeCase.getCaseOfficer().getUsername());
            pstmt.setInt(3, crimeCase.getWitness() != null ? crimeCase.getWitness().getWitnessId() : 0);
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
                    CrimeReport crimeReport = new CrimeReportRepositoryImpl().findById(crimeReportId); // Fetch CrimeReport
                    crimeCase.setCrimeReport(crimeReport);

                    String caseOfficerUsername = rs.getString("case_officer_username");
                    User caseOfficer = new UserRepositoryImpl().findByUsername(caseOfficerUsername); // Fetch CaseOfficer
                    crimeCase.setCaseOfficer((CaseOfficer) caseOfficer); // Assuming CaseOfficer is a User subclass

                    int witnessId = rs.getInt("witness_id");
                    if (witnessId != 0) { // Assuming 0 means no witness is associated
                        Witness witness = new WitnessRepositoryImpl().findById(witnessId);
                        crimeCase.setWitness(witness);
                    }

                    // Fetch and set Investigation
                    int investigationId = rs.getInt("investigation_id");
                    if (investigationId != 0) { // Assuming 0 means no investigation is associated
                        Investigation investigation = new InvestigationRepositoryImpl().findById(investigationId);
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
                CrimeReport crimeReport = new CrimeReportRepositoryImpl().findById(crimeReportId);
                crimeCase.setCrimeReport(crimeReport);

                String caseOfficerUsername = rs.getString("case_officer_username");
                User caseOfficer = new UserRepositoryImpl().findByUsername(caseOfficerUsername);
                crimeCase.setCaseOfficer((CaseOfficer) caseOfficer);

                int witnessId = rs.getInt("witness_id");
                if (witnessId != 0) {
                    Witness witness = new WitnessRepositoryImpl().findById(witnessId);
                    crimeCase.setWitness(witness);
                }

                int investigationId = rs.getInt("investigation_id");
                if (investigationId != 0) {
                    Investigation investigation = new InvestigationRepositoryImpl().findById(investigationId);
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