package parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotTest {
    Object vehicle = null;
    ParkingLotSystem parkingLotSystem = null;
    Informer informer = null;

    @Before
    public void setUp() throws Exception {
        vehicle = new Object();
        parkingLotSystem = new ParkingLotSystem(2);
        informer = new Informer();
    }

    @Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() {
        Object vehicle = new Object();
        try {
            parkingLotSystem.parkingAttendant(this.vehicle, DriverType.NORMAL);
            parkingLotSystem.parkingAttendant(vehicle, DriverType.HANDICAP);
            boolean isParked = parkingLotSystem.isVehicleParked(this.vehicle);
            Assert.assertTrue(isParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicle_WhenParked_ShouldReturnrue() {
        Object vehicle = new Object();
        try {
            parkingLotSystem.parkingAttendant(this.vehicle, DriverType.NORMAL);
            parkingLotSystem.parkingAttendant(vehicle, DriverType.HANDICAP);
            parkingLotSystem.parkingAttendant(new Object(), DriverType.HANDICAP);
            boolean isParked = parkingLotSystem.isVehicleParked(this.vehicle);
            Assert.assertTrue(isParked);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Lot is full", e.getMessage());
        }
    }

    @Test
    public void givenAVehicle_WhenSpaceIsFull_ShouldThrowException() {
        try {
            parkingLotSystem.parkingAttendant(vehicle, DriverType.NORMAL);
            parkingLotSystem.parkingAttendant(new Object(), DriverType.NORMAL);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Parking Lot is full", e.getMessage());
        }
    }

    @Test
    public void givenAVehicle_WhenAlreadyParked_ShouldThrowException() {
        try {
            parkingLotSystem.parkingAttendant(vehicle, DriverType.NORMAL);
            parkingLotSystem.parkingAttendant(vehicle, DriverType.NORMAL);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Vehicle already parked", e.getMessage());
        }
    }

    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnTrue() {
        try {
            parkingLotSystem.parkingAttendant(vehicle, DriverType.NORMAL);
            boolean unPark = parkingLotSystem.unPark(vehicle);
            Assert.assertTrue(unPark);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLotVacancy_WhenFull_ShouldInformTheOwner() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2);
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLotSystem.registerParkingLotObserver(owner);
        try {
            parkingLotSystem.parkingAttendant(vehicle, DriverType.NORMAL);
            parkingLotSystem.parkingAttendant(new Object(), DriverType.NORMAL);
        } catch (ParkingLotException e) {
        }
        boolean capacityFull = owner.isCapacityFull();
        Assert.assertTrue(capacityFull);
    }

    @Test
    public void givenParkingLotVacancy_WhenNotFull_ShouldInformTheOwner() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2);
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLotSystem.registerParkingLotObserver(owner);
        try {
            parkingLotSystem.parkingAttendant(vehicle, DriverType.NORMAL);
            parkingLotSystem.unPark(vehicle);
            parkingLotSystem.parkingAttendant(new Object(), DriverType.NORMAL);
        } catch (ParkingLotException e) {
        }
        boolean capacityFull = owner.isCapacityFull();
        Assert.assertFalse(capacityFull);
    }

    @Test
    public void givenParkingLotVacancy_WhenNotFull_ShouldReturnFalse() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLotSystem.registerParkingLotObserver(owner);
        try {
            parkingLotSystem.parkingAttendant(vehicle, DriverType.NORMAL);
        } catch (ParkingLotException e) {
        }
        boolean capacityFull = owner.isCapacityFull();
        Assert.assertFalse(capacityFull);
    }

    @Test
    public void givenCapacityAs2_ShouldBeAbleToPark2Vehicles() {
        Object vehicle2 = new Object();
        parkingLotSystem.setCapacity(2);
        try {
            parkingLotSystem.parkingAttendant(vehicle, DriverType.NORMAL);
            parkingLotSystem.parkingAttendant(vehicle2, DriverType.NORMAL);
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
            parkingLotSystem.parkingAttendant(vehicle, DriverType.NORMAL);
            parkingLotSystem.parkingAttendant(new Object(), DriverType.NORMAL);
        } catch (ParkingLotException e) {
        }
        boolean capacityFull = airportSecurity.isCapacityFull();
        Assert.assertTrue(capacityFull);
    }

    @Test
    public void givenParkingLotSpace_WhenNotFull_ShouldReturnFalse() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLotSystem.registerParkingLotObserver(airportSecurity);
        try {
            parkingLotSystem.parkingAttendant(vehicle, DriverType.NORMAL);
        } catch (ParkingLotException e) {
        }
        boolean capacityFull = airportSecurity.isCapacityFull();
        Assert.assertFalse(capacityFull);
    }


    @Test
    public void givenParkingLotSpace_WhenAvailable_ShouldReturnTrue() {
        Object vehicle2 = new Object();
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLotSystem.registerParkingLotObserver(airportSecurity);
        try {
            parkingLotSystem.parkingAttendant(vehicle, DriverType.NORMAL);
            parkingLotSystem.unPark(vehicle);
            parkingLotSystem.parkingAttendant(vehicle2, DriverType.NORMAL);
        } catch (ParkingLotException e) {
        }
        boolean capacityFull = airportSecurity.isCapacityFull();
        Assert.assertFalse(capacityFull);
    }


    @Test
    public void givenCar_ParkingAttendantShouldParkTheCar() {
        try {
            parkingLotSystem.parkingAttendant(vehicle, DriverType.NORMAL);
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
            parkingLotSystem.parkingAttendant(vehicle, DriverType.NORMAL);
            parkingLotSystem.parkingAttendant(vehicle2, DriverType.NORMAL);
            int slotNumberZero = parkingLotSystem.findMyCar(vehicle);
            int slotNUmberOne = parkingLotSystem.findMyCar(vehicle2);
            Assert.assertEquals(1, slotNumberZero);
            Assert.assertEquals(0, slotNUmberOne);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenACar_WhenNotParked_ShouldThrowAnException() {
        try {
            parkingLotSystem.findMyCar(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Car is not Parked", e.getMessage());
        }
    }

    @Test
    public void givenCarNotParked_ShouldThrowAnException() {
        try {
            parkingLotSystem.unPark(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Vehicle is not parked", e.getMessage());
        }
    }

    @Test
    public void givenACar_WhenParked_ShouldBeParkedEvenly() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(3);
        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
        try {
            parkingLotSystem.parkingAttendant(vehicle, DriverType.NORMAL);
            parkingLotSystem.parkingAttendant(vehicle2, DriverType.NORMAL);
            parkingLotSystem.parkingAttendant(vehicle3, DriverType.NORMAL);
            int myCar = parkingLotSystem.findMyCar(vehicle);
            int myCar1 = parkingLotSystem.findMyCar(vehicle2);
            int myCar2 = parkingLotSystem.findMyCar(vehicle3);
            Assert.assertEquals(0, myCar);
            Assert.assertEquals(1, myCar1);
            Assert.assertEquals(2, myCar2);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenAHandicappedPersonsCar_ShouldBeParkedToTheNearestSpace() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(4);
        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
        Object handicappedPersonsCar = new Object();
        Object handicappedPersonsCar2 = new Object();

        try {
            parkingLotSystem.parkingAttendant(vehicle, DriverType.NORMAL);
            parkingLotSystem.parkingAttendant(vehicle2, DriverType.NORMAL);
            parkingLotSystem.parkingAttendant(vehicle3, DriverType.NORMAL);
            parkingLotSystem.parkingAttendant(handicappedPersonsCar, DriverType.HANDICAP);
            parkingLotSystem.parkingAttendant(handicappedPersonsCar2, DriverType.HANDICAP);
            int myCar = parkingLotSystem.findMyCar(handicappedPersonsCar);
            int myCar2 = parkingLotSystem.findMyCar(handicappedPersonsCar2);
            Assert.assertEquals(0, myCar);
            Assert.assertEquals(2, myCar2);
        } catch (ParkingLotException e) {
        }
    }
}
