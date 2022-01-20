package com.onlineshop.order_service.Exceptions;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException() {

    }

    public OrderNotFoundException(Long orderId) {
        super(String.format("The order with the ID %d does not exist!", orderId));
    }

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
