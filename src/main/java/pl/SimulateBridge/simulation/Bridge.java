package pl.SimulateBridge.simulation;
import javafx.application.Platform;
import javafx.scene.control.Label;
import java.util.List;
public class Bridge {
    private final int maxCapacity;
    private final int length;
    private int carsOnBridge;
    public List<Label> bridgeLabels;
    public List<Label> queueLabels;
    public List<Label> allLabels;
    public int currentCapacity;
    public Bridge(int maxCapacity, int length, List<Label> bridgeLabels, List<Label> queueLabels,List<Label> allLabels){
        this.maxCapacity = maxCapacity;
        this.length = length;
        this.bridgeLabels = bridgeLabels;
        this.queueLabels = queueLabels;
        this.currentCapacity = 0;
        this.allLabels = allLabels;
    }
    public synchronized boolean enter(Car car){
        if(currentCapacity + car.getMass() > maxCapacity || carsOnBridge + car.getSize() > length)
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        currentCapacity += car.getMass();
        carsOnBridge += car.getSize();
        return true;
    }
    public synchronized void exit(Car car){
        currentCapacity -= car.getMass();
        carsOnBridge -= car.getMainSize();
        notifyAll();
    }
    public synchronized void moveOnBridge(Car car){
        int index = car.getIndex();
        if (index >= allLabels.size()) {
            car.setState(Car.State.Exit);
            return;
        }
        Label currentLabel = allLabels.get(index);
        Platform.runLater(() -> {
            if (currentLabel.getText().equals(".") || currentLabel.getText().equals(".]")) {
                if(currentLabel.getText().equals(".")){
                    currentLabel.setText(String.valueOf(car.getIdentifier()));
                }
                if(currentLabel.getText().equals(".]")){
                    currentLabel.setText(car.getIdentifier() + "]");
                }
                Label lastLabel = allLabels.get(index - car.getSize());
                if (lastLabel.getText().equals("[" + car.getIdentifier())) {
                    lastLabel.setText("[.");
                } else {
                    lastLabel.setText(".");
                }
                car.setIndex(index + 1);
            }
        });
        notifyAll();
    }
}
