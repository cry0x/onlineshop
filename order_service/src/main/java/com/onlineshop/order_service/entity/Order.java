package com.onlineshop.order_service.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(nullable = false,updatable = false)
    private long id;

    @Column(name="total_amount", nullable = false)
    private double totalAmount;

    @Column(name="order_status", nullable = false)
    private StatusEnum orderStatus;

    @Column(name="customer_id", nullable = false, updatable = false)
    private long customerId;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Product> productListInOrder;

}

