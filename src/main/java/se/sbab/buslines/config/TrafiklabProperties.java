package se.sbab.buslines.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "trafiklab")
public record TrafiklabProperties(
        String apiKey,
        String url
) {
}
