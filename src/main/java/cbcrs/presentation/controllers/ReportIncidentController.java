package cbcrs.presentation.controllers;

import buisness.models.*;
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

import static buisness.services.IncidentService.currentUser;
import static buisness.services.IncidentService.getCurrentUser;

public class ReportIncidentController {

    public HBox bottomHBox;
    public CheckBox anonymousCheckBox;
    public ComboBox incidentTypeComboBox;
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
    public void initialize() {
        uploadImageButton.setOnAction(this::handleUploadImage);
        uploadVoiceButton.setOnAction(this::handleUploadVoice);
        submitButton.setOnAction(this::handleSubmit);
    }

    private void handleUploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            // Handle the image file...
        }
    }

    private void handleUploadVoice(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            // Handle the voice file...
        }
    }

    private void handleSubmit(ActionEvent event) {
        String incidentType = incidentTypeComboBox.getValue().toString();
        String name = nameField.getText();
        String location = locationField.getText();
        String description = descriptionField.getText();

        if (incidentType.isEmpty() || name.isEmpty() || location.isEmpty() || description.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the required fields.");
            alert.showAndWait();
        } else {
            // Create a new Incident object
            Incident newIncident = new Incident();
            newIncident.setDescription(description);
            newIncident.setCategory(CaseCategory.valueOf(incidentType));
            newIncident.setStatus("Pending");
            newIncident.setLocation(String.valueOf(locationField));
            if (anonymousCheckBox.isSelected()) {
                newIncident.setReporter(null);
            } else {
                newIncident.setReporter(getCurrentUser());
            }

            newIncident.setEvidence(new Evidence());
            newIncident.getEvidence().setEvidenceId(IncidentRepository.getLastEvidenceId() + 1);

            //newIncident.setCategory(CaseCategory.valueOf(incidentType));

            IncidentRepository incidentRepository = new IncidentRepository();
            incidentRepository.save(newIncident);

            // Show a success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Incident report submitted successfully.");
            alert.showAndWait();

            // Clear the form fields
            //incidentTypeField.clear();
            nameField.clear();
            locationField.clear();
            descriptionField.clear();
        }
    }


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