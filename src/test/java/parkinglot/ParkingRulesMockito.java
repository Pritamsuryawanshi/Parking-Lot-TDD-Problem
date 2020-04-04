package parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParkingRulesMockito {
    private static ArrayList<Integer> list;

    @Before
    public void beforeClass() throws Exception {
        list = new ArrayList<>(Arrays.asList(0, 4, 3, 2, 1));
    }

    @Test
    public void whenParkingSpotIsAskedForHandicappedDriver_ShouldReturnNearestSpot() {
        ParkingRules parkingRules = mock(ParkingRules.class);
        when(parkingRules.decideParkingSpot(VehicleType.HANDICAP, list)).thenReturn(0);
        int spot = parkingRules.decideParkingSpot(VehicleType.HANDICAP, list);
        Assert.assertEquals(0, spot);
    }

    @Test
    public void whenParkingSpotIsAskedForNormalDriver_ShouldReturnNearestSpot() {
        ParkingRules parkingRules = mock(ParkingRules.class);
        when(parkingRules.decideParkingSpot(VehicleType.NORMAL, list)).thenReturn(4);
        int spot = parkingRules.decideParkingSpot(VehicleType.NORMAL, list);
        Assert.assertEquals(4, spot);
    }

    @Test
    public void whenParkingSpotIsAskedForLargeVehicle_ShouldReturnSpot_WhichIsEmptyOnEitherSide() {
        ParkingRules parkingRules = mock(ParkingRules.class);
        when(parkingRules.decideParkingSpot(VehicleType.LARGE, list)).thenReturn(2);
        int spot = parkingRules.decideParkingSpot(VehicleType.LARGE, list);
        Assert.assertEquals(2, spot);
    }

    @Test
    public void whenParkingSpotIsAskedForHandicappedLargeVehicle_ShouldReturnNearestSpot() {
        ParkingRules parkingRules = mock(ParkingRules.class);
        when(parkingRules.decideParkingSpot(VehicleType.LARGE, list)).thenReturn(0);
        int spot = parkingRules.decideParkingSpot(VehicleType.LARGE, list);
        Assert.assertEquals(0, spot);
    }
}
