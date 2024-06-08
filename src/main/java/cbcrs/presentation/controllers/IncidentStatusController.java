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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class IncidentStatusController {

    @FXML
    private ListView<Incident> incidentListView; // Now it's a ListView<Incident>

    private IncidentService IncidentService; // You'll need to initialize this

    @FXML
    public void initialize() {
        // 1. Initialize IncidentService (using your existing mechanism)
        IncidentService = new IncidentService(new IncidentRepository());

        // 2. Fetch incident data from the database
        List<Incident> myIncidents = IncidentService.getIncidentsForCurrentUser();

        // 3. Populate the ListView
        ObservableList<Incident> incidentItems = FXCollections.observableArrayList(myIncidents);
        incidentListView.setItems(incidentItems);

        // 4. Configure the ListView to display Incident objects
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