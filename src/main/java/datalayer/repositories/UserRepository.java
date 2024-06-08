package datalayer.repositories;

import buisness.models.CaseOfficer;
import buisness.models.User;
import buisness.models.UserRole;

import java.sql.*;

public class UserRepository {

    private final String url = "jdbc:mysql://localhost:3306/sda_proj";
    private final String user = "root";
    private final String password = "root";

    public void save(User user) {
        String sql = "INSERT INTO users (username, password, role, phone_number, email_address) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, this.user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword()); // Remember to hash passwords in a real application!
            pstmt.setString(3, user.getRole().toString()); // Assuming UserRole is an enum
            pstmt.setString(4, user.getPhoneNumber());
            pstmt.setString(5, user.getEmail());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saving user: " + e.getMessage());
        }
    }

    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        User user = null;

        try (Connection conn = DriverManager.getConnection(url, this.user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(UserRole.valueOf(rs.getString("role"))); // Assuming UserRole is an enum
                    user.setPhoneNumber(rs.getString("phone_number"));
                    user.setEmail(rs.getString("email_address"));
                    // ... set other attributes from ResultSet
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving user: " + e.getMessage());
        }

        return user;
    }

    public CaseOfficer findOfficerByUsername(String assignedOfficerUsername) {
        String sql = "SELECT * FROM users WHERE username = ? AND role = 'CASE_OFFICER'";
        CaseOfficer caseOfficer = null;

        try (Connection conn = DriverManager.getConnection(url, this.user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, assignedOfficerUsername);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    caseOfficer = new CaseOfficer();
                    caseOfficer.setUsername(rs.getString("username"));
                    caseOfficer.setPassword(rs.getString("password"));
                    caseOfficer.setRole(UserRole.valueOf(rs.getString("role"))); // Assuming UserRole is an enum
                    caseOfficer.setPhoneNumber(rs.getString("phone_number"));
                    caseOfficer.setEmail(rs.getString("email_address"));
                    // ... set other attributes from ResultSet
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving case officer: " + e.getMessage());
        }

        return caseOfficer;
    }
}
