package cbcrs.presentation.controllers;

import buisness.models.Incident;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class RequestCaseUpdateController {

    private Incident incident; // To store the selected incident

    public RequestCaseUpdateController(Incident incident) {
        this.incident = incident;
    }

    @FXML
    private void handleYes(ActionEvent event) {
        // 1. Implement logic to request case update
        //    (e.g., update the incident status in the database,
        //    send a notification to the relevant officer)

        // For now, just display a success message
        showAlert("Success", "Case update request submitted successfully.");

        // Close the confirmation window
        closeWindow(event);
    }

    @FXML
    private void handleNo(ActionEvent event) {
        // Close the confirmation window
        closeWindow(event);
    }

    private void closeWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}