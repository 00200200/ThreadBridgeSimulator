package pl.SimulateBridge.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CarGenerator {
    public static List<Car> cars = new ArrayList<>();
    private char nextChar = 'a';
    public void generateVehicle(Bridge bridge, int maxMass,int length,int maxCarMass,int maxCarSize) throws InterruptedException {
        new Thread(() -> {
            System.out.println("WATEK CAR GENERATOR");
            while (true) {
                if (bridge.allLabels.get(0).getText() == "." && cars.size() < 25) {
                    System.out.println("CAR GENERATOR");
                    int mass = new Random().nextInt(maxCarMass) + 1;
                    int size = new Random().nextInt(maxCarSize) + 1;
                    Car car = new Car(mass, nextChar, bridge, size);
                    cars.add(car);
                    car.start();
                    nextChar = (char) (nextChar + 1);
                    if(nextChar > 'z'){
                        nextChar = 'a';
                    }
                }
                try {
                    Thread.sleep(750);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }


}
