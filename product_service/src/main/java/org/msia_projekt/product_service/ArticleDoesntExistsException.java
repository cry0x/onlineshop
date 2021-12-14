package org.msia_projekt.product_service;

public class ArticleDoesntExistsException extends RuntimeException {

    public ArticleDoesntExistsException(Long id) {
        super(String.format("The article with the ID: %d doesnt exist!", id));
    }

}
