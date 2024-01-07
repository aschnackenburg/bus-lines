package se.sbab.buslines.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.sbab.buslines.model.response.BusLineResponseWithStops;
import se.sbab.buslines.model.response.BusLinesResponse;
import se.sbab.buslines.service.BusLinesService;

/**
 * Primary controller for bus lines. Can fetch a subset of lines or a single line.
 * Has GET requests for getting a list of bus lines with a size parameter or a specific bus line including stops by name
 */
@RestController
@RequestMapping(path = "/bus-lines")
public class BusLinesController {

    private final BusLinesService busLinesService;

    public BusLinesController(BusLinesService busLinesService) {
        this.busLinesService = busLinesService;
    }

    @GetMapping
    public ResponseEntity<BusLinesResponse> getLinesWithMostBusStops(@RequestParam(defaultValue = "10") int size) {
        BusLinesResponse busLinesWithMostBusStops = busLinesService.getBusLinesWithMostBusStops(size);
        return ResponseEntity.ok(busLinesWithMostBusStops);
    }

    @GetMapping(path = "/{name}")
    public ResponseEntity<BusLineResponseWithStops> getLineWithStops(@PathVariable String name) {
        BusLineResponseWithStops busLinesWithMostBusStops = busLinesService.getLineWithBusStops(name);
        return ResponseEntity.ok(busLinesWithMostBusStops);
    }
}
