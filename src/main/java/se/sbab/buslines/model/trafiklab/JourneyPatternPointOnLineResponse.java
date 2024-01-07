package se.sbab.buslines.model.trafiklab;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JourneyPatternPointOnLineResponse(

    @JsonProperty("StatusCode")
    int statusCode,

    @JsonProperty("Message")
    String message,

    @JsonProperty("ExecutionTime")
    int executionTime,

    @JsonProperty("ResponseData")
    JourneyPatternPointOnLineResponseData responseData
) {
}
