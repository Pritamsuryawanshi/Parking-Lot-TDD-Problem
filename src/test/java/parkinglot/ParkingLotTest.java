package parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotTest {
    Object vehicle = null;
    ParkingLot parkingLot = null;
    Informer informer = null;
    ParkingLotsSystem parkingLotsSystem;
    ParkingLotOwner owner;
    AirportSecurity airPortSecurity;

    @Before
    public void setUp() throws Exception {
        vehicle = new Object();
        parkingLot = new ParkingLot(2);
        informer = new Informer();
        parkingLotsSystem = new ParkingLotsSystem();
        parkingLotsSystem.addLot(parkingLot);
        owner = new ParkingLotOwner();
        airPortSecurity = new AirportSecurity();
    }


    @Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() {
        ParkingLot parkingLot = new ParkingLot(5);

        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "WHITE");
            boolean isParked = parkingLot.isVehicleParked(vehicle);
            Assert.assertTrue(isParked);
        } catch (ParkingLotException e) {
            Assert.assertEquals("No spaces for large vehicle", e.getMessage());
        }
    }

    @Test
    public void givenAVehicle_WhenParked_ShouldReturnrue() {
        Object vehicle = new Object();
        try {
            parkingLot.parkingAttendant(this.vehicle, VehicleType.NORMAL, "WHITE");
            parkingLot.parkingAttendant(vehicle, VehicleType.HANDICAP, "WHITE");
            parkingLot.parkingAttendant(new Object(), VehicleType.HANDICAP, "WHITE");
            boolean isParked = parkingLot.isVehicleParked(this.vehicle);
            Assert.assertTrue(isParked);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Lot is full", e.getMessage());
        }
    }

    @Test
    public void givenAVehicle_WhenSpaceIsFull_ShouldThrowException() {
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "WHITE");
            parkingLot.parkingAttendant(new Object(), VehicleType.NORMAL, "WHITE");
        } catch (ParkingLotException e) {
            Assert.assertEquals("Parking Lot is full", e.getMessage());
        }
    }

    @Test
    public void givenAVehicle_WhenAlreadyParked_ShouldThrowException() {
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "WHITE");
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "WHITE");
        } catch (ParkingLotException e) {
            Assert.assertEquals("Vehicle already parked", e.getMessage());
        }
    }

    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnTrue() {
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "WHITE");
            boolean unPark = parkingLot.unPark(vehicle);
            Assert.assertTrue(unPark);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLotVacancy_WhenFull_ShouldInformTheOwner() {
        ParkingLot parkingLot = new ParkingLot(2);
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLot.registerParkingLotObserver(owner);
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "WHITE");
            parkingLot.parkingAttendant(new Object(), VehicleType.NORMAL, "WHITE");
        } catch (ParkingLotException e) {
        }
        boolean capacityFull = owner.isCapacityFull();
        Assert.assertTrue(capacityFull);
    }

    @Test
    public void givenParkingLotVacancy_WhenNotFull_ShouldInformTheOwner() {
        ParkingLot parkingLot = new ParkingLot(2);
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLot.registerParkingLotObserver(owner);
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "WHITE");
            parkingLot.unPark(vehicle);
            parkingLot.parkingAttendant(new Object(), VehicleType.NORMAL, "WHITE");
        } catch (ParkingLotException e) {
        }
        boolean capacityFull = owner.isCapacityFull();
        Assert.assertFalse(capacityFull);
    }

    @Test
    public void givenParkingLotVacancy_WhenNotFull_ShouldReturnFalse() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLot.registerParkingLotObserver(owner);
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "WHITE");
        } catch (ParkingLotException e) {
        }
        boolean capacityFull = owner.isCapacityFull();
        Assert.assertFalse(capacityFull);
    }

    @Test
    public void givenCapacityAs2_ShouldBeAbleToPark2Vehicles() {
        Object vehicle2 = new Object();
        parkingLot.setCapacity(2);
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "WHITE");
            parkingLot.parkingAttendant(vehicle2, VehicleType.NORMAL, "WHITE");
            boolean isParked1 = parkingLot.isVehicleParked(vehicle);
            boolean isParked2 = parkingLot.isVehicleParked(vehicle2);
            Assert.assertTrue(isParked1 && isParked2);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLotSpace_WhenFull_ShouldInformTheSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLot.registerParkingLotObserver(airportSecurity);
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "WHITE");
            parkingLot.parkingAttendant(new Object(), VehicleType.NORMAL, "WHITE");
        } catch (ParkingLotException e) {
        }
        boolean capacityFull = airportSecurity.isCapacityFull();
        Assert.assertTrue(capacityFull);
    }

    @Test
    public void givenParkingLotSpace_WhenNotFull_ShouldReturnFalse() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLot.registerParkingLotObserver(airportSecurity);
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "WHITE");
        } catch (ParkingLotException e) {
        }
        boolean capacityFull = airportSecurity.isCapacityFull();
        Assert.assertFalse(capacityFull);
    }


    @Test
    public void givenParkingLotSpace_WhenAvailable_ShouldReturnTrue() {
        Object vehicle2 = new Object();
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLot.registerParkingLotObserver(airportSecurity);
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "WHITE");
            parkingLot.unPark(vehicle);
            parkingLot.parkingAttendant(vehicle2, VehicleType.NORMAL, "WHITE");
        } catch (ParkingLotException e) {
        }
        boolean capacityFull = airportSecurity.isCapacityFull();
        Assert.assertFalse(capacityFull);
    }


    @Test
    public void givenCar_ParkingAttendantShouldParkTheCar() {
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "WHITE");
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
        boolean isParked = parkingLot.isVehicleParked(vehicle);
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenCarParked_ShouldFindTheCar() {
        ParkingLot parkingLot = new ParkingLot(2);
        Object vehicle2 = new Object();
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "WHITE");
            parkingLot.parkingAttendant(vehicle2, VehicleType.NORMAL, "WHITE");
            int slotNumberZero = parkingLot.findMyCar(vehicle);
            int slotNUmberOne = parkingLot.findMyCar(vehicle2);
            Assert.assertEquals(1, slotNumberZero);
            Assert.assertEquals(0, slotNUmberOne);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenACar_WhenNotParked_ShouldThrowAnException() {
        try {
            parkingLot.findMyCar(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Car is not Parked", e.getMessage());
        }
    }

    @Test
    public void givenCarNotParked_ShouldThrowAnException() {
        try {
            parkingLot.unPark(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Vehicle is not parked", e.getMessage());
        }
    }

    @Test
    public void givenCarParked_WhenTimeIsSet_ShouldReturnTrue() {
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "WHITE");
            parkingLot.parkingAttendant(new Object(), VehicleType.NORMAL, "WHITE");
            boolean timeSet = parkingLot.isTimeSet();
            Assert.assertTrue(timeSet);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenCarNotParked_AndTimeIsNotSte_ShouldReturnFalse() {
        boolean timeSet = parkingLot.isTimeSet();
        Assert.assertFalse(timeSet);
    }

    @Test
    public void givenACar_WhenParked_ShouldBeParkedEvenly() {
        ParkingLot parkingLot = new ParkingLot(3);
        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "WHITE");
            parkingLot.parkingAttendant(vehicle2, VehicleType.NORMAL, "WHITE");
            parkingLot.parkingAttendant(vehicle3, VehicleType.NORMAL, "WHITE");
            int myCar = parkingLot.findMyCar(vehicle);
            int myCar1 = parkingLot.findMyCar(vehicle2);
            int myCar2 = parkingLot.findMyCar(vehicle3);
            Assert.assertEquals(2, myCar);
            Assert.assertEquals(1, myCar1);
            Assert.assertEquals(0, myCar2);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenAHandicappedPersonsCar_ShouldBeParkedToTheNearestSpace() {
        ParkingLot parkingLot = new ParkingLot(5);
        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
        Object handicappedPersonsCar = new Object();
        Object handicappedPersonsCar2 = new Object();
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "WHITE");
            parkingLot.parkingAttendant(vehicle2, VehicleType.NORMAL, "WHITE");
            parkingLot.parkingAttendant(vehicle3, VehicleType.NORMAL, "WHITE");
            parkingLot.parkingAttendant(handicappedPersonsCar, VehicleType.HANDICAP, "WHITE");
            parkingLot.parkingAttendant(handicappedPersonsCar2, VehicleType.HANDICAP, "WHITE");
            int myCar = parkingLot.findMyCar(handicappedPersonsCar);
            int myCar2 = parkingLot.findMyCar(handicappedPersonsCar2);
            Assert.assertEquals(0, myCar);
            Assert.assertEquals(1, myCar2);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Lot is full", e.getMessage());
        }
    }
    //multiple lots

    @Test
    public void givenParkingLot_WhenLotIsFull_ShouldInfromAirPortSecurityStaff() {
        parkingLotsSystem.registerParkingLots(airPortSecurity);
        Object vehicle2 = new Object();
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "WHITE");
            parkingLot.parkingAttendant(vehicle2, VehicleType.NORMAL, "WHITE");
            boolean isCapacityFull = airPortSecurity.isCapacityFull();
            Assert.assertTrue(isCapacityFull);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenVehicle_WhenParkedUsingParkingSystem_ShouldReturnTrue() throws ParkingLotException {
        parkingLotsSystem.park(vehicle, VehicleType.NORMAL);
        boolean vehicleParked = parkingLot.isVehicleParked(vehicle);
        Assert.assertTrue(vehicleParked);
    }

    @Test
    public void givenALargeVehicle_WhenParked_ShouldReturnTrue() {
        Object vehicleLarge = new Object();
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "WHITE");
            parkingLot.parkingAttendant(vehicleLarge, VehicleType.LARGE, "WHITE");
            boolean isParked = parkingLot.isVehicleParked(vehicleLarge);
            Assert.assertTrue(isParked);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenLargeVehicles_WhenParked_ShouldReturnTrue() {
        ParkingLot parkingLot = new ParkingLot(7);
        Object vehicle2 = new Object();
        Object vehicleLarge = new Object();
        Object vehicle3 = new Object();
        Object vehicle4 = new Object();
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "WHITE");
            parkingLot.parkingAttendant(vehicle2, VehicleType.LARGE, "WHITE");
            parkingLot.parkingAttendant(vehicle3, VehicleType.NORMAL, "WHITE");
            parkingLot.parkingAttendant(vehicle4, VehicleType.LARGE, "WHITE");
            parkingLot.parkingAttendant(vehicleLarge, VehicleType.LARGE, "WHITE");
            boolean isParked = parkingLot.isVehicleParked(vehicle3);
            Assert.assertTrue(isParked);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenAVehicleColour_WhenParked_ShouldReturnTheSlotNumber() {
        ParkingLot parkingLot = new ParkingLot(5);
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "WHITE");
            boolean isParked = parkingLot.isVehicleParked(vehicle);
            Assert.assertTrue(isParked);
            int spot = parkingLot.findCarByColour("WHITE");
            Assert.assertEquals(4, spot);
            System.out.println("spot is " + spot);
        } catch (ParkingLotException e) {
        }
    }
}
