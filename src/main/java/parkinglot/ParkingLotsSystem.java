package parkinglot;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotsSystem {
    List<ParkingLot> parkingLotList;
    boolean addLot;
    Informer informer;
    ParkingLot parkingLot;

    public ParkingLotsSystem() {
        informer = new Informer();
        this.parkingLotList = new ArrayList<>();
        parkingLot = new ParkingLot(2);
    }

    public void addLot(ParkingLot parkingLot) {
        addLot = this.parkingLotList.add(parkingLot);
    }

    public boolean isVehicleParked(Vehicle vehicle) {
        return parkingLot.isVehicleParked(vehicle);
    }

    public void registerParkingLots(ParkingLotObserver observer) {
        informer.registerParkingLotObserver(observer);
    }

    public boolean park(Vehicle vehicle, VehicleType type) throws ParkingLotException {
        return parkingLot.parkingAttendant(vehicle, type);
    }
}
