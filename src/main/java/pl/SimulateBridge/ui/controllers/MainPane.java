package pl.SimulateBridge.ui.controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

import java.io.IOException;
public class MainPane {
    @FXML
    private TextField mainPaneCapacity;
    @FXML
    private TextField mainPaneLength;
    @FXML
    private TextField mainPaneMaxCarMass;
    @FXML
    private TextField mainPaneMaxCarSize;

    @FXML
    private Label mainPaneWarningLabel;


    public void openSimulateWindow(){
        if(mainPaneCapacity != null && mainPaneLength != null && mainPaneMaxCarMass != null && mainPaneMaxCarSize != null){
            int bridgeLength = Integer.parseInt(mainPaneLength.getText());
            int bridgeCapacity = Integer.parseInt(mainPaneCapacity.getText());
            int maxCarMass = Integer.parseInt(mainPaneMaxCarMass.getText());
            int maxCarSize = Integer.parseInt(mainPaneMaxCarSize.getText());
            if(maxCarSize >= bridgeLength || maxCarMass >= bridgeCapacity){
                mainPaneWarningLabel.setText("Rozmiar auta musi byc mniejszy od dlugosci mostu oraz Masa musi byc mniejsza od nosnosci mostu");
                return;
            }

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/simulatePane.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("Symulacja");
                stage.setScene(scene);
                stage.show();
                stage.toFront();
                SimulatePaneController simulatePaneController = fxmlLoader.getController();
                simulatePaneController.generateLabels(bridgeLength,bridgeCapacity,maxCarMass,maxCarSize);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
