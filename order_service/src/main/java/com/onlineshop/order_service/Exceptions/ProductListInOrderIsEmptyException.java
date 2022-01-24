package com.onlineshop.order_service.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Custom exception that checks if the product list of an order is empty to prevent an order from being completed
 * invalidly.
 * @author Simon Spang
 */
public class ProductListInOrderIsEmptyException extends ResponseStatusException {


        public ProductListInOrderIsEmptyException(long orderId) {
            super(HttpStatus.BAD_REQUEST, String.format("The order (id: %d) can't be finished, since it contains no items!", orderId));
        }
}
