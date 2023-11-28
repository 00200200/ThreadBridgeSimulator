package pl.SimulateBridge.simulation;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class Car extends Thread {
    private final int mass;
    private final char identifier;
    private int index;
    private int size;
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

//    private String getString() {
//        String str = "";
//        for (int i = 0; i < size; i++) {
//            str += getIdentifier();
//        }
//        return str;
//    }


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
                Thread.sleep(666);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void enterBridge() {
        if (bridge.enter(this)) {
            Platform.runLater(() -> {
                Label currentLabel = bridge.bridgeLabels.get(0);
                if (currentLabel.getText() == "[.") {
                    Label lastLabel = bridge.queueLabels.get(index - size);
                    lastLabel.setText(".");
                    currentLabel.setText("[" + getIdentifier());
                    index += 1;
                    this.state = State.MoveOnBridge;
                }
            });
        }
        System.out.println("ENTER: " + getIdentifier());
    }

    private void moveOnBridge() {
        if (index >= bridge.allLabels.size()) {
            this.state = State.Exit;
            return;
        }
        Label currentLabel = bridge.allLabels.get(index);
        Platform.runLater(() -> {
            if (currentLabel.getText() == "." || currentLabel.getText() == ".]") {

                if(currentLabel.getText() == "."){
                    currentLabel.setText(String.valueOf(getIdentifier()));
                }
                if(currentLabel.getText() == ".]"){
                    currentLabel.setText(String.valueOf(getIdentifier()) + "]");
                }
                Label lastLabel = bridge.allLabels.get(index - size);
                if (lastLabel.getText().equals("[" + getIdentifier())) {
                    lastLabel.setText("[.");
                } else {
                    lastLabel.setText(".");
                }
                index += 1;
            }
        });

    }

    private void move() {
        if (index == bridge.queueLabels.size()) {
            this.state = State.ReadyToEnter;
            return;
        }
        Label currentLabel = bridge.queueLabels.get(index);
        Platform.runLater(() -> {
            if (currentLabel.getText() == "." || currentLabel.getText().equals(String.valueOf(getIdentifier()))) {
                moveHe();
                index += 1;
            }
        });
    }

    private void moveHe() {
        for (int i = 0; i < size; i++) {
            if (index - i >= 0) {
                Label currentLabel = bridge.queueLabels.get(index - i);
                currentLabel.setText(String.valueOf(getIdentifier()));
            }
        }
        if (index - size >= 0) {
            Label lastLabel = bridge.queueLabels.get(index - size);
            lastLabel.setText(".");
        }
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
            }
        });
        CarGenerator.cars.remove(this);
        bridge.exit(this);
    }

    ;

}
