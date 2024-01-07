package se.sbab.buslines.client;

import se.sbab.buslines.model.trafiklab.JourneyPatternPointOnLine;
import se.sbab.buslines.model.trafiklab.StopPoint;

import java.util.List;

public interface ITrafiklabClient {
    List<StopPoint> getAllBusStopPoints();
    List<JourneyPatternPointOnLine> getAllBusJourneyPatternPoints();
}
