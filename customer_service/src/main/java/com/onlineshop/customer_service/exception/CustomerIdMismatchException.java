package com.onlineshop.customer_service.exception;

/**
 * Custom Exception Class that checks if the customerId matches.
 * @author Nico Welsch
 * @version 1.0
 */
public class CustomerIdMismatchException extends RuntimeException {

    public CustomerIdMismatchException() {
    }

    public CustomerIdMismatchException(String message) {
        super(message);
    }

    public CustomerIdMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerIdMismatchException(Throwable cause) {
        super(cause);
    }

    public CustomerIdMismatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}