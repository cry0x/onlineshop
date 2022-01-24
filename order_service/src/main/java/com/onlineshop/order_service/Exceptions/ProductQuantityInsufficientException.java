package com.onlineshop.order_service.Exceptions;

/**
 * Custom exception that checks if there is sufficient amount of product left in stock.
 * @author Simon Spang
 */
public class ProductQuantityInsufficientException extends RuntimeException {

    public ProductQuantityInsufficientException(Long productId) {
        super(String.format("There is not enough product (id: %d) in stock!", productId));
    }

}
