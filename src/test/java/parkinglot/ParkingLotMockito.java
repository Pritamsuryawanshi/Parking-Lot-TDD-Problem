package parkinglot;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

public class ParkingLotMockito {
    @Mock
    ParkingLotsSystem parkingLotsSystem;

    ParkingLot parkingLot;
    Object vehicle = null;
    //  ParkingLotsSystem parkingLotsSystem;

    @Before
    public void setUp() {
        vehicle = new Object();
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
}
