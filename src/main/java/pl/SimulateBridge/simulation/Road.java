package pl.SimulateBridge.simulation;
import javafx.application.Platform;
import javafx.scene.control.Label;
import java.util.List;
public class Road {
    private List<Label> queueLabels;
    public Road(List<Label> queueLabels){
        this.queueLabels = queueLabels;
    }
    public synchronized void move(Car car){
        int index = car.getIndex();
        if(index == queueLabels.size()){
            car.setState(car.setState(Car.State.ReadyToEnter));
            return;
        }
        Label currentLabel = queueLabels.get(index);
        Platform.runLater(() -> {
            if (currentLabel.getText().equals(".") || currentLabel.getText().equals(String.valueOf(car.getIdentifier()))) {
                moveCar(car,index);
                car.setIndex(index + 1);
            }
        });
    }
    private void moveCar(Car car, int index){
        for (int i = 0; i < car.getSize(); i++) {
            if (index - i >= 0) {
                Label currentLabel = queueLabels.get(index - i);
                currentLabel.setText(String.valueOf(car.getIdentifier()));
            }
        }
        if (index - car.getSize() >= 0) {
            Label lastLabel = queueLabels.get(index - car.getSize());
            lastLabel.setText(".");
        }
    }
}
