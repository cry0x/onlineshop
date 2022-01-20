package com.onlineshop.order_service.entity;

import javax.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
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
    @Transient
    private Customer customer;
    @Transient
    private List<Product> productInformation = new ArrayList<>();

    public List<Product> getProductInformation() {
        return productInformation;
    }

    // Reduce amount when order is done?
    public void setProductInformation(List<Product> productInformation) {
        this.productInformation = productInformation;
    }

}

