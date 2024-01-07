package se.sbab.buslines.service;

import org.springframework.stereotype.Service;
import se.sbab.buslines.client.ITrafiklabClient;
import se.sbab.buslines.model.internal.BusLine;
import se.sbab.buslines.model.trafiklab.JourneyPatternPointOnLine;
import se.sbab.buslines.model.trafiklab.StopPoint;

import java.util.*;

import static java.util.stream.Collectors.*;

@Service
public class TrafiklabService {

    private final ITrafiklabClient trafiklabClient;

    public TrafiklabService(ITrafiklabClient trafiklabClient) {
        this.trafiklabClient = trafiklabClient;
    }

    /**
     * Fetches all bus lines and stops
     * @return a sorted list of all lines, with the line with the most bus stops first
     */
    public List<BusLine> getAllBusLines() {
        List<StopPoint> stopPoints = trafiklabClient.getAllBusStopPoints();
        List<JourneyPatternPointOnLine> journeyPatternPoints = trafiklabClient.getAllBusJourneyPatternPoints();

        Map<String, String> stopPointNameByStopPointNumber = stopPoints.stream()
                .collect(toMap(StopPoint::stopPointNumber, StopPoint::stopPointName));

        Map<String, Set<String>> lineWithStopPoints = journeyPatternPoints.stream()
                .collect(groupingBy(JourneyPatternPointOnLine::lineNumber,
                        mapping(jpp-> stopPointNameByStopPointNumber.get(jpp.journeyPatternPointNumber()), toSet())

                ));

        return lineWithStopPoints.entrySet().stream()
                .map(entry -> new BusLine(
                        entry.getKey(),
                        entry.getValue()
                )).sorted(Comparator.comparing(BusLine::getNumberOfStops).reversed())
                .toList();
    }
}
