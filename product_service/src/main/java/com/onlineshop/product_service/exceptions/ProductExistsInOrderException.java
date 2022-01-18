package com.onlineshop.product_service.exceptions;

public class ProductExistsInOrderException extends RuntimeException {
    public ProductExistsInOrderException(Long productId) {
        super(String.format("The product with the ID: %d still exists in an order!", productId));
    }
}
