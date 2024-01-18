package pl.SimulateBridge.simulation;
import javafx.application.Platform;
import javafx.scene.control.Label;
public class Car extends Thread {
    private final int mass;
    private final char identifier;
    public int getIndex() {
        return index;
    }
    public State setState(State state) {
        this.state = state;
        return state;
    }
    private State state;
    public void setIndex(int index) {
        this.index = index;
    }
    private int index;
    private int size;
    private final int mainSize;
    private final Bridge bridge;
    public int getMass() {
        return mass;
    }
    public char getIdentifier() {
        return identifier;
    }
    public int getMainSize() {
        return mainSize;
    }
    enum State {
        Queue, ReadyToEnter, MoveOnBridge, Exit, Finished
    }
    private Road road;
    public int getSize() {
        return size;
    }
    public Car(int mass, char identifier, Bridge bridge, int size,Road road) {
        this.mass = mass;
        this.size = size;
        this.identifier = identifier;
        this.bridge = bridge;
        this.index = 0;
        this.state = State.Queue;
        this.mainSize = size;
        this.road = road;
    }

    @Override
    public void run() {
        try {
            while (state != State.Finished) {
                switch (state) {
                    case Queue:
                        road.move(this);
                        break;
                    case ReadyToEnter:
                        enterBridge();
                        break;
                    case MoveOnBridge:
                        bridge.moveOnBridge(this);
                        break;
                    case Exit:
                        exit();
                        break;
                }
                Thread.sleep(111);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void enterBridge() {
        synchronized (bridge){
            if(!bridge.enter(this)){
                return;
            }
        }
        Platform.runLater(() -> {
            Label currentLabel = bridge.bridgeLabels.get(0);
            if (currentLabel.getText().equals("[.")) {
                Label lastLabel = bridge.queueLabels.get(index - size);
                lastLabel.setText(".");
                currentLabel.setText("[" + getIdentifier());
                index += 1;
                this.state = State.MoveOnBridge;
                }
            });
        System.out.println("ENTER: " + getIdentifier());
    }
    private void exit() {
        System.out.println("EXIT");
        Platform.runLater(() -> {
            Label currentLabel = bridge.allLabels.get(index - size);
            if (currentLabel.getText().equals(getIdentifier() + "]")) {
                    currentLabel.setText(".]");
            }
            else {
                currentLabel.setText(".");
            }
            size = size -1;
            if(size == 0){
                state = State.Finished;
                CarGenerator.cars.remove(this);
                bridge.exit(this);
            }
        });
    }
}
