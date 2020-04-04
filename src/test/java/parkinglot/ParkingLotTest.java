package parkinglot;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ParkingLotTest {
    Vehicle vehicle = null;
    ParkingLot parkingLot = null;
    Informer informer = null;
    ParkingLotsSystem parkingLotsSystem;
    ParkingLotOwner owner;
    AirportSecurity airPortSecurity;

    @Before
    public void setUp() {
        vehicle = new Vehicle("TOYOTA", "BLUE", "MH 16 244");
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
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "B");
            boolean isParked = parkingLot.isVehicleParked(vehicle);
            assertTrue(isParked);
        } catch (ParkingLotException e) {
            assertEquals("No spaces for large vehicle", e.getMessage());
        }
    }

    @Test
    public void givenAVehicle_WhenParked_ShouldReturnrue() {
        Vehicle vehicle2 = new Vehicle("TOYOTA", "WHITE", "MH 16 244");
        Vehicle vehicle3 = new Vehicle("TOYOTA", "WHITE", "MH 16 244");
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle2, VehicleType.HANDICAP, "B");
            parkingLot.parkingAttendant(vehicle3, VehicleType.HANDICAP, "B");
            boolean isParked = parkingLot.isVehicleParked(this.vehicle);
            assertTrue(isParked);
        } catch (ParkingLotException e) {
            assertEquals("Lot is full", e.getMessage());
        }
    }

    @Test
    public void givenAVehicle_WhenSpaceIsFull_ShouldThrowException() {
        Vehicle vehicle2 = new Vehicle();

        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle2, VehicleType.NORMAL, "B");
        } catch (ParkingLotException e) {
            assertEquals("Parking Lot is full", e.getMessage());
        }
    }

    @Test
    public void givenAVehicle_WhenAlreadyParked_ShouldThrowException() {
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "B");
        } catch (ParkingLotException e) {
            assertEquals("Vehicle already parked", e.getMessage());
        }
    }

    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnTrue() {
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "B");
            boolean unPark = parkingLot.unPark(vehicle);
            assertTrue(unPark);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLotVacancy_WhenFull_ShouldInformTheOwner() {
        ParkingLot parkingLot = new ParkingLot(2);
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLot.registerParkingLotObserver(owner);
        Vehicle vehicle2 = new Vehicle("TOYOTA", "WHITE", "MH 16 244");
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle2, VehicleType.NORMAL, "B");
        } catch (ParkingLotException e) {
        }
        boolean capacityFull = owner.isCapacityFull();
        assertTrue(capacityFull);
    }

    @Test
    public void givenParkingLotVacancy_WhenNotFull_ShouldInformTheOwner() {
        ParkingLot parkingLot = new ParkingLot(2);
        Vehicle vehicle2 = new Vehicle("TOYOTA", "WHITE", "MH 16 244");

        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLot.registerParkingLotObserver(owner);
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "B");
            parkingLot.unPark(vehicle);
            parkingLot.parkingAttendant(vehicle2, VehicleType.NORMAL, "B");
        } catch (ParkingLotException e) {
        }
        boolean capacityFull = owner.isCapacityFull();
        assertFalse(capacityFull);
    }

    @Test
    public void givenParkingLotVacancy_WhenNotFull_ShouldReturnFalse() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLot.registerParkingLotObserver(owner);
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "B");
        } catch (ParkingLotException e) {
        }
        boolean capacityFull = owner.isCapacityFull();
        assertFalse(capacityFull);
    }

    @Test
    public void givenCapacityAs2_ShouldBeAbleToPark2Vehicles() {
        Vehicle vehicle2 = new Vehicle("TOYOTA", "WHITE", "MH 16 244");
        parkingLot.setCapacity(2);
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle2, VehicleType.NORMAL, "B");
            boolean isParked1 = parkingLot.isVehicleParked(vehicle);
            boolean isParked2 = parkingLot.isVehicleParked(vehicle2);
            assertTrue(isParked1 && isParked2);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLotSpace_WhenFull_ShouldInformTheSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        Vehicle vehicle2 = new Vehicle("TOYOTA", "WHITE", "MH 16 244");
        parkingLot.registerParkingLotObserver(airportSecurity);
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle2, VehicleType.NORMAL, "B");
        } catch (ParkingLotException e) {
        }
        boolean capacityFull = airportSecurity.isCapacityFull();
        assertTrue(capacityFull);
    }

    @Test
    public void givenParkingLotSpace_WhenNotFull_ShouldReturnFalse() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLot.registerParkingLotObserver(airportSecurity);
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "B");
        } catch (ParkingLotException e) {
        }
        boolean capacityFull = airportSecurity.isCapacityFull();
        assertFalse(capacityFull);
    }


    @Test
    public void givenParkingLotSpace_WhenAvailable_ShouldReturnTrue() {
        Vehicle vehicle2 = new Vehicle("TOYOTA", "WHITE", "MH 16 244");
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLot.registerParkingLotObserver(airportSecurity);
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "B");
            parkingLot.unPark(vehicle);
            parkingLot.parkingAttendant(vehicle2, VehicleType.NORMAL, "B");
        } catch (ParkingLotException e) {
        }
        boolean capacityFull = airportSecurity.isCapacityFull();
        assertFalse(capacityFull);
    }


    @Test
    public void givenCar_ParkingAttendantShouldParkTheCar() {
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "B");
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
        boolean isParked = parkingLot.isVehicleParked(vehicle);
        assertTrue(isParked);
    }

    @Test
    public void givenCarParked_ShouldFindTheCar() {
        ParkingLot parkingLot = new ParkingLot(2);
        Vehicle vehicle2 = new Vehicle("TOYOTA", "WHITE", "MH 16 244");
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle2, VehicleType.NORMAL, "B");
            int slotNumberZero = parkingLot.findMyCar(vehicle);
            int slotNUmberOne = parkingLot.findMyCar(vehicle2);
            assertEquals(1, slotNumberZero);
            assertEquals(0, slotNUmberOne);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenACar_WhenNotParked_ShouldThrowAnException() {
        try {
            parkingLot.findMyCar(vehicle);
        } catch (ParkingLotException e) {
            assertEquals("Car is not Parked", e.getMessage());
        }
    }

    @Test
    public void givenCarNotParked_ShouldThrowAnException() {
        try {
            parkingLot.unPark(vehicle);
        } catch (ParkingLotException e) {
            assertEquals("Vehicle is not parked", e.getMessage());
        }
    }

    @Test
    public void givenCarParked_WhenTimeIsSet_ShouldReturnTrue() {
        Vehicle vehicle2 = new Vehicle("BMW", "GREEN", "MH 16 244");
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle2, VehicleType.NORMAL, "B");
            boolean timeSet = parkingLot.isTimeSet();
            assertTrue(timeSet);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenCarNotParked_AndTimeIsNotSte_ShouldReturnFalse() {
        boolean timeSet = parkingLot.isTimeSet();
        assertFalse(timeSet);
    }

    @Test
    public void givenACar_WhenParked_ShouldBeParkedEvenly() {
        ParkingLot parkingLot = new ParkingLot(3);
        Vehicle vehicle2 = new Vehicle("BMW", "GREEN", "MH 16 244");
        Vehicle vehicle3 = new Vehicle("TOYOTA", "WHITE", "MH 16 244");
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle2, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle3, VehicleType.NORMAL, "B");
            int myCar = parkingLot.findMyCar(vehicle);
            int myCar1 = parkingLot.findMyCar(vehicle2);
            int myCar2 = parkingLot.findMyCar(vehicle3);
            assertEquals(2, myCar);
            assertEquals(1, myCar1);
            assertEquals(0, myCar2);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenAHandicappedPersonsCar_ShouldBeParkedToTheNearestSpace() {
        ParkingLot parkingLot = new ParkingLot(5);
        Vehicle vehicle = new Vehicle("TOYOTA", "GREEN", "MH 16 244");
        Vehicle handicappedPersonsCar = new Vehicle("BMW", "GREEN", "MH 16 244");
        Vehicle vehicle2 = new Vehicle("BMW", "GREEN", "MH 16 244");
        Vehicle vehicle3 = new Vehicle("TOYOTA", "WHITE", "MH 16 244");
        Vehicle handicappedPersonsCar2 = new Vehicle("BMW", "GREEN", "MH 16 244");
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle2, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle3, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(handicappedPersonsCar, VehicleType.HANDICAP, "B");
            parkingLot.parkingAttendant(handicappedPersonsCar2, VehicleType.HANDICAP, "B");
            int myCar = parkingLot.findMyCar(handicappedPersonsCar);
            int myCar2 = parkingLot.findMyCar(handicappedPersonsCar2);
            assertEquals(0, myCar);
            assertEquals(1, myCar2);
        } catch (ParkingLotException e) {
            assertEquals("Lot is full", e.getMessage());
        }
    }
    //multiple lots

    @Test
    public void givenParkingLot_WhenLotIsFull_ShouldInfromAirPortSecurityStaff() {
        parkingLotsSystem.registerParkingLots(airPortSecurity);
        Vehicle vehicle2 = new Vehicle();
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle2, VehicleType.NORMAL, "B");
            boolean isCapacityFull = airPortSecurity.isCapacityFull();
            assertTrue(isCapacityFull);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenVehicle_WhenParkedUsingParkingSystem_ShouldReturnTrue() throws ParkingLotException {
        parkingLotsSystem.park(vehicle, VehicleType.NORMAL);
        boolean vehicleParked = parkingLot.isVehicleParked(vehicle);
        assertTrue(vehicleParked);
    }

    @Test
    public void givenALargeVehicle_WhenParked_ShouldReturnTrue() {
        Vehicle vehicleLarge = new Vehicle();
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicleLarge, VehicleType.LARGE, "B");
            boolean isParked = parkingLot.isVehicleParked(vehicleLarge);
            assertTrue(isParked);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenLargeVehicles_WhenParked_ShouldReturnTrue() {
        ParkingLot parkingLot = new ParkingLot(7);
        Vehicle vehicle = new Vehicle("TOYOTA", "GREEN", "MH 16 244");
        Vehicle vehicleLarge = new Vehicle("BMW", "GREEN", "MH 16 244");
        Vehicle vehicle2 = new Vehicle("BMW", "GREEN", "MH 16 244");
        Vehicle vehicle3 = new Vehicle("TOYOTA", "WHITE", "MH 16 244");
        Vehicle vehicle4 = new Vehicle("BMW", "GREEN", "MH 16 244");
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle2, VehicleType.LARGE, "B");
            parkingLot.parkingAttendant(vehicle3, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle4, VehicleType.LARGE, "B");
            parkingLot.parkingAttendant(vehicleLarge, VehicleType.LARGE, "B");
            boolean isParked = parkingLot.isVehicleParked(vehicle3);
            assertTrue(isParked);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenAVehicleColour_WhenParked_ShouldReturnTheSlotNumber() {
        Vehicle vehicle2 = new Vehicle("TOYOTA", "WHITE", "MH 16 244");
        ParkingLot parkingLot = new ParkingLot(5);
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle2, VehicleType.NORMAL, "B");
            ArrayList<Integer> list = parkingLot.getVehicleList();
            ArrayList<Integer> spot = parkingLot.findCarByColour(list, "WHITE");
            int location = spot.get(0);
            assertEquals(3, location);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenAVehicleBrandName_WhenParked_ShouldReturnTrue() {
        ParkingLot parkingLot = new ParkingLot(5);
        Vehicle vehicle2 = new Vehicle("TOYOTA", "BLUE", "MH 16 244");
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle2, VehicleType.NORMAL, "B");
            ArrayList<Integer> list = parkingLot.getVehicleList();
            ArrayList<Integer> blue = parkingLot.findCarByColour(list, "BLUE");
            ArrayList<Integer> toyota = parkingLot.findCarByBrand(blue, "TOYOTA");
            System.out.println("in test " + toyota);
            Vehicle object = parkingLot.getObject(toyota.get(0));
            System.out.println("in test " + object.row);
            assertEquals("TOYOTA", object.brand);
            assertEquals("BLUE", object.colour);
            assertNotEquals("WHITE3", object.colour);
            assertEquals("MH 16 244", object.plateNumber);
        } catch (ParkingLotException e) {
            assertEquals("No spaces for large vehicle", e.getMessage());
        }
    }


    @Test
    public void givenAVehicleColourAndBrand_WhenParked_ShouldReturnTheObject() {
        ParkingLot parkingLot = new ParkingLot(5);
        Vehicle vehicle = new Vehicle("TOYOTA", "BLUE", "MH 16 244");
        Vehicle vehicle2 = new Vehicle("TOYOTA", "GREEN", "MH 16 244");
        Vehicle vehicle4 = new Vehicle("BMW", "GREEN", "MH 16 244");
        Vehicle vehicle5 = new Vehicle("TOYOTA", "BLUE", "MH 16 244");
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle2, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle4, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle5, VehicleType.NORMAL, "B");
            ArrayList<Integer> list = parkingLot.getVehicleList();
            ArrayList<Integer> listOfBlue = parkingLot.findCarByColour(list, "BLUE");
            ArrayList<Integer> toyota = parkingLot.findCarByBrand(listOfBlue, "TOYOTA");
            ArrayList<Integer> vehicleList = new ArrayList<>(Arrays.asList(4, 1));
            assertEquals(vehicleList, toyota);
        } catch (ParkingLotException e) {
            assertEquals("No spaces for large vehicle", e.getMessage());
        }
    }

    @Test
    public void givenAVehicleBrand_WhenParked_ShouldReturnTheListOfCars() {
        ParkingLot parkingLot = new ParkingLot(5);
        Vehicle vehicle = new Vehicle("TOYOTA", "GREEN", "MH 16 244");
        Vehicle vehicle1 = new Vehicle("BMW", "GREEN", "MH 16 244");
        Vehicle vehicle2 = new Vehicle("BMW", "GREEN", "MH 16 244");
        Vehicle vehicle3 = new Vehicle("TOYOTA", "WHITE", "MH 16 244");
        Vehicle vehicle4 = new Vehicle("BMW", "GREEN", "MH 16 244");
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle1, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle2, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle3, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle4, VehicleType.NORMAL, "B");
            ArrayList<Integer> list = parkingLot.getVehicleList();
            ArrayList<Integer> carList = parkingLot.findCarByColour(list, "GREEN");
            ArrayList<Integer> potentialCarList = new ArrayList<>(Arrays.asList(4, 3, 2, 0));
            assertEquals(potentialCarList, carList);
            ArrayList<Integer> bmwList = parkingLot.findCarByBrand(carList, "BMW");
            ArrayList<Integer> potentialBMWCarList = new ArrayList<>(Arrays.asList(3, 2, 0));
            assertEquals(potentialBMWCarList, bmwList);
        } catch (ParkingLotException e) {
            assertEquals("No spaces for large vehicle", e.getMessage());
        }
    }

    @Test
    public void givenAVehicle_WhenParkedFor30Minutes_ShouldReturnVehicle() {
        ParkingLot parkingLot = new ParkingLot(5);
        Vehicle vehicle = new Vehicle("TOYOTA", "WHITE", "MH 16 244");
        Vehicle vehicle1 = new Vehicle("TOYOTA", "WHITE", "MH 16 244");
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(3, 4));
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle1, VehicleType.NORMAL, "B");
            ArrayList<Integer> parkedCars = parkingLot.calculateTime();
            assertEquals(list, parkedCars);
        } catch (ParkingLotException e) {
            assertEquals("", e.getMessage());
        }
    }

    @Test
    public void givenAHandicappedVehicle_WhenParkedInBOrDRow_ShouldReturnVehicle() {
        ParkingLot parkingLot = new ParkingLot(5);
        Vehicle vehicle = new Vehicle("TOYOTA", "WHITE", "MH 16 244");
        Vehicle vehicle1 = new Vehicle("TOYOTA", "WHITE", "MH 16 244");
        Vehicle vehicle2 = new Vehicle("TOYOTA", "WHITE", "MH 16 244");
        Vehicle vehicle3 = new Vehicle("TOYOTA", "WHITE", "MH 16 244");
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(0, 1));
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.HANDICAP, "B");
            parkingLot.parkingAttendant(vehicle1, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle2, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle3, VehicleType.HANDICAP, "B");
            ArrayList<Integer> vehiclesByRow = parkingLot.findVehiclesByRow();
            assertEquals(list, vehiclesByRow);
        } catch (ParkingLotException e) {
            assertEquals("", e.getMessage());
        }
    }

    @Test
    public void givenAParkingLot_ShouldReturnAllParkedVehicles() {
        ParkingLot parkingLot = new ParkingLot(5);
        Vehicle vehicle1 = new Vehicle("TOYOTA", "WHITE", "MH 16 244");
        Vehicle vehicle2 = new Vehicle("TOYOTA", "WHITE", "MH 16 244");
        Vehicle vehicle3 = new Vehicle("TOYOTA", "WHITE", "MH 16 244");
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(0, 4, 3, 1));
        try {
            parkingLot.parkingAttendant(vehicle, VehicleType.HANDICAP, "B");
            parkingLot.parkingAttendant(vehicle1, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle2, VehicleType.NORMAL, "B");
            parkingLot.parkingAttendant(vehicle3, VehicleType.HANDICAP, "B");
            ArrayList<Integer> parkedVehicles = parkingLot.getVehicleList();
            assertEquals(list, parkedVehicles);
        } catch (ParkingLotException e) {
            assertEquals("", e.getMessage());
        }
    }


}
