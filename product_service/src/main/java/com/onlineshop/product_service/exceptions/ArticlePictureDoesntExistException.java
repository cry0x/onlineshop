package com.onlineshop.product_service.exceptions;

public class ArticlePictureDoesntExistException extends RuntimeException {

    public ArticlePictureDoesntExistException(Long id) {
        super(String.format("The articlepicture with the ID: %d doesnt exist!", id));
    }

}
