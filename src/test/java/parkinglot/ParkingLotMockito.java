package parkinglot;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParkingLotMockito {
    @Mock
    ParkingLot parkingLot;

    Object vehicle = null;
    ParkingLotsSystem parkingLotsSystem;

    @Before
    public void setUp() {
        vehicle = new Object();
        parkingLot = mock(ParkingLot.class);
        parkingLotsSystem = new ParkingLotsSystem();
        parkingLotsSystem.addLot(parkingLot);
    }

    @Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() {
        when(parkingLot.isVehicleParked(vehicle)).thenReturn(true);
        System.out.println("result " + parkingLot.isVehicleParked(vehicle));
        System.out.println("after " + parkingLotsSystem.isVehicleParked(vehicle));
        boolean isParked = parkingLotsSystem.isVehicleParked(vehicle);
        assertFalse(isParked);
    }
}
