package pl.SimulateBridge.simulation;

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
    };

    public synchronized boolean enter(Car car){
        if(currentCapacity + car.getMass() > maxCapacity || carsOnBridge + car.getSize() > length ){
            return false;
        }
        currentCapacity += car.getMass();
        carsOnBridge += car.getSize();
        return true;
    }

    public synchronized void exit(Car car){
        currentCapacity -= car.getMass();
        carsOnBridge -= car.getSize();
    }
}
