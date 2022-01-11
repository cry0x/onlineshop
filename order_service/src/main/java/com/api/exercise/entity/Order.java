package com.api.exercise.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
// import Product...
// import Customer ...

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
    private long  customerId;
    @Getter
    @Setter
    private long total;
    private Customer customer;
    @Getter
    @Setter
    private String orderStatus; // enum 1-4 for Pending, Cancelled, in_delivery, completed?
    private List<Product> productInformation = new ArrayList<Product>(); // Info

    public List<Product> getProductInformation() {
        return productInformation;
    }

    // Reduce amount when order is done?
    public void setProductInformation(List<Product> productInformation) {
        this.productInformation = productInformation;
    }




}

