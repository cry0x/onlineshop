package com.onlineshop.order_service.Exceptions;

import com.onlineshop.order_service.entity.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomerIdIsZeroOrNegative extends ResponseStatusException {

    public CustomerIdIsZeroOrNegative(Order order){
        super(HttpStatus.BAD_REQUEST, String.format("The Customer Id in an order (order id: %d) can't be 0 or have a negative value!", order.getId()));
    }

}
