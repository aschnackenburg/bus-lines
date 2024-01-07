package se.sbab.buslines.model.trafiklab;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JourneyPatternPointOnLine(

        @JsonProperty("LineNumber")
        String lineNumber,

        @JsonProperty("DirectionCode")
        String directionCode,

        @JsonProperty("JourneyPatternPointNumber")
        String journeyPatternPointNumber
) {
}
