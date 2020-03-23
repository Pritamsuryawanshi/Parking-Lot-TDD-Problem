package parkinglot;

public class ParkingAttendant {
    public void parkTheCar(Object vehicle) {
        try {
            new ParkingLotSystem(1).park(vehicle);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }
}
