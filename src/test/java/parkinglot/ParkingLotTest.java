package parkinglot;

import org.junit.Assert;
import org.junit.Test;

public class ParkingLotTest {
    @Test
    public void givenAVehicle_WhenParked_ShouldBeAbleToPark() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
        boolean parked = parkingLotSystem.park(new Object());
        Assert.assertTrue(parked);
    }
}
