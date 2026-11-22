/**
 * Author: Brandon Garcia
 * @author Brandon Garcia, TrafficLight
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.LinkedHashMap;
import java.util.Map;

public class TrafficLight extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the three circles representing the lights (initially off / white)
        Circle redLight = createLightCircle();
        Circle yellowLight = createLightCircle();
        Circle greenLight = createLightCircle();

        // Store radio buttons paired with their light circles and active colors
        // using a LinkedHashMap to preserve display order (Red -> Yellow -> Green)
        Map<RadioButton, LightConfig> lightControls = new LinkedHashMap<>();
        RadioButton rbRed = new RadioButton("Red");
        RadioButton rbYellow = new RadioButton("Yellow");
        RadioButton rbGreen = new RadioButton("Green");

        lightControls.put(rbRed, new LightConfig(redLight, Color.RED));
        lightControls.put(rbYellow, new LightConfig(yellowLight, Color.YELLOW));
        lightControls.put(rbGreen, new LightConfig(greenLight, Color.GREEN));

        // Group radio buttons so only one light can be selected at a time
        ToggleGroup group = new ToggleGroup();
        HBox radioPane = new HBox(20);
        radioPane.setAlignment(Pos.CENTER);
        radioPane.setPadding(new Insets(10));

        for (Map.Entry<RadioButton, LightConfig> entry : lightControls.entrySet()) {
            RadioButton rb = entry.getKey();
            rb.setToggleGroup(group);
            radioPane.getChildren().add(rb);

            // Update light colors whenever a radio button is selected
            rb.setOnAction(e -> {
                for (LightConfig config : lightControls.values()) {
                    config.circle.setFill(Color.WHITE);
                }
                entry.getValue().circle.setFill(entry.getValue().activeColor);
            });
        }

        // Arrange the three lights vertically inside the traffic light housing
        VBox lightsBox = new VBox(10, redLight, yellowLight, greenLight);
        lightsBox.setAlignment(Pos.CENTER);

        Rectangle housing = new Rectangle(80, 180);
        housing.setFill(Color.WHITE);
        housing.setStroke(Color.BLACK);

        StackPane trafficLightPane = new StackPane(housing, lightsBox);
        trafficLightPane.setPadding(new Insets(20));

        // Main layout
        BorderPane root = new BorderPane();
        root.setCenter(trafficLightPane);
        root.setBottom(radioPane);

        Scene scene = new Scene(root, 300, 260);
        primaryStage.setTitle("Traffic Light");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /** Creates a circular light bulb with a black outline and white (off) fill */
    private Circle createLightCircle() {
        Circle circle = new Circle(20);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        return circle;
    }

    /** Helper class binding a Circle node to its active Color */
    private static class LightConfig {
        final Circle circle;
        final Color activeColor;

        LightConfig(Circle circle, Color activeColor) {
            this.circle = circle;
            this.activeColor = activeColor;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}