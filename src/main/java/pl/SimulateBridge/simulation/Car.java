package pl.SimulateBridge.simulation;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class Car extends Thread {
    private final int mass;
    private final char identifier;
    private int index;
    private final int size;
    private Bridge bridge;

    public int getMass() {
        return mass;
    }

    public char getIdentifier() {
        return identifier;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Bridge getBridge() {
        return bridge;
    }

    public void setBridge(Bridge bridge) {
        this.bridge = bridge;
    }

    private enum State {
        Queue, ReadyToEnter, MoveOnBridge, Exit, Finished
    }

    private State state;

    private String getString() {
        String str = "";
        for (int i = 0; i < size; i++) {
            str += getIdentifier();
        }
        return str;
    }

    public int getSize() {
        return size;
    }

    public Car(int mass, char identifier, Bridge bridge, int size) {
        this.mass = mass;
        this.size = size;
        this.identifier = identifier;
        this.bridge = bridge;
        this.index = 0;
        this.state = State.Queue;
    }

    @Override
    public void run() {
        try {
            while (state != State.Finished) {
                switch (state) {
                    case Queue:
                        move();
                        break;
                    case ReadyToEnter:
                        enterBridge();
                        break;
                    case MoveOnBridge:
                        moveOnBridge();
                        break;
                    case Exit:
                        exit();
                        break;

                }
                Thread.sleep(255);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void enterBridge() {
        if (bridge.enter(this)) {
            Platform.runLater(() -> {
                Label currentLabel = bridge.bridgeLabels.get(0);
                if (currentLabel.getText() == "[") {
                    Label lastLabel = bridge.queueLabels.get(index - 1);
                    lastLabel.setText(".");
                    currentLabel.setText("[" + getString());
                    index = 1;
                    this.state = State.MoveOnBridge;
                }
            });
        }
        System.out.println("ENTER: " + getIdentifier());
    }
    private void moveOnBridge() {
        if(index >= bridge.bridgeLabels.size() -1){
            this.state = State.Exit;
            return;
        }
        Label currentLabel = bridge.bridgeLabels.get(index);
        Platform.runLater(() -> {
            if (currentLabel.getText() == "." && currentLabel != null) {
                currentLabel.setText(getString());
                if (index == 1) {
                    Label lastLabel = bridge.bridgeLabels.get(0);
                    lastLabel.setText("[");
                }else{
                    Label lastLabel = bridge.bridgeLabels.get(index -1);
                    lastLabel.setText(".");
                }
                index += 1;
            }
        });

    }
    private void move(){
        if (index == bridge.queueLabels.size()) {
            this.state = State.ReadyToEnter;
            return;
        }
        Label currentLabel = bridge.queueLabels.get(index);
        Platform.runLater(() -> {
            if (currentLabel.getText() == "." && currentLabel != null) {
                currentLabel.setText(getString());
                if (index >= 1) {
                    Label lastLabel = bridge.queueLabels.get(index - 1);
                    lastLabel.setText(".");
                }
                index += 1;
            }
        });

    }

    private void exit() {
        System.out.println("EXIT");
        Label currentLabel = bridge.bridgeLabels.get(index -1);
        Platform.runLater(() -> {
            currentLabel.setText(".");
            state = State.Finished;
        });
        CarGenerator.cars.remove(this);
        bridge.exit(this);
    }

;

}
