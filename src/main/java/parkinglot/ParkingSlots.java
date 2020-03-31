package parkinglot;

import java.time.LocalDateTime;

public class ParkingSlots {
    private LocalDateTime time;
    Object vehicle;

    public ParkingSlots(Object vehicle) {
        this.vehicle = vehicle;
        this.time = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlots that = (ParkingSlots) o;
        return vehicle.equals(that.vehicle);
    }
}
