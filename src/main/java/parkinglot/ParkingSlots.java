package parkinglot;

import org.apache.commons.lang3.time.StopWatch;

import java.time.Instant;
import java.time.LocalDateTime;

public class ParkingSlots {
    float time;
    Object vehicle;
    VehicleType type;
    String colour;
    String brand;
    String plateNumber;

    public ParkingSlots(Object vehicle, VehicleType type, String brand, String colour, String plateNumber) {
        this.plateNumber = plateNumber;
        this.brand = brand;
        this.colour = colour;
        this.time = System.currentTimeMillis() / 10000;
        this.vehicle = vehicle;
        this.type = type;
    }

    public ParkingSlots(Object vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ParkingSlots parkingSlots = (ParkingSlots) object;
        return vehicle.equals(parkingSlots.vehicle);
    }
}
