package cbcrs.presentation.controllers;

import buisness.models.User;
import buisness.models.UserRole;
import buisness.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    public TextField phoneField;
    public TextField emailField;
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField confirmPasswordField;

    private UserService userService = new UserService();

    @FXML
    private void handleRegisterButton(ActionEvent event) {
        String username = usernameField.getText();
        String password = confirmPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // 1. Basic Input Validation
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match.");
            return;
        }

        // 2. Create User Object
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // The UserService will handle password hashing
        user.setRole(UserRole.CITIZEN); // Set the default role for new users

        // 3. Register User
        try {
            userService.registerUser(user);
            showAlert("Success", "Registration successful!");
            // You might want to clear the input fields or navigate back to the login page here
        } catch (Exception e) {
            showAlert("Error", "Registration failed: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cbcrs/presentation/login-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading login page: " + e.getMessage());
        }
    }
}