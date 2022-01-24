package com.onlineshop.order_service.Exceptions;

import com.onlineshop.order_service.entity.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Custom exception that checks if the original product id of a product is missing.
 * @author Simon Spang
 */
public class MissingOriginalProductIdException extends ResponseStatusException {

    public MissingOriginalProductIdException(Product product) {
        super(HttpStatus.BAD_REQUEST, String.format("The original id of a product can't be empty or 0! (product id in orders: %d)", product.getId()));
    }
}
