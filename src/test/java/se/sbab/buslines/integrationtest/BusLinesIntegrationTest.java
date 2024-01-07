package se.sbab.buslines.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import se.sbab.buslines.BusLinesTestConfig;
import se.sbab.buslines.model.response.BusLineResponseWithStops;
import se.sbab.buslines.model.response.BusLinesResponse;
import se.sbab.buslines.model.response.ExceptionResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(BusLinesTestConfig.class)
class BusLinesIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class BusLineWithMostStopsController {
        @Test
        public void shouldReturnBusLineWithMostStops() throws Exception {
            String responseAsString = mockMvc.perform(get("/bus-line-with-most-stops"))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            BusLineResponseWithStops response = objectMapper.readValue(responseAsString, BusLineResponseWithStops.class);
            assertEquals("636", response.name());
            assertEquals(105, response.busStops().size());
        }
    }

    @Nested
    class BusLinesController {
        @Test
        public void shouldReturn10BusLinesByDefault() throws Exception {
            String responseAsString = mockMvc.perform(get("/bus-lines"))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            BusLinesResponse response = objectMapper.readValue(responseAsString, BusLinesResponse.class);

            assertEquals(10, response.busLines().size());
        }

        @Test
        public void shouldReturnBusLineWithMostStopsIfSizeIsOne() throws Exception {
            String responseAsString = mockMvc.perform(get("/bus-lines?size=1"))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            BusLinesResponse response = objectMapper.readValue(responseAsString, BusLinesResponse.class);

            assertEquals(1, response.busLines().size());
            assertEquals("636", response.busLines().get(0).name());
        }

        @Test
        public void shouldReturnBusLineWithStops() throws Exception {
            String responseAsString = mockMvc.perform(get("/bus-lines/201"))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            BusLineResponseWithStops response = objectMapper.readValue(responseAsString, BusLineResponseWithStops.class);

            assertEquals("201", response.name());
            assertEquals(17, response.busStops().size());
            assertTrue(response.busStops().contains("Ropsten"));
            assertTrue(response.busStops().contains("Kottla station"));
        }

        @Test
        public void shouldReturn404WhenBusLineIsNotFound() throws Exception {
            String responseAsString = mockMvc.perform(get("/bus-lines/non-existing-bus-line"))
                    .andExpect(status().isNotFound())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            ExceptionResponse response = objectMapper.readValue(responseAsString, ExceptionResponse.class);

            assertEquals("Bus line 'non-existing-bus-line' not found", response.reason());
        }
    }
}