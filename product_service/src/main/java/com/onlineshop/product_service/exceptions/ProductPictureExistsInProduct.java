package com.onlineshop.product_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProductPictureExistsInProduct extends ResponseStatusException {

    public ProductPictureExistsInProduct() {
        super(HttpStatus.BAD_REQUEST, getExceptionMessage());
    }

    private static String getExceptionMessage() {
        return "The productpicture is still referenced in a product!";
    }

}
