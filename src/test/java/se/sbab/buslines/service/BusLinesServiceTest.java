package se.sbab.buslines.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import se.sbab.buslines.model.internal.BusLine;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BusLinesServiceTest {

    private final TrafiklabService trafiklabService = mock(TrafiklabService.class);
    private final BusLinesService busLinesService = new BusLinesService(trafiklabService);

    @Test
    public void shouldOnlyCallTrafiklabServiceOnce() {
        when(trafiklabService.getAllBusLines()).thenReturn(List.of(
                new BusLine("1", Set.of("Stop1")),
                new BusLine("2", Set.of("Stop1")),
                new BusLine("3", Set.of("Stop1")),
                new BusLine("4", Set.of("Stop1")),
                new BusLine("6", Set.of("Stop1")),
                new BusLine("201", Set.of("Stop1", "Stop2")),
                new BusLine("204", Set.of("Stop1")),
                new BusLine("206", Set.of("Stop1"))
        ));

        busLinesService.getBusLinesWithMostBusStops(10);
        busLinesService.getBusLinesWithMostBusStops(5);
        busLinesService.getLineWithBusStops("201");

        verify(trafiklabService, times(1)).getAllBusLines();
    }
}