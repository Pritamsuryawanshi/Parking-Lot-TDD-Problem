package parkinglot;

import java.util.*;
import java.util.stream.IntStream;

//Welcome to parking lot system
public class ParkingLot {
    private int actualCapacity;
    static List<ParkingSlots> vehicles = new ArrayList<>();
    Informer informer;
    ParkingRules parkingRules;
    ArrayList<Integer> list ;

    public ParkingLot(int capacity) {
        list= new ArrayList<>();
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

    public boolean park(Object vehicle, int availableSlot, VehicleType type, String brand, String colour, String plateNumber) throws ParkingLotException {
        ParkingSlots parkingSlots = new ParkingSlots(vehicle, type, colour, brand, plateNumber);
        if (isVehicleParked(vehicle))
            throw new ParkingLotException("Vehicle already parked");
        vehicles.set(availableSlot, parkingSlots);
        list.add(vehicles.indexOf(parkingSlots));
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

    public boolean parkingAttendant(Object vehicle, VehicleType type, String brand, String colour, String plateNumber) throws ParkingLotException {
        ArrayList<Integer> availableSlot = getAvailableSlots(type);
        int spot = parkingRules.decideParkingSpot(type, availableSlot);
        return park(vehicle, spot, type, colour, brand, plateNumber);
    }

    private ArrayList<Integer> getAvailableSlots(VehicleType type) throws ParkingLotException {
        ArrayList<Integer> availableSlots = new ArrayList();
        IntStream.range(0, actualCapacity)
                .filter(index -> vehicles.get(index) == null)
                .forEach(index -> availableSlots.add(index));
        if (availableSlots.size() == 1)
            informer.notifyFull();
        if (availableSlots.isEmpty())
            throw new ParkingLotException("Lot is full");
        return availableSlots;
    }

    public boolean isTimeSet() {
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

    public ArrayList<Integer> findCarByColour(ArrayList<Integer> list, String colour) {
        ArrayList<Integer> carsMatchingColour = new ArrayList();
        IntStream.range(0, list.size())
                .filter(index -> vehicles.get(list.get(index)) != null)
                .filter(index -> vehicles.get(list.get(index)).colour == colour)
                .forEach(index -> carsMatchingColour.add(list.get(index)));
        return carsMatchingColour;
    }

    public ArrayList<Integer> findCarByBrand(ArrayList<Integer> carList, String brand) {
        ArrayList<Integer> carsMatchingBrand = new ArrayList();
        IntStream.range(0, carList.size())
                .filter(index -> vehicles.get(carList.get(index)) != null)
                .filter(index -> vehicles.get(carList.get(index)).brand == brand)
                .forEach(index -> carsMatchingBrand.add(carList.get(index)));
        return carsMatchingBrand;
    }

    public ArrayList<Integer> getVehicleList() {
        return list;
    }

    public ParkingSlots getObject(Integer index) {
        return vehicles.get(index);
    }
}
