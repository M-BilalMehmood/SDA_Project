package cbcrs.presentation.controllers;

import buisness.models.*;
import datalayer.repositories.EvidenceRepository;
import datalayer.repositories.IncidentRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Locale;
import static buisness.services.IncidentService.getCurrentUser;

public class ReportIncidentController {

    @FXML
    private HBox bottomHBox;
    @FXML
    private CheckBox anonymousCheckBox;
    @FXML
    private ComboBox<String> incidentTypeComboBox;
    @FXML
    private TextField nameField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private Button uploadImageButton;
    @FXML
    private Button uploadVoiceButton;
    @FXML
    private TextField locationField;
    @FXML
    private Button submitButton;
    @FXML
    private Label uploadStatusLabel;

    private IncidentRepository incidentRepository = new IncidentRepository();
    private EvidenceRepository evidenceRepository = new EvidenceRepository();

    @FXML
    public void initialize() {
        uploadImageButton.setOnAction(this::handleUploadImage);
        uploadVoiceButton.setOnAction(this::handleUploadVoice);
        submitButton.setOnAction(this::handleSubmit);

        // Set items for the incident type combo box
        incidentTypeComboBox.getItems().addAll("THEFT", "ASSAULT", "VANDALISM", "RAPE", "OTHERS");
    }

    private void handleUploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            uploadStatusLabel.setText("Image uploaded: " + file.getAbsolutePath());
        }
    }

    private void handleUploadVoice(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            uploadStatusLabel.setText("Voice message uploaded: " + file.getAbsolutePath());
        }
    }

    private void handleSubmit(ActionEvent event) {
        String incidentType = incidentTypeComboBox.getValue(); // Get selected incident type
        String name = nameField.getText();
        String location = locationField.getText();
        String description = descriptionField.getText();

        if (incidentType == null || name.isEmpty() || location.isEmpty() || description.isEmpty()) {
            showAlert("Error", "Please fill in all the required fields.");
        } else {
            // Create a new Incident object
            Incident newIncident = new Incident();
            newIncident.setDescription(description);
            newIncident.setCategory(CaseCategory.valueOf(incidentTypeComboBox.getValue().toString())); // Assuming Category is your enum
            newIncident.setDateTime(LocalDateTime.now());
            newIncident.setStatus("Pending");
            newIncident.setLocation(String.valueOf(locationField));
            if (anonymousCheckBox.isSelected()) {
                newIncident.setReporter(null);
            } else {
                newIncident.setReporter(getCurrentUser());
            }

            // Save the incident first
            incidentRepository.save(newIncident);

            // Now create and save Evidence (if needed)
            String evidenceDescription = descriptionField.getText();
            String evidenceFilePath = uploadStatusLabel.getText().replace("Image uploaded: ", "").replace("Voice message uploaded: ", "").trim();

            if (!evidenceDescription.isEmpty() || !evidenceFilePath.isEmpty()) {
                Evidence evidence = new Evidence();
                evidence.setDescription(evidenceDescription);
                evidence.setFilePath(evidenceFilePath);
                evidence.setIncident(newIncident); // Associate with the saved incident
                evidenceRepository.save(evidence);
            }

            // Show a success message
            showAlert("Success", "Incident report submitted successfully.");

            // Clear the form fields
            incidentTypeComboBox.getSelectionModel().clearSelection();
            nameField.clear();
            locationField.clear();
            descriptionField.clear();
            uploadStatusLabel.setText("");
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
    private Button homeButton; // Add fx:id for the Home button
    @FXML
    private Button reportIncidentButton; // Add fx:id for the Report Incident button
    @FXML
    private Button contactsButton; // Add fx:id for the Contacts button
    @FXML
    private Button moreButton; // Add fx:id for the More button

    public void handleHomeButton(ActionEvent actionEvent) {
        try {
            // Load the Home-view.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cbcrs/presentation/Home-view.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("Error loading home page: " + e.getMessage());
        }
    }

    public void handleReportIncidentButton(ActionEvent actionEvent) {
    }

    public void handleContactsButton(ActionEvent actionEvent) {
        try {
            // Load the Home-view.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cbcrs/presentation/IncidentStatus-view.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("Error loading home page: " + e.getMessage());
        }
    }

    public void handleMoreButton(ActionEvent actionEvent) {
    }
}