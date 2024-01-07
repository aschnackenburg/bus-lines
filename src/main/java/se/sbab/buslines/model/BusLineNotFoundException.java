package se.sbab.buslines.model;

public class BusLineNotFoundException extends RuntimeException {
    public BusLineNotFoundException(String message) {
        super(message);
    }
}
