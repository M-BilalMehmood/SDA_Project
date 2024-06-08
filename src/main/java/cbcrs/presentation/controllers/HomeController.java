package cbcrs.presentation.controllers;

import buisness.models.Incident;
import buisness.services.IncidentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.*;

public class HomeController {

    @FXML
    public HBox bottomHBox;
    @FXML
    private VBox SentinalLogo;
    @FXML
    private VBox incidentListContainer;
    @FXML
    private ListView<Incident> incidentListView;

    private IncidentService IncidentService = new IncidentService(null); // Inject your IncidentRepository here
    @FXML
    private Button homeButton; // Add fx:id for the Home button
    @FXML
    private Button reportIncidentButton; // Add fx:id for the Report Incident button
    @FXML
    private Button contactsButton; // Add fx:id for the Contacts button
    @FXML
    private Button moreButton; // Add fx:id for the More button

    @FXML
    public void initialize() {
        Insets bottomPadding = new Insets(20);
        Insets logoPadding = new Insets(10);
        bottomHBox.setPadding(bottomPadding);
        SentinalLogo.setPadding(logoPadding);
        incidentListContainer.setPadding(new Insets(20));
        incidentListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                openAddEvidenceWindow(newValue);
            }
        });
        loadRecentIncidents();
    }

    private void openAddEvidenceWindow(Incident incident) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cbcrs/presentation/UploadEvidence-view.fxml"));
            // Pass the incident to the AddEvidenceController
            loader.setControllerFactory(aClass -> new uploadEvidenceController(incident));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add Evidence");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.err.println("Error loading AddEvidence page: " + e.getMessage());
        }
    }

    private void loadRecentIncidents() {
        List<Incident> recentIncidents = IncidentService.getRecentIncidents();

        recentIncidents.sort(Comparator.comparing((Incident incident) -> incident.getSeverity() != null ? incident.getSeverity() : "").reversed());

        ObservableList<Incident> incidentList = FXCollections.observableArrayList(recentIncidents);
        incidentListView.setItems(incidentList);

        // Create a custom cell factory for the incident list view
        incidentListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Incident incident, boolean empty) {
                super.updateItem(incident, empty);
                if (empty || incident == null) {
                    setText(null);
                } else {
                    setText("Incident ID: " + incident.getIncidentId() +
                            "\nLocation: " + incident.getLocation() +
                            "\nDate Time: " + incident.getDateTime() +
                            "\nCategory: " + incident.getCategory());
                }
            }
        });
    }


    // Navigation Button Handlers (replace with your navigation logic)
    @FXML
    private void handleHomeButton(ActionEvent event) {
        // ...
    }

    @FXML
    private void handleReportIncidentButton(ActionEvent event) {
        //call the report incident page
        try {
            // Load the Home-view.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cbcrs/presentation/ReportIncident-view.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("Error loading home page: " + e.getMessage());
        }
    }

    @FXML
    private void handleContactsButton(ActionEvent event) {
        try {
            // Load the Home-view.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cbcrs/presentation/IncidentStatus-view.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("Error loading home page: " + e.getMessage());
        }
    }

    @FXML
    private void handleMoreButton(ActionEvent event) {
        // ...
    }

    private void highlightButton(Button button) {
        // Reset all buttons to gray
        homeButton.setStyle("-fx-background-radius: 20; -fx-background-color: #cccccc; -fx-text-fill: black;");
        reportIncidentButton.setStyle("-fx-background-radius: 20; -fx-background-color: #cccccc; -fx-text-fill: black;");
        contactsButton.setStyle("-fx-background-radius: 20; -fx-background-color: #cccccc; -fx-text-fill: black;");
        moreButton.setStyle("-fx-background-radius: 20; -fx-background-color: #cccccc; -fx-text-fill: black;");

        // Highlight the selected button
        button.setStyle("-fx-background-radius: 20; -fx-background-color: #007bff; -fx-text-fill: white;");
    }
}
