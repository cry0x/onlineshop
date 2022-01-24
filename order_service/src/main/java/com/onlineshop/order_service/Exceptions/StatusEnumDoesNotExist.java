package com.onlineshop.order_service.Exceptions;

import com.onlineshop.order_service.entity.StatusEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Custom exception that checks if a status enum exists.
 * @author Simon Spang
 */
public class StatusEnumDoesNotExist extends ResponseStatusException {

    public StatusEnumDoesNotExist(StatusEnum statusEnum) {
        super(HttpStatus.BAD_REQUEST, statusEnum.toString().format("The order status %d does not exist!", statusEnum.toString())); // TODO
    }

}
