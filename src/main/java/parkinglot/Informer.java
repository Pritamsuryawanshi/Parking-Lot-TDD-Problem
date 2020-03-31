package parkinglot;

import java.util.ArrayList;
import java.util.List;

public class Informer {
    public List<ParkingLotObserver> observers ;

    public Informer() {
       observers = new ArrayList<>();
    }

    public void registerParkingLotObserver(ParkingLotObserver observer) {
        observers.add(observer);
    }

    public void notifyFull() {
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
