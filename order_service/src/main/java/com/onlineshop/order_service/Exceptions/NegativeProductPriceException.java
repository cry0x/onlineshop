package com.onlineshop.order_service.Exceptions;

import com.onlineshop.order_service.entity.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Custom exception that checks if a product price has a negative value.
 * @author Simon Spang
 */
public class NegativeProductPriceException extends ResponseStatusException {

    public NegativeProductPriceException(Product product) {
        super(HttpStatus.BAD_REQUEST, String.format("The price of a product (product id: %d) can't be negative!", product.getId()));
    }
}
