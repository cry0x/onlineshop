package org.msia_projekt.product_service;

public class ArticlePictureDoesntExistException extends RuntimeException {

    public ArticlePictureDoesntExistException(Long id) {
        super(String.format("The articlepicture with the ID: %d doesnt exist!", id));
    }

}
