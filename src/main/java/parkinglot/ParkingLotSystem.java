package parkinglot;

import java.util.ArrayList;
import java.util.List;

//Welcome to parking lot system
public class ParkingLotSystem {
    private int actualCapacity;
    private List vehicles;
    private List<ParkingLotObserver> observers;

    public ParkingLotSystem(int capacity) {
        this.observers = new ArrayList<>();
        this.vehicles = new ArrayList();
        this.actualCapacity = capacity;
    }

    public void registerParkingLotObserver(ParkingLotObserver observer) {
        this.observers.add(observer);
    }

    public void setCapacity(int capacity) {
        this.actualCapacity = capacity;
    }

    public void park(Object vehicle) throws ParkingLotException {
        if (this.vehicles.size() == this.actualCapacity) {
            for (ParkingLotObserver observers : observers) {
                observers.capacityIsFull();
            }
            throw new ParkingLotException("Parking Lot is full");
        }
        if (isVehicleParked(vehicle))
            throw new ParkingLotException("Vehicle already parked");
        this.vehicles.add(vehicle);
    }

    public boolean isVehicleParked(Object vehicle) {
        if (this.vehicles.contains(vehicle))
            return true;
        return false;
    }

    public boolean unPark(Object vehicle) {
        if (vehicle == null)
            return false;
        if (this.vehicles.contains(vehicle)) {
            this.vehicles.remove(vehicle);
            return true;
        }
        return false;
    }
}
