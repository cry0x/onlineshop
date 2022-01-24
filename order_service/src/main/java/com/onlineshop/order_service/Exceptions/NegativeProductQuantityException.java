package com.onlineshop.order_service.Exceptions;



import com.onlineshop.order_service.entity.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Custom exception that checks if the quantity of a product has a negative value.
 * @author Simon Spang
 */
public class NegativeProductQuantityException extends ResponseStatusException {

    public NegativeProductQuantityException(Product product) {
        super(HttpStatus.BAD_REQUEST, String.format("The quantity of a product (product id: %d) can't be negative.", product.getId()));
    }

}
