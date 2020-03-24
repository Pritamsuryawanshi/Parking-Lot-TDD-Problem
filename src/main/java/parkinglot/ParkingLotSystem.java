package parkinglot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.ZonedDateTime.now;

//Welcome to parking lot system
public class ParkingLotSystem {
    private int actualCapacity;
    private List vehicles;
    private Map parkingTime;
    private Object vehicle;

    private List<ParkingLotObserver> observers;
    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String time = myDateObj.format(myFormatObj);

    public ParkingLotSystem(int capacity) {
        parkingTime=new HashMap<Object,String>();
        observers = new ArrayList<>();
        vehicles = new ArrayList();
        actualCapacity = capacity;
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
        if (this.vehicles.size() == this.actualCapacity) {
            for (ParkingLotObserver observers : observers) {
                observers.capacityIsFull();
            }
            throw new ParkingLotException("Parking Lot is full");
        }
        this.parkingTime.put(vehicle,time);
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
            for (ParkingLotObserver observers : observers) {
                observers.capacityIsAvailable();
            }
            return true;
        }
        return false;
    }

    public void parkingAttendant(Object vehicle) throws ParkingLotException {
        this.park(vehicle);
    }

    public int findMyCar(Object vehicle) throws ParkingLotException {
        if(this.vehicles.contains(vehicle))
        return this.vehicles.indexOf(vehicle);
        throw new ParkingLotException("Car is not Parked");
    }

    public String getParkingTime(Object vehicle) {

        return (String) this.parkingTime.get(vehicle);
    }
}
