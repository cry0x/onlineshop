package com.onlineshop.product_service.exceptions;

public class ArticleDoesntExistsException extends RuntimeException {

    public ArticleDoesntExistsException(Long id) {
        super(String.format("The article with the ID: %d doesnt exist!", id));
    }

}
