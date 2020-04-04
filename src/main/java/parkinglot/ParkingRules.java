package parkinglot;

import java.util.ArrayList;

public class ParkingRules {
    public int decideParkingSpot(VehicleType type, ArrayList<Integer> availableSlots) {
        if (type.equals(VehicleType.HANDICAP) || type.equals(VehicleType.HANDICAP_LARGE))
            return handicapParkingRules(availableSlots);
        if (type.equals(VehicleType.LARGE) && availableSlots.size() > 2) {
            return largeVehicleParkingRules(availableSlots);
        }
        return NormalParkingRules(availableSlots);
    }

    public int NormalParkingRules(ArrayList<Integer> availableSlots) {
        return availableSlots.get(availableSlots.size() - 1);
    }

    public int largeVehicleParkingRules(ArrayList<Integer> availableSlots) {
        for (int i = 1; i < availableSlots.size() - 1; i++) {
            if (availableSlots.get(i - 1) == availableSlots.get(i) - 1 &&
                    availableSlots.get(i + 1) == availableSlots.get(i) + 1) {
                return availableSlots.get(i);
            }
        }
        return availableSlots.get(0);
    }

    public int handicapParkingRules(ArrayList<Integer> availableSlot) {
        return availableSlot.get(0);
    }
}