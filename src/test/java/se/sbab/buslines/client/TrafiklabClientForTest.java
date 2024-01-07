package se.sbab.buslines.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import se.sbab.buslines.model.trafiklab.JourneyPatternPointOnLine;
import se.sbab.buslines.model.trafiklab.StopPoint;
import se.sbab.buslines.model.trafiklab.JourneyPatternPointOnLineResponse;
import se.sbab.buslines.model.trafiklab.StopPointResponse;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TrafiklabClientForTest implements ITrafiklabClient {

    private final ObjectMapper mapper = new ObjectMapper();
    
    private List<StopPoint> stopPoints;
    private List<JourneyPatternPointOnLine> journeyPatternPointsOnLine;

    public TrafiklabClientForTest() {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public List<StopPoint> getAllBusStopPoints() {
        if (stopPoints == null) {
            loadStopPoints();
        }
        return stopPoints;
    }

    private void loadStopPoints() {
        try {
            StopPointResponse response = mapper.readValue(new File("src/test/resources/trafiklab/stoppoint.json"), StopPointResponse.class);
            stopPoints = response.responseData().result();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<JourneyPatternPointOnLine> getAllBusJourneyPatternPoints() {
        if (journeyPatternPointsOnLine == null) {
            loadJourneyPatternPointsOnLine();
        }
        return journeyPatternPointsOnLine;
    }

    private void loadJourneyPatternPointsOnLine() {
        try {
            JourneyPatternPointOnLineResponse response = mapper.readValue(new File("src/test/resources/trafiklab/journeyPatternPointOnLine.json"), JourneyPatternPointOnLineResponse.class);
            journeyPatternPointsOnLine = response.responseData().result();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
