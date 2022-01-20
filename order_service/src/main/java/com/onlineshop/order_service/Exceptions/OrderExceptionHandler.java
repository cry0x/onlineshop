package com.onlineshop.order_service.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class OrderExceptionHandler {

    @ExceptionHandler(value = {OrderNotFoundException.class, ProductNotAvailableException.class, CustomerDoesNotExistException.class})
    public final ResponseStatusException handleException(Exception exception) {
        if (exception instanceof OrderNotFoundException) {
            return handleExceptionInternally(HttpStatus.NOT_FOUND, exception);
        } else if (exception instanceof ProductNotAvailableException) {
            return handleExceptionInternally(HttpStatus.NOT_FOUND, exception);
        } else if (exception instanceof CustomerDoesNotExistException) {
            return handleExceptionInternally(HttpStatus.NOT_FOUND, exception);
        } else {
            return handleExceptionInternally(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    private ResponseStatusException handleExceptionInternally(HttpStatus httpStatus, Exception exception) {
        return new ResponseStatusException(httpStatus, exception.getMessage());
    }

}
