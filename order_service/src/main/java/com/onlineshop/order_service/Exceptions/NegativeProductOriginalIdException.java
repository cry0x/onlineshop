package com.onlineshop.order_service.Exceptions;

import com.onlineshop.order_service.entity.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NegativeProductOriginalIdException extends ResponseStatusException {
    public NegativeProductOriginalIdException(Product product) {
        super(HttpStatus.BAD_REQUEST, String.format("The original id of a product (id: %d) can't have a negative value!", product.getOriginalId()));

    }
}
