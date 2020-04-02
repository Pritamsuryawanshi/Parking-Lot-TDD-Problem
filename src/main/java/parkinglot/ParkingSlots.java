package parkinglot;

import java.time.LocalDateTime;

public class ParkingSlots {
    public LocalDateTime time;
    Object vehicle;
    VehicleType type;

    public ParkingSlots(Object vehicle, VehicleType type) {
        this.vehicle = vehicle;
        this.time = LocalDateTime.now();
        this.type=type;
    }

    public ParkingSlots(Object vehicle) {
        this.vehicle=vehicle;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ParkingSlots parkingSlots = (ParkingSlots) object;
        return vehicle.equals(parkingSlots.vehicle);
    }
}
