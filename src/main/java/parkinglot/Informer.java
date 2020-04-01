package parkinglot;

import java.util.ArrayList;
import java.util.List;

public class Informer {
    static List<ParkingLotObserver> observers ;

    public Informer() {
       observers = new ArrayList<>();
    }

    public void registerParkingLotObserver(ParkingLotObserver observer) {
        observers.add(observer);
        System.out.println("list in rgsiter"+observers);
    }

    public void notifyFull() {
        System.out.println("list in full"+observers);
        for (ParkingLotObserver observers : observers) {
            observers.capacityIsFull();
        }

    }

    public void notifyAvailable() {
        for (ParkingLotObserver observers : observers) {
            observers.capacityIsAvailable();
        }
    }
}
