package pl.SimulateBridge.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pl.SimulateBridge.simulation.Bridge;
import pl.SimulateBridge.simulation.CarGenerator;

import java.util.ArrayList;
import java.util.List;
public class SimulatePaneController {
    @FXML
    private VBox simulatePaneQueue;
    @FXML
    private HBox simulatePaneBridge;
    private CarGenerator carGenerator;
    private Bridge bridge;
    private static final int MaxCarsNumber = 25;
    public void generateLabels(int bridgeLength,int capacity,int maxCarMass, int maxCarSize) {
        List<Label> bridgeLabels = new ArrayList<>();
        List<Label> queueLabels = new ArrayList<>();
        List<Label> allLabels = new ArrayList<>();

        for (int i = 0; i < bridgeLength; i++) {
            Label label = new Label(".");

            if(i == 0){
                label.setText("[.");
            }
            if(i == bridgeLength -1){
                label.setText(".]");
            }

            bridgeLabels.add(label);
            simulatePaneBridge.getChildren().add(label);
        }
        for (int i = bridgeLength; i < MaxCarsNumber; i++) {
            Label label = new Label(".");
            queueLabels.add(label);
            allLabels.add(label);
            simulatePaneQueue.getChildren().add(label);
        }
        for(Label label : bridgeLabels){
            allLabels.add(label);
        }

        bridge = new Bridge(capacity,bridgeLength,bridgeLabels,queueLabels,allLabels);
        carGenerator = new CarGenerator();
        startSimulation(maxCarMass,maxCarSize);
    }
    public void startSimulation(int maxCarMass,int maxCarSize){
        new Thread(() -> {
            try{
                carGenerator.generateVehicle(bridge,maxCarMass,maxCarSize);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
