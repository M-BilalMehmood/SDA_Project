package cbcrs.presentation.controllers;

import buisness.models.Incident;
import buisness.services.IncidentService;
import datalayer.repositories.IncidentRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class IncidentStatusController {

    @FXML
    private ListView<Incident> incidentListView;
    @FXML
    private Button homeButton; // Add fx:id for the Home button
    @FXML
    private Button reportIncidentButton; // Add fx:id for the Report Incident button
    @FXML
    private Button contactsButton; // Add fx:id for the Contacts button
    @FXML
    private Button moreButton; // Add fx:id for the More button
    private IncidentService incidentService;

    @FXML
    public void initialize() {
        incidentService = new IncidentService(new IncidentRepository()); // Use concrete implementation

        List<Incident> myIncidents = incidentService.getIncidentsForCurrentUser();

        ObservableList<Incident> incidentItems = FXCollections.observableArrayList(myIncidents);
        incidentListView.setItems(incidentItems);

        incidentListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Incident item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText("Incident ID: " + item.getIncidentId() +
                            ", Category: " + item.getCategory() +
                            ", Status: " + item.getStatus());
                }
            }
        });

        // Add listener to ListView to open confirmation form on click
        incidentListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                openCaseUpdateConfirmation(newValue);
            }
        });
    }

    // Method to open the case update confirmation form
    private void openCaseUpdateConfirmation(Incident incident) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cbcrs/presentation/RequestCaseUpdate.fxml"));
            loader.setControllerFactory(aClass -> new RequestCaseUpdateController(incident));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Request Case Update");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.err.println("Error loading RequestCaseUpdateConfirmation page: " + e.getMessage());
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
        try {
            // Load the Home-view.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cbcrs/presentation/ReportIncident-view.fxml"));
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

    public void handleContactsButton(ActionEvent actionEvent) {
    }

    public void handleMoreButton(ActionEvent actionEvent) {
    }
}