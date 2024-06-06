package cbcrs.presentation.controllers;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HelloController {
    @FXML
    private final MapPoint Islamabad = new MapPoint(33.6844, 73.0479);

    @FXML
    private VBox address;

    @FXML
    public void initialize() {
        if (address != null) {
            MapView mapView = createMapView();
            VBox.setVgrow(mapView, Priority.ALWAYS);
            mapView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            address.getChildren().add(mapView);
        } else {
            System.err.println("Error: address VBox is null!");
        }
    }

    @FXML
    private MapView createMapView() {
        MapView mapView = new MapView();
        mapView.setCenter(Islamabad);
        mapView.setZoom(12.5);

        // Add multiple markers
        List<MapPoint> markerPoints = new ArrayList<>();
        markerPoints.add(new MapPoint(33.7215, 73.0433)); // Example marker point 1
        markerPoints.add(new MapPoint(33.6941, 73.0606)); // Example marker point 2
        markerPoints.add(new MapPoint(33.6519, 73.1570)); // Example marker point 3
        // Add more marker points as needed

        Random random = new Random();
        for (MapPoint point : markerPoints) {
            mapView.addLayer(new CustomMapLayer(point, Color.MAROON));
        }

        return mapView;
    }

    private Color getRandomColor(Random random) {
        Color[] colors = {Color.MAROON, Color.RED, Color.YELLOW};
        return colors[random.nextInt(colors.length)];
    }

    // Custom MapLayer class (modified to accept MapPoint and Color)
    private class CustomMapLayer extends MapLayer {
        private final Node marker;
        private final MapPoint mapPoint;

        public CustomMapLayer(MapPoint mapPoint, Color color) {
            this.mapPoint = mapPoint;
            marker = new Circle(5, color); // Use the provided color
            getChildren().add(marker);
        }

        @Override
        protected void layoutLayer() {
            Point2D point = getMapPoint(mapPoint.getLatitude(), mapPoint.getLongitude());
            marker.setTranslateX(point.getX());
            marker.setTranslateY(point.getY());
        }
    }

    @FXML
    protected void onHelloButtonClick() {
        // nothing for the time being
    }
}