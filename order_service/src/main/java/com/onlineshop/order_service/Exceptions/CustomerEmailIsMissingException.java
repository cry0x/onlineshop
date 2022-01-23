package com.onlineshop.order_service.Exceptions;

import com.onlineshop.order_service.entity.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomerEmailIsMissingException extends ResponseStatusException {

    public CustomerEmailIsMissingException(Order order) {
        super(HttpStatus.BAD_REQUEST, String.format("The email address of the customer (customer id: %d) is missing in order id: %d !", order.getCustomerId(), order.getId()));
    }
}
