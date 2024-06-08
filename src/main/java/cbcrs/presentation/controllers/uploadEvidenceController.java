package cbcrs.presentation.controllers;

import buisness.models.Evidence;
import buisness.models.Incident;
import buisness.services.EvidenceService;
import buisness.services.IncidentService;
import datalayer.repositories.EvidenceRepository;
import datalayer.repositories.IncidentRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class uploadEvidenceController {

    @FXML
    private TextField evidenceDescriptionField;

    @FXML
    private TextField evidenceFilePathField;

    @FXML
    private Button addEvidenceButton;

    private IncidentService crimeReportService;
    private EvidenceService evidenceService; // Add EvidenceService
    private Incident incident; // Store the incident being updated

    // Constructor to receive the incident
    public uploadEvidenceController(Incident incident) {
        this.incident = incident;
    }

    @FXML
    public void initialize() {
        // Initialize services (using your existing mechanism)
        crimeReportService = new IncidentService(new IncidentRepository());
        evidenceService = new EvidenceService(new EvidenceRepository());
    }

    @FXML
    private void handleAddEvidence(ActionEvent event) {
        String description = evidenceDescriptionField.getText();
        String filePath = evidenceFilePathField.getText();

        // Basic validation
        if (description.isEmpty()) {
            showAlert("Error", "Please enter a description for the evidence.");
            return;
        }

        try {
            // Add evidence to the incident
            Evidence evidence = evidenceService.uploadEvidence(description, filePath, incident);

            // Update the incident with the new evidence (if needed)
            // ... (you might need to update the incident status or other fields)

            // Show success message
            showAlert("Success", "Evidence added successfully.");

            // Close the AddEvidence window
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            showAlert("Error", "Error adding evidence: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}