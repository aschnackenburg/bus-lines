package se.sbab.buslines;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import se.sbab.buslines.client.ITrafiklabClient;
import se.sbab.buslines.client.TrafiklabClientForTest;
import se.sbab.buslines.service.BusLinesService;
import se.sbab.buslines.service.TrafiklabService;

@TestConfiguration
public class BusLinesTestConfig {

    private final ITrafiklabClient trafiklabClient = new TrafiklabClientForTest();
    private final TrafiklabService trafiklabService = new TrafiklabService(trafiklabClient);
    private final BusLinesService busLinesService = new BusLinesService(trafiklabService);

    @Bean
    public ITrafiklabClient trafiklabClient() {
        return trafiklabClient;
    }

    @Bean
    public TrafiklabService trafiklabService() {
        return trafiklabService;
    }

    @Bean
    public BusLinesService busLinesService() {
        return busLinesService;
    }
}
