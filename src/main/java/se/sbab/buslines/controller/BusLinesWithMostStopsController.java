package se.sbab.buslines.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.sbab.buslines.model.internal.BusLine;
import se.sbab.buslines.model.response.BusLineResponse;
import se.sbab.buslines.model.response.BusLineResponseWithStops;
import se.sbab.buslines.model.response.BusLinesResponse;
import se.sbab.buslines.service.BusLinesService;

/**
 * Specific controller endpoint for fetching the single line with most stops.
 * Has GET requests for getting the bus line with most stops, including the name of all stops on the line.
 */
@RestController
@RequestMapping(path = "/bus-line-with-most-stops")
public class BusLinesWithMostStopsController {

    private final BusLinesService busLinesService;

    public BusLinesWithMostStopsController(BusLinesService busLinesService) {
        this.busLinesService = busLinesService;
    }

    @GetMapping
    public ResponseEntity<BusLineResponseWithStops> getLineWithMostBusStops() {
        BusLineResponseWithStops busLinesWithMostBusStops = busLinesService.getLineWithMostBusStops();
        return ResponseEntity.ok(busLinesWithMostBusStops);
    }
}
