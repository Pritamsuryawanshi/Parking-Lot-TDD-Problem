package parkinglot;

import java.util.ArrayList;
import java.util.List;

public class ParkingRules {
    Informer informer = new Informer();

    public int decideParkingSpot(DriverType type, ArrayList<Integer> availableSpots) throws ParkingLotException {
        if (type.equals(DriverType.HANDICAP))
            return handicapParkingRules(availableSpots);
        if (type.equals(DriverType.LARGE_VEHICLE) && availableSpots.size()>2) {
            return largeVehicleParkingRules(availableSpots);
        }
        return NormalParkingRules(availableSpots);
    }

    public int NormalParkingRules(ArrayList<Integer> availableSlot) throws ParkingLotException {
        if (availableSlot.size() != 0)
            return availableSlot.get(availableSlot.size() - 1);
        throw new ParkingLotException("Lot is full");
    }

    public int largeVehicleParkingRules(ArrayList<Integer> availableSlot) throws ParkingLotException {
        for (int i = 1; i < availableSlot.size() - 1; i++) {
            if (availableSlot.get(i - 1) == availableSlot.get(i) - 1 && availableSlot.get(i + 1) == availableSlot.get(i) + 1) {
                return availableSlot.get(i);
            }
        }
        return availableSlot.get(0);
    }

    public int handicapParkingRules(ArrayList<Integer> availableSlot) throws ParkingLotException {
        if (availableSlot.size() != 0)
            return availableSlot.get(0);
        throw new ParkingLotException("Lot is full");
    }
}