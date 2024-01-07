package se.sbab.buslines.service;

import org.junit.jupiter.api.Test;
import se.sbab.buslines.client.TrafiklabClientForTest;
import se.sbab.buslines.model.internal.BusLine;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrafiklabServiceTest {

    private final TrafiklabClientForTest testClient = new TrafiklabClientForTest();
    private final TrafiklabService trafiklabService = new TrafiklabService(testClient);

    @Test
    public void shouldReturnAllBusLines() {
        List<BusLine> busLines = trafiklabService.getAllBusLines();
        assertEquals(473, busLines.size());
        BusLine busLineWithMostStops = busLines.get(0);
        assertEquals("636", busLineWithMostStops.name());
        assertEquals(105, busLineWithMostStops.busStops().size());
    }
}