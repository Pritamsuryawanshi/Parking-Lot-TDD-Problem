package parkinglot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

//Welcome to parking lot system
public class ParkingLotSystem {
    private int actualCapacity;
    private LinkedHashMap vehicles;
    private List<ParkingLotObserver> observers;
    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String time = myDateObj.format(myFormatObj);

    public ParkingLotSystem(int capacity) {
        this.vehicles = new LinkedHashMap();
        this.observers = new ArrayList<>();
        this.actualCapacity = capacity;
    }

    public void registerParkingLotObserver(ParkingLotObserver observer) {
        this.observers.add(observer);
    }

    public void setCapacity(int capacity) {
        this.actualCapacity = capacity;
    }

    public void park(Object vehicle) throws ParkingLotException {
        if (isVehicleParked(vehicle))
            throw new ParkingLotException("Vehicle already parked");
        if (vehicles.size() == actualCapacity) {
            for (ParkingLotObserver observers : observers) {
                observers.capacityIsFull();
            }
            throw new ParkingLotException("Parking Lot is full");
        }
        vehicles.put(vehicle, time);
    }

    public boolean isVehicleParked(Object vehicle) {
        if (vehicles.containsKey(vehicle))
            return true;
        return false;
    }

    public boolean unPark(Object vehicle) {
        if (vehicle == null)
            return false;
        if (vehicles.containsKey(vehicle)) {
            vehicles.remove(vehicle);
            for (ParkingLotObserver observers : observers) {
                observers.capacityIsAvailable();
            }
            return true;
        }
        return false;
    }

    public void parkingAttendant(Object vehicle) throws ParkingLotException {
        park(vehicle);
    }

    public int findMyCar(Object vehicle) throws ParkingLotException {
        if (vehicles.containsKey(vehicle)) {
            Set<Integer> keys = vehicles.keySet();
            List<Integer> listKeys = new ArrayList<>(keys);
            return listKeys.indexOf(vehicle);
        }
        throw new ParkingLotException("Car is not Parked");
    }

    public String getParkingTime(Object vehicle) {
        return (String) vehicles.get(vehicle);
    }
}
