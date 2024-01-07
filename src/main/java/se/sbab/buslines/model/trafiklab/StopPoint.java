package se.sbab.buslines.model.trafiklab;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StopPoint(

        @JsonProperty("StopPointNumber")
        String stopPointNumber,

        @JsonProperty("StopPointName")
        String stopPointName
) {
}
