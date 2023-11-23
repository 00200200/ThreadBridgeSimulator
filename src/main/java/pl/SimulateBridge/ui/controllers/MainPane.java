package pl.SimulateBridge.ui.controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPane {
    @FXML
    private TextField mainPaneCapacity;
    @FXML
    private TextField mainPaneLength;
    @FXML
    private Button mainPaneStartButton;


    public void openSimulateWindow(){
        if(mainPaneCapacity != null && mainPaneLength != null){
            int bridgeLength = Integer.parseInt(mainPaneLength.getText());

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/simulatePane.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("Symulacja");
                stage.setScene(scene);
                stage.show();
                stage.toFront();
                SimulatePaneController simulatePaneController = fxmlLoader.getController();
                simulatePaneController.generateLabels(bridgeLength);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
