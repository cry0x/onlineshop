package com.onlineshop.order_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onlineshop.order_service.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Data
public class OrderDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)

    private long id;

    private long customerId;

    private long orderTotal;

    private String orderStatus;

    private List<Product> productListInOrder = new ArrayList<>(); // Default is empty list instead of null

}
