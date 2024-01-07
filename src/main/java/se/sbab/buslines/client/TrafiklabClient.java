package se.sbab.buslines.client;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import se.sbab.buslines.config.TrafiklabProperties;
import se.sbab.buslines.model.trafiklab.JourneyPatternPointOnLine;
import se.sbab.buslines.model.trafiklab.JourneyPatternPointOnLineResponse;
import se.sbab.buslines.model.trafiklab.StopPoint;
import se.sbab.buslines.model.trafiklab.StopPointResponse;

import java.util.List;

@Service
public class TrafiklabClient implements ITrafiklabClient{

    private final TrafiklabProperties properties;

    public TrafiklabClient(TrafiklabProperties properties) {
        this.properties = properties;
    }

    @Override
    public List<StopPoint> getAllBusStopPoints() {
        String apiKey = properties.apiKey();
        String url = properties.url() + "?model=stop&key=" + apiKey + "&DefaultTransportModeCode=BUS";

        RestClient restClient = RestClient.create();
        StopPointResponse response = restClient.get()
                .uri(url)
                .retrieve()
                .body(StopPointResponse.class);

        return response.responseData().result();
    }

    @Override
    public List<JourneyPatternPointOnLine> getAllBusJourneyPatternPoints() {
        String apiKey = properties.apiKey();
        String url = properties.url() + "?model=JourneyPatternPointOnLine&key=" + apiKey + "&DefaultTransportModeCode=BUS";

        RestClient restClient = RestClient.create();
        JourneyPatternPointOnLineResponse response = restClient.get()
                .uri(url)
                .retrieve()
                .body(JourneyPatternPointOnLineResponse.class);

        return response.responseData().result();
    }
}
