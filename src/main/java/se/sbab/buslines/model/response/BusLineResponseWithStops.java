package se.sbab.buslines.model.response;

import java.util.List;

public record BusLineResponseWithStops(
        String name,
        List<String> busStops
) {
}
