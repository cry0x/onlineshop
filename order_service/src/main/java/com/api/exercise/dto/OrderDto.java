package com.api.exercise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class OrderDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Getter
    @Setter
    private Long orderId;
    @Getter
    @Setter
    private Long customerId;
    @Getter
    @Setter
    private Long orderTotal;
    @Getter
    @Setter
    private String orderStatus;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<OrderDto> orderInformation = new ArrayList<>(); // Default is empty list instead of null

    public List<OrderDto> getOrderInformation() {
        return orderInformation;
    }

    public void setOrderInformation(List<OrderDto> orderInformation) {
        this.orderInformation = orderInformation;
    }

}
