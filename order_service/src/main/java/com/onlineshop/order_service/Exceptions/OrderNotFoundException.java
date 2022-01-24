package com.onlineshop.order_service.Exceptions;

/**
 * Custom exception that checks if the order has been found.
 * @author Simon Spang
 */
public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Long orderId) {
        super(String.format("The order with the ID %d does not exist!", orderId));
    }

}
