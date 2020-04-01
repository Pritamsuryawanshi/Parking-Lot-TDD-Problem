package parkinglot;

import java.time.LocalDateTime;

public class ParkingSlots {
    public LocalDateTime time;
    Object vehicle;
    DriverType type;

    public ParkingSlots(Object vehicle, DriverType type) {
        this.vehicle = vehicle;
        this.time = LocalDateTime.now();
        this.type=type;
    }

    public ParkingSlots(Object vehicle) {
        this.vehicle=vehicle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlots that = (ParkingSlots) o;
        return vehicle.equals(that.vehicle);
    }

    @Override
    public String toString() {
        return "ParkingSlots{" +
                "time=" + time +
                ", vehicle=" + vehicle +
                '}';
    }
}
