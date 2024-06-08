package cbcrs.presentation.controllers;

import buisness.models.Incident;
import buisness.services.IncidentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
        //highlightButton(homeButton);
        loadRecentIncidents();
    }

    private void createSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(() -> {
            JXMapViewer mapViewer = new JXMapViewer();
            TileFactoryInfo info = new OSMTileFactoryInfo();
            mapViewer.setTileFactory(new org.jxmapviewer.viewer.DefaultTileFactory(info));

            GeoPosition islamabad = new GeoPosition(33.6844, 73.0479);
            mapViewer.setZoom(0);
            mapViewer.setAddressLocation(islamabad);

            // Add a ComponentListener to update the preferred size of the JXMapViewer
            mapViewer.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    mapViewer.setPreferredSize(e.getComponent().getSize());
                }
            });

            swingNode.setContent(mapViewer);
        });
    }

    private void loadRecentIncidents() {
        List<Incident> recentIncidents = IncidentService.getRecentIncidents(); // Implement this method in IncidentService

        // Sort incidents by severity (assuming you have a severity attribute in Incident) and then by time
        recentIncidents.sort(Comparator.comparing(Incident::getSeverity).reversed()
                .thenComparing(Incident::getDateTime).reversed());

        ObservableList<Incident> incidentList = FXCollections.observableArrayList(recentIncidents);
        incidentListView.setItems(incidentList);

        // You might need to create a custom cell factory for the ListView to display incident details properly
        // incidentListView.setCellFactory(...); 
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cbcrs/presentation/ReportIncident.fxml"));
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
        // ...
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
