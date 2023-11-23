module simulateBridgeThreads {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    exports pl.SimulateBridge.ui.controllers to javafx.fxml;
    exports pl.SimulateBridge to javafx.graphics;
    opens  pl.SimulateBridge.ui.controllers to javafx.fxml;

}