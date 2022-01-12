package com.onlineshop.product_service.exceptions;

public class ProductPictureDoesntExistException extends RuntimeException {

    public ProductPictureDoesntExistException(Long id) {
        super(String.format("The productpicture with the ID: %d doesnt exist!", id));
    }

}
