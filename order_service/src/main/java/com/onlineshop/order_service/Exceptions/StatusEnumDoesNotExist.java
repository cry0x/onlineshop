package com.onlineshop.order_service.Exceptions;

import com.onlineshop.order_service.entity.StatusEnum;

public class StatusEnumDoesNotExist extends RuntimeException {


    public StatusEnumDoesNotExist() {

    }

    public StatusEnumDoesNotExist(StatusEnum statusEnum) {
        super(statusEnum.toString().format("The order status %d does not exist!", statusEnum.toString())); // TODO
    }

    public StatusEnumDoesNotExist(String message) {
        super(message);
    }

    public StatusEnumDoesNotExist(String message, Throwable cause) {
        super(message, cause);
    }

}
