package se.sbab.buslines.model.internal;

import java.util.Set;

public record BusLine(
        String name,
        Set<String> busStops
) {
    public int getNumberOfStops() {
        return busStops.size();
    }
}
