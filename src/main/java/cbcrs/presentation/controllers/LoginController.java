package cbcrs.presentation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button signUpButton;

    @FXML
    private void handleLoginButton(ActionEvent event) {
        // TODO: Implement login logic (connect to your business layer)
        String username = usernameField.getText();
        String password = passwordField.getText();

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
    }

    @FXML
    private void handleSignUpButton(ActionEvent event) {
        // TODO: Navigate to the sign-up page
        System.out.println("Sign up button clicked!");
    }
}