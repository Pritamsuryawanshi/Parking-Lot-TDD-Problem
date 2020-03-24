package parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ParkingLotTest {
    Object vehicle = null;
    ParkingLotSystem parkingLotSystem = null;
    String time;

    @Before
    public void setUp() throws Exception {
        vehicle = new Object();
        parkingLotSystem = new ParkingLotSystem(1);
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        time = myDateObj.format(myFormatObj);
    }

    @Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() {
        try {
            parkingLotSystem.parkingAttendant(vehicle);
            boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
            Assert.assertTrue(isParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicle_WhenSpaceIsFull_ShouldThrowException() {
        try {
            parkingLotSystem.parkingAttendant(vehicle);
            parkingLotSystem.parkingAttendant(new Object());
        } catch (ParkingLotException e) {
            Assert.assertEquals("Parking Lot is full", e.getMessage());
        }
    }

    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnTrue() {
        try {
            parkingLotSystem.parkingAttendant(vehicle);
            boolean unPark = parkingLotSystem.unPark(vehicle);
            Assert.assertTrue(unPark);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLotVacancy_WhenFull_ShouldInformTheOwner() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLotSystem.registerParkingLotObserver(owner);
        try {
            parkingLotSystem.parkingAttendant(vehicle);
            parkingLotSystem.parkingAttendant(new Object());
        } catch (ParkingLotException e) {
        }
        boolean capacityFull = owner.isCapacityFull();
        Assert.assertTrue(capacityFull);
    }

    @Test
    public void givenCapacityAs2_ShouldBeAbleToPark2Vehicles() {
        Object vehicle2 = new Object();
        parkingLotSystem.setCapacity(2);
        try {
            parkingLotSystem.parkingAttendant(vehicle);
            parkingLotSystem.parkingAttendant(vehicle2);
            boolean isParked1 = parkingLotSystem.isVehicleParked(vehicle);
            boolean isParked2 = parkingLotSystem.isVehicleParked(vehicle2);
            Assert.assertTrue(isParked1 && isParked2);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLotSpace_WhenFull_ShouldInformTheSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLotSystem.registerParkingLotObserver(airportSecurity);
        try {
            parkingLotSystem.parkingAttendant(vehicle);
            parkingLotSystem.parkingAttendant(new Object());

        } catch (ParkingLotException e) {
        }
        boolean capacityFull = airportSecurity.isCapacityFull();
        Assert.assertTrue(capacityFull);
    }

    @Test
    public void givenParkingLotSpace_WhenAvailable_ShouldReturnTrue() {
        Object vehicle2 = new Object();
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLotSystem.registerParkingLotObserver(owner);
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(vehicle2);
        } catch (ParkingLotException e) {
        }
        parkingLotSystem.unPark(vehicle);
        boolean capacityFull = owner.isCapacityFull();
        Assert.assertFalse(capacityFull);
    }

    @Test
    public void givenCar_ParkingAttendantShouldParkTheCar() {
        try {
            parkingLotSystem.parkingAttendant(vehicle);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenCarParked_ShouldFindTheCar() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2);
        Object vehicle2 = new Object();
        try {
            parkingLotSystem.parkingAttendant(vehicle);
            parkingLotSystem.parkingAttendant(vehicle2);
            int slotNumberZero = parkingLotSystem.findMyCar(vehicle);
            int slotNUmberOne = parkingLotSystem.findMyCar(vehicle2);
            Assert.assertEquals(0, slotNumberZero);
            Assert.assertEquals(1, slotNUmberOne);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenCarNotParked_ShouldThrowAnExcption() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2);
        Object vehicle2 = new Object();
        try {
            parkingLotSystem.parkingAttendant(vehicle);
            parkingLotSystem.parkingAttendant(vehicle2);
            parkingLotSystem.findMyCar(vehicle);
            parkingLotSystem.findMyCar(vehicle2);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Car is not Parked", e.getMessage());
        }
    }

    @Test
    public void givenACar_WhenParked_ShouldReturnParkingTime() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2);
        Object vehicle2 = new Object();
        try {
            parkingLotSystem.parkingAttendant(vehicle2);
            parkingLotSystem.parkingAttendant(vehicle);
        } catch (ParkingLotException e) {
        }
        String parkingTimeOne = parkingLotSystem.getParkingTime(vehicle);
        String parkingTimeTwo = parkingLotSystem.getParkingTime(vehicle);
        Assert.assertEquals(time, parkingTimeOne);
        Assert.assertEquals(time, parkingTimeTwo);
    }
}
