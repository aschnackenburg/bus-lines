package se.sbab.buslines.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.sbab.buslines.controller.RestControllerExceptionHandler;
import se.sbab.buslines.model.BusLineNotFoundException;
import se.sbab.buslines.model.internal.BusLine;
import se.sbab.buslines.model.response.BusLineResponse;
import se.sbab.buslines.model.response.BusLineResponseWithStops;
import se.sbab.buslines.model.response.BusLinesResponse;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class BusLinesService {

    private final TrafiklabService trafiklabService;

    // Storing bus lines so each request doesn't call the api.
    private List<BusLine> busLinesInMemoryStorage = null;

    // Date used to re-fetch bus lines once per day
    private LocalDate lastUpdated = null;

    private final Logger logger = LoggerFactory.getLogger(RestControllerExceptionHandler.class);


    public BusLinesService(TrafiklabService trafiklabService) {
        this.trafiklabService = trafiklabService;
    }


    public BusLinesResponse getBusLinesWithMostBusStops(int size) {
        List<BusLine> busLines = getBusLines();
        return new BusLinesResponse(busLines.stream()
                .sorted(Comparator.comparing(BusLine::getNumberOfStops).reversed())
                .limit(size)
                .map(bl -> new BusLineResponse(bl.name(), bl.getNumberOfStops()))
                .collect(toList()));
    }

    public BusLineResponseWithStops getLineWithMostBusStops() {
        List<BusLine> busLines = getBusLines();
        return busLines.stream()
                .max(Comparator.comparing(BusLine::getNumberOfStops))
                .map(bl -> new BusLineResponseWithStops(bl.name(), bl.busStops().stream().toList()))
                .orElseThrow(() -> new BusLineNotFoundException("Bus line with most stops not found"));
    }

    public BusLineResponseWithStops getLineWithBusStops(String lineName) {
        Optional<BusLine> busLine = getBusLines().stream()
                .filter(bl -> bl.name().equals(lineName))
                .findFirst();

        if (busLine.isPresent()) {
            return new BusLineResponseWithStops(
                    busLine.get().name(),
                    busLine.get().busStops().stream()
                            .sorted(Comparator.reverseOrder()) //Alphabetical order
                            .toList()
            );
        }

        throw new BusLineNotFoundException("Bus line '" + lineName + "' not found");
    }

    /**
     * Fetches all bus lines. Only fetches data from the api once per day, otherwise the data is stored in memory.
     * @return a list of all bus lines
     */
    private List<BusLine> getBusLines() {
        if (busLinesInMemoryStorage == null || lastUpdated.isBefore(LocalDate.now())) {
            logger.info("Fetching all bus lines from Trafiklab");
            busLinesInMemoryStorage = trafiklabService.getAllBusLines();
            lastUpdated = LocalDate.now();
        }
        return busLinesInMemoryStorage;
    }
}
