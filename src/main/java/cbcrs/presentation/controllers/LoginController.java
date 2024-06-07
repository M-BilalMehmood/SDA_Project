package cbcrs.presentation.controllers;

import buisness.models.User;
import buisness.services.UserService;
import cbcrs.presentation.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    UserService userService = new UserService();

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button signUpButton;

    @FXML
    private void handleLoginButton(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = userService.loginUser(username, password);
        if (user != null) {
            // Login successful - navigate to the main application or dashboard
            System.out.println("Login successful!");
        } else {
            // Login failed - show error message
            System.out.println("Login failed!");
        }
    }

    @FXML
    private void handleSignUpButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cbcrs/presentation/Register-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading register page: " + e.getMessage());
        }
    }
}