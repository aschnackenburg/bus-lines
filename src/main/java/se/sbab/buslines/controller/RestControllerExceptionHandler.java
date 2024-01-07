package se.sbab.buslines.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import se.sbab.buslines.model.BusLineNotFoundException;
import se.sbab.buslines.model.response.ExceptionResponse;


@ControllerAdvice
public class RestControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(RestControllerExceptionHandler.class);

    @ExceptionHandler(value = { BusLineNotFoundException.class })
    protected ResponseEntity<ExceptionResponse> handleBusLineNotFound(BusLineNotFoundException exception) {
        logger.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(exception.getMessage()));
    }

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<ExceptionResponse> handleGeneralExceptions(Exception exception) {
        logger.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(
                "Something went wrong. Check logs for more info."
        ));
    }
}
