package parkinglot;

import java.util.ArrayList;

public class ParkingRules {
    public int decideParkingSpot(DriverType type, ArrayList<Integer> availableSpots) {
        if (type.equals(DriverType.HANDICAP))
            return handicapParkingRules(availableSpots);
        if (type.equals(DriverType.LARGE_VEHICLE) && availableSpots.size() > 2) {
            return largeVehicleParkingRules(availableSpots);
        }
        return NormalParkingRules(availableSpots);
    }

    public int NormalParkingRules(ArrayList<Integer> availableSlot) {
        return availableSlot.get(availableSlot.size() - 1);
    }

    public int largeVehicleParkingRules(ArrayList<Integer> availableSlot) {
        for (int i = 1; i < availableSlot.size() - 1; i++) {
            if (availableSlot.get(i - 1) == availableSlot.get(i) - 1 && availableSlot.get(i + 1) == availableSlot.get(i) + 1) {
                return availableSlot.get(i);
            }
        }
        return availableSlot.get(0);
    }

    public int handicapParkingRules(ArrayList<Integer> availableSlot) {
        return availableSlot.get(0);
    }
}