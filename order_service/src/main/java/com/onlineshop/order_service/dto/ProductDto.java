package com.onlineshop.order_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

public class ProductDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)

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

}
