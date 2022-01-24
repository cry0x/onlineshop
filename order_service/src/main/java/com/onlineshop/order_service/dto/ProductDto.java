package com.onlineshop.order_service.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

/**
 * DataTransferObject for Products. This can limit the "access rights", when using Product objects.
 * @author Simon Spang
 */
public class ProductDto {

    @Id
    @Getter
    @Setter
    private long id;
    @Getter
    @Setter
    private double price;
    @Getter
    @Setter
    private long quantity;
    @Getter
    @Setter
    private long originalId;

}
