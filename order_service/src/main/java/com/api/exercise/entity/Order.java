package com.api.exercise.entity;

import javax.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private long orderId;
    @Getter
    @Setter
    private long totalPrice;
    @Getter
    @Setter
    private String orderStatus; // enum 1-4 for Pending, Cancelled, in_delivery, completed?

    private Customer customer;

    private List<Product> productInformation = new ArrayList<Product>();

    public List<Product> getProductInformation() {
        return productInformation;
    }

    // Reduce amount when order is done?
    public void setProductInformation(List<Product> productInformation) {
        this.productInformation = productInformation;
    }

}

