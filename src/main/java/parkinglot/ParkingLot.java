package parkinglot;

import java.util.*;
import java.util.stream.IntStream;

//Welcome to parking lot system
public class ParkingLot {
    private int actualCapacity;
    static List<ParkingSlots> vehicles = new ArrayList<>();
    Informer informer;
    ParkingRules parkingRules;

    public ParkingLot(int capacity) {
        parkingRules = new ParkingRules();
        informer = new Informer();
        this.actualCapacity = capacity;
        this.vehicles = new ArrayList<>();
        setNull();
    }

    private void setNull() {
        IntStream.range(0, actualCapacity)
                .forEach(slot -> vehicles.add(null));
    }

    public void registerParkingLotObserver(ParkingLotObserver observer) {
        informer.registerParkingLotObserver(observer);
    }

    public void setCapacity(int capacity) {
        this.actualCapacity = capacity;
    }

    public boolean park(Object vehicle, int availableSlot, DriverType type) throws ParkingLotException {
        ParkingSlots parkingSlots = new ParkingSlots(vehicle, type);
        if (isVehicleParked(vehicle))
            throw new ParkingLotException("Vehicle already parked");
        vehicles.set(availableSlot, parkingSlots);
        return true;
    }

    public boolean isVehicleParked(Object vehicle) {
        ParkingSlots parkingSlot = new ParkingSlots(vehicle);
        return vehicles.contains(parkingSlot);
    }

    public boolean unPark(Object vehicle) throws ParkingLotException {
        ParkingSlots parkingSlot = new ParkingSlots(vehicle);
        if (this.vehicles.contains(parkingSlot)) {
            vehicles.set(vehicles.indexOf(parkingSlot), null);
            informer.notifyAvailable();
            return true;
        }
        throw new ParkingLotException("Vehicle is not parked");
    }

    public boolean parkingAttendant(Object vehicle, DriverType type) throws ParkingLotException {
        ArrayList<Integer> availableSlot = getAvailableSlots(type);
        int spot = parkingRules.decideParkingSpot(type, availableSlot);
        return park(vehicle, spot, type);
    }

    private ArrayList<Integer> getAvailableSlots(DriverType type) throws ParkingLotException {
        ArrayList<Integer> availableSlots = new ArrayList();
        IntStream.range(0, actualCapacity)
                .filter(index -> vehicles.get(index) == null)
                .forEach(index -> availableSlots.add(index));
        return availableSlots;
    }


    public boolean isTimeSet(Object vehicle) {
        ArrayList<Integer> filledSlots = new ArrayList();
        IntStream.range(0, vehicles.size())
                .filter(index -> vehicles.get(index) != null)
                .forEach(index -> filledSlots.add(index));
        if (filledSlots.isEmpty())
            return false;
        for (int i = 0; i < filledSlots.size(); i++) {
            if (vehicles.get(filledSlots.get(i)).time == null) {
                return false;
            }
        }
        return true;
    }

    public int findMyCar(Object vehicle) throws ParkingLotException {
        ParkingSlots parkingSlots = new ParkingSlots(vehicle);
        if (vehicles.contains(parkingSlots)) {
            return vehicles.indexOf(parkingSlots);
        }
        throw new ParkingLotException("Car is not Parked");
    }
}
