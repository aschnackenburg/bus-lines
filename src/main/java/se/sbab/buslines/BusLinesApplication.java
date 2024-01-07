package se.sbab.buslines;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class BusLinesApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusLinesApplication.class, args);
    }

}
