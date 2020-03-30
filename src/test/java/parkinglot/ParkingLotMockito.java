package parkinglot;

import org.junit.Test;
import java.util.LinkedHashMap;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParkingLotMockito {

    @Test
    public void mockitoTesting() throws ParkingLotException {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        ParkingLotSystem parkingLotSystem = mock(ParkingLotSystem.class);
        when(linkedHashMap.containsKey(anyObject())).thenReturn(false);
        parkingLotSystem.findMyCar(new Object());
    }
}
