package com.onlineshop.order_service.entity;

import javax.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Order implements Serializable {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name="order_id",nullable = false,updatable = false)
    private long id;

    @Column(name="total_price", nullable = false)
    private long totalPrice;

    @Column(name="order_status", nullable = false)
    private String orderStatus; // enum 1-4 for Pending, Cancelled, in_delivery, completed?

    @Column(name="customer_id", nullable = false, updatable = false)
    private long customerId;

    @Column(name= "customer_email", nullable = false)
    private long customerEmail;
    @Transient
    private List<Product> productListInOrder = new ArrayList<>();

}

