package pl.SimulateBridge.simulation;

import java.util.Properties;

public class Car extends Thread{

    private final int mass;
    private final char identifier;

    public Car(int mass, char identifier){
        this.mass = mass;
        this.identifier = identifier;
    }

}
