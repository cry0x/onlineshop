package com.onlineshop.order_service.Exceptions;

import com.onlineshop.order_service.entity.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Custom exception that checks if the total amount has a negative value.
 * @author Simon Spang
 */
public class NegativeOrderTotalAmountException extends ResponseStatusException {

    public NegativeOrderTotalAmountException(Order order) {
        super(HttpStatus.BAD_REQUEST, String.format("The total amount of an order (order id: %d) can't be negative!", order.getId()));
    }

}
