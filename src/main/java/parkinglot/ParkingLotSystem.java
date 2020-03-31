package parkinglot;

import java.util.*;
import java.util.stream.IntStream;

//Welcome to parking lot system
public class ParkingLotSystem {
    private int actualCapacity;
    private List<ParkingSlots> vehicles;
    Informer informer;

    public ParkingLotSystem(int capacity) {
        informer = new Informer();
        this.actualCapacity = capacity;
        this.vehicles = new ArrayList<>();
        setNull();
    }

    private void setNull() {
        if (vehicles.isEmpty()) {
            IntStream.range(0, actualCapacity)
                    .forEach(slot -> vehicles.add(null));
        }
    }

    public void registerParkingLotObserver(ParkingLotObserver observer) {
        informer.registerParkingLotObserver(observer);
    }

    public void setCapacity(int capacity) {
        this.actualCapacity = capacity;
    }

    public void park(Object vehicle, int availableSlot) throws ParkingLotException {
        ParkingSlots parkingSlots = new ParkingSlots(vehicle);
        if (isVehicleParked(vehicle))
            throw new ParkingLotException("Vehicle already parked");
        vehicles.set(availableSlot, parkingSlots);
    }

    public boolean isVehicleParked(Object vehicle) {
        ParkingSlots parkingSlot = new ParkingSlots(vehicle);
        if (this.vehicles.contains(parkingSlot))
            return true;
        return false;
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

    public void parkingAttendant(Object vehicle, DriverType type) throws ParkingLotException {
        int availableSlot = getAvailableSlots(type);
        park(vehicle, availableSlot);
    }

    private int getAvailableSlots(DriverType type) throws ParkingLotException {
        ArrayList<Integer> availableSlot = new ArrayList();
        IntStream.range(0, actualCapacity)
                .filter(index -> vehicles.get(index) == null)
                .forEach(index -> availableSlot.add(index));
        if (availableSlot.size() == 1) {
            informer.notifyFull();
            return availableSlot.get(0);
        }
        return decideSlot(availableSlot, type);
    }

    private int decideSlot(ArrayList<Integer> availableSlot, DriverType type) throws ParkingLotException {
        if (availableSlot.size() != 0 && type.equals(DriverType.HANDICAP)) {
            return availableSlot.get(0);
        }
        if (availableSlot.size() != 0 && type.equals(DriverType.NORMAL)) {
            return availableSlot.get(0) + 1;
        }
        throw new ParkingLotException("Lot is full");
    }

    public int findMyCar(Object vehicle) throws ParkingLotException {
        ParkingSlots parkingSlots = new ParkingSlots(vehicle);
        if (vehicles.contains(parkingSlots)) {
            return vehicles.indexOf(parkingSlots);
        }
        throw new ParkingLotException("Car is not Parked");
    }
}
