package com.onlineshop.order_service.Exceptions;

public class ProductNotAvailableException extends RuntimeException {

    public ProductNotAvailableException() {

    }

    public ProductNotAvailableException(Long productId) {
        super(String.format("The product with the ID %d is not available!", productId));
    }

    public ProductNotAvailableException(Long productId, String message) {
        super(message);
    }

    public ProductNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
