package parkinglot;

import java.util.ArrayList;
import java.util.List;

public class ParkingRules {
    Informer informer = new Informer();

    public int decideParkingSpot(DriverType type, ArrayList<Integer> availableSpots) throws ParkingLotException {
        if (availableSpots.size() == 1) {
            informer.notifyFull();
            return availableSpots.get(0);
        }
        if (type.equals(DriverType.HANDICAP))
            return handicapParkingRules(availableSpots);
        if (type.equals(DriverType.LARGE_VEHICLE)) {
            return largeVehicleParkingRules(availableSpots);
        }
        return NormalDriverStrategy(availableSpots);
    }


    public int NormalDriverStrategy(ArrayList<Integer> availableSlot) throws ParkingLotException {
        if (availableSlot.size() != 0)
            return availableSlot.get(availableSlot.size() - 1);
        throw new ParkingLotException("Lot is full");
    }

    public int largeVehicleParkingRules(ArrayList<Integer> availableSlot) throws ParkingLotException {
        if (availableSlot.size() < 3) {
            throw new ParkingLotException("No space or large vehicle");
        }
        for (int i = 1; i < availableSlot.size(); i++) {
            if (availableSlot.get(i - 1) == availableSlot.get(i) - 1 && availableSlot.get(i + 1) == availableSlot.get(i) + 1) {
                return availableSlot.get(i);
            }
        }
        throw new ParkingLotException("No space or large vehicle");
    }

    public int handicapParkingRules(ArrayList<Integer> availableSlot) throws ParkingLotException {
        if (availableSlot.size() != 0)
            return availableSlot.get(0);
        throw new ParkingLotException("Lot is full");
    }
}