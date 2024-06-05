package datalayer.repositories;

import buisness.models.ContactInformation;

import java.sql.*;

public class ContactInformationRepository {
    private final String url = "jdbc:mysql://localhost:3306/sda_proj";
    private final String user = "root";
    private final String password = "root";

    public void save(ContactInformation contactInformation) {
        String sql = "INSERT INTO contact_information (phone_number, email_address) " +
                "VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, contactInformation.getPhoneNumber());
            pstmt.setString(2, contactInformation.getEmailAddress());

            pstmt.executeUpdate(); // Execute the insert

            // Retrieve the generated contact_id (if needed)
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedContactId = generatedKeys.getInt(1);
                    // You can use generatedContactId if you need it for logging or other purposes
                    System.out.println("Generated contact ID: " + generatedContactId);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saving contact information: " + e.getMessage());
        }
    }

    public int getContactIdFromUser(String username) {
        String sql = "SELECT contact_id FROM users WHERE username = ?";
        int contactId = 0;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    contactId = rs.getInt("contact_id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving contact id: " + e.getMessage());
        }

        return contactId;
    }

    //use the user id to update the contact information
    public void update(ContactInformation contactInformation, String username) {
        String sql = "UPDATE contact_information SET phone_number = ?, email_address = ? WHERE contact_id = ?";

        int contactId = getContactIdFromUser(username);
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, contactInformation.getPhoneNumber());
            pstmt.setString(2, contactInformation.getEmailAddress());
            pstmt.setInt(3, contactId);

            pstmt.executeUpdate(); // Execute the update
        } catch (SQLException e) {
            System.err.println("Error updating contact information: " + e.getMessage());
        }
    }
}
