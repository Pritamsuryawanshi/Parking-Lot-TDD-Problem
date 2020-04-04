package parkinglot;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class ParkingLotMockito {
    @Mock
    ParkingLotsSystem parkingLotsSystem;

    ParkingLot parkingLot;
    Vehicle vehicle = null;

    @Before
    public void setUp() {
        vehicle = new Vehicle();
        parkingLot = mock(ParkingLot.class);
        parkingLotsSystem = mock(ParkingLotsSystem.class);
        parkingLotsSystem.addLot(parkingLot);
    }

    @Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() {
        when(parkingLot.isVehicleParked(vehicle)).thenReturn(true);
        boolean isParked = parkingLotsSystem.isVehicleParked(vehicle);
        assertFalse(isParked);
    }

    @Test
    public void givenANormalVehicle_WhenReadyToParkParked_ShouldReturnTheParkingSpot() throws ParkingLotException {
        ParkingRules parkingRules = mock(ParkingRules.class);
        ArrayList<Integer> availableSpot = new ArrayList<>(Arrays.asList(4, 3, 2, 1, 0));
        when(parkingRules.decideParkingSpot(VehicleType.NORMAL, availableSpot)).thenReturn(1);
        parkingLot.parkingAttendant(vehicle, VehicleType.NORMAL, "B");
        int myCar = parkingLot.findMyCar(vehicle);
    }

}
