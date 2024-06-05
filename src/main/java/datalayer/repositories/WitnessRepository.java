package datalayer.repositories;

import buisness.models.Case;
import buisness.models.ContactInformation;
import buisness.models.Witness;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WitnessRepository {

    private final String url = "jdbc:mysql://localhost:3306/sda_proj";
    private final String user = "root";
    private final String password = "root";
    private final ContactInformationRepository contactInformationRepository = new ContactInformationRepository();

    public void save(Witness witness) {
        // 1. Save ContactInformation first
        ContactInformation contactInfo = witness.getContactInformation();
        contactInformationRepository.save(contactInfo);

        // 2. Now save the Witness
        String sql = "INSERT INTO witnesses (name, contact_id, case_id) " +
                "VALUES (?, LAST_INSERT_ID(), ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, witness.getName());
            pstmt.setInt(3, witness.getCrimeCase().getCaseId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        witness.setWitnessId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saving witness: " + e.getMessage());
        }
    }

    public Witness findById(int witnessId) {
        String sql = "SELECT w.witness_id, w.name, w.case_id, ci.phone_number, ci.email_address " +
                "FROM witnesses w " +
                "INNER JOIN contact_information ci ON w.contact_id = ci.contact_id " +
                "WHERE w.witness_id = ?";
        Witness witness = null;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, witnessId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    witness = new Witness();
                    witness.setWitnessId(rs.getInt("witness_id"));
                    witness.setName(rs.getString("name"));

                    // Create ContactInformation object
                    ContactInformation contactInfo = new ContactInformation();
                    contactInfo.setPhoneNumber(rs.getString("phone_number"));
                    contactInfo.setEmailAddress(rs.getString("email_address"));
                    witness.setContactInformation(contactInfo);

                    // Retrieve associated Case (if needed)
                    int caseId = rs.getInt("case_id");
                    Case crimeCase = new CaseRepository().findById(caseId);
                    witness.setCrimeCase(crimeCase);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving witness by ID: " + e.getMessage());
        }

        return witness;
    }

    public List<Witness> findAll() {
        String sql = "SELECT * FROM witnesses";
        List<Witness> witnesses = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Witness witness = new Witness();
                witness.setWitnessId(rs.getInt("witness_id"));
                witness.setName(rs.getString("name"));

                // Create ContactInformation object
                ContactInformation contactInfo = new ContactInformation();
                contactInfo.setPhoneNumber(rs.getString("phone_number"));
                contactInfo.setEmailAddress(rs.getString("email_address"));
                witness.setContactInformation(contactInfo);

                // Retrieve associated Case (if needed)
                // ... (similar to findById method)

                witnesses.add(witness);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all witnesses: " + e.getMessage());
        }

        return witnesses;
    }

    public void update(Witness witness) {
        String sql = "UPDATE witnesses SET name = ?, contact_information = ?, case_id = ? " +
                "WHERE witness_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, witness.getName());
            pstmt.setString(2, witness.getContactInformation().getPhoneNumber());
            pstmt.setString(3, witness.getContactInformation().getEmailAddress());
            pstmt.setInt(3, witness.getCrimeCase().getCaseId());
            pstmt.setInt(4, witness.getWitnessId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating witness: " + e.getMessage());
        }
    }

    public void delete(int witnessId) {
        String sql = "DELETE FROM witnesses WHERE witness_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, witnessId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting witness: " + e.getMessage());
        }
    }
}