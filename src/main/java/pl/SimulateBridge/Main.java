package pl.SimulateBridge;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/mainPane.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Główne okno aplikacji");
        stage.setScene(scene);
        stage.show();
        stage.toFront();
    }
}