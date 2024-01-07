package se.sbab.buslines.model.trafiklab;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record JourneyPatternPointOnLineResponseData(

        @JsonProperty("Version")
        String version,

        @JsonProperty("Type")
        String type,

        @JsonProperty("Result")
        List<JourneyPatternPointOnLine> result
) {
}
