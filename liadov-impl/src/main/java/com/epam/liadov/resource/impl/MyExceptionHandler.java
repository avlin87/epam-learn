package com.epam.liadov.resource.impl;

import com.epam.liadov.exception.SomeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * MyExceptionHandler - class for handling Exceptions
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e) {
        log.error("handleException() - HttpStatus.INTERNAL_SERVER_ERROR\n{}", e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleException(RuntimeException e) {
        log.error("handleException() - HttpStatus.NOT_FOUND:\n{}", e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(SomeException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "bad request")
    public String handleException(SomeException e) {
        log.error("handleException() - HttpStatus.BAD_REQUEST\n{}", e.getMessage());
        return e.getMessage();
    }

}