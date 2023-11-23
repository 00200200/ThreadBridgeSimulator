package pl.SimulateBridge.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SimulatePaneController {
    @FXML
    private VBox simulatePaneQueue;
    @FXML
    private HBox simulatePaneBridge;

    private static final int MaxCarsNumber = 25;

    public void generateLabels(int bridgeLength) {

        for (int i = 0; i < bridgeLength; i++) {
            Label label = new Label(Character.toString((char) ('a' + i)));
            simulatePaneBridge.getChildren().add(label);
        }
        for (int i = bridgeLength; i < MaxCarsNumber; i++) {
            Label label = new Label(Character.toString((char) ('a' + i)));
            simulatePaneQueue.getChildren().add(label);
        }
    }
}
