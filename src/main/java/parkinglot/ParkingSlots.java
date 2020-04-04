package parkinglot;

public class ParkingSlots {
    float time;
    Vehicle vehicle;
    VehicleType type;
    String colour;
    String brand;
    String plateNumber;
    String row;

    public ParkingSlots(Vehicle vehicle, VehicleType type, String row) {
        this.row=row;
        this.time = System.currentTimeMillis() / 100;
        this.vehicle = vehicle;
        this.type = type;
    }

    public ParkingSlots(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ParkingSlots parkingSlots = (ParkingSlots) object;
        return vehicle.equals(parkingSlots.vehicle);
    }
}
