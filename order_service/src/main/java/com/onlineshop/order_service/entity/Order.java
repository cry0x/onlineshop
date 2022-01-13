package com.onlineshop.order_service.entity;

import javax.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order implements Serializable {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name="order_id",nullable = false,updatable = false)
    private long id;

    @Column(name="total_amount", nullable = false)
    private double totalAmount;

    @Column(name="order_status", nullable = false)
    private StatusEnum orderStatus;

    @Column(name="customer_id", nullable = false, updatable = false)
    private long customerId;

    @Transient
   // @OneToMany(mappedBy = "", cascade = CascadeType.ALL)
    private List<Product> productListInOrder = new ArrayList<>();

}

