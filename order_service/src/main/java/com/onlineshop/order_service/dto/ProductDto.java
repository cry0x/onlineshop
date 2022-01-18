package com.onlineshop.order_service.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

public class ProductDto {

   // @Schema(accessMode = Schema.AccessMode.READ_ONLY)
   // @JsonProperty(access = JsonProperty.Access.READ_ONLY) // TODO  remove when ID comes from productService

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
