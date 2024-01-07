package se.sbab.buslines.model.response;

import java.util.List;

public record BusLinesResponse(
        List<BusLineResponse> busLines
) {
}
