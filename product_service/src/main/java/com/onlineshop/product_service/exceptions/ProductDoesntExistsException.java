package com.onlineshop.product_service.exceptions;

public class ProductDoesntExistsException extends RuntimeException {

    public ProductDoesntExistsException(Long id) {
        super(String.format("The product with the ID: %d doesnt exist!", id));
    }

}
