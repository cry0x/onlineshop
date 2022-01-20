package com.onlineshop.order_service.Exceptions;

public class CustomerDoesNotExistException extends RuntimeException {

    public CustomerDoesNotExistException() {

    }

    public CustomerDoesNotExistException(Long customerId) {
        super(String.format("The customer with the ID %d does not exist!", customerId));
    }

    public CustomerDoesNotExistException(String message) {
        super(message);
    }

    public CustomerDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
