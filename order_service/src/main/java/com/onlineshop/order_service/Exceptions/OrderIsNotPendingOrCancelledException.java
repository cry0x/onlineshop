package com.onlineshop.order_service.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Custom exception that checks if a order is IN_DELIVERY or COMPLETED to prevent from being deleted from the database.
 * @author Simon Spang
 */
public class OrderIsNotPendingOrCancelledException extends ResponseStatusException {

    public OrderIsNotPendingOrCancelledException(long orderId) {
        super(HttpStatus.BAD_REQUEST, String.format("The order (id: %d) is either IN_DELIVERY or COMPLETED and cannot be deleted!", orderId));
    }

    public OrderIsNotPendingOrCancelledException(long orderId, String message) {
        super(HttpStatus.BAD_REQUEST, String.format(message, orderId));
    }

}