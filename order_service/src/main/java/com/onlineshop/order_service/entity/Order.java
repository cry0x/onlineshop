package com.onlineshop.order_service.entity;

import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Table(name = "orders")
public class Order implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private long id;

    @Column(name="total_amount", nullable = false)
    private double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name="order_status", nullable = false)
    private StatusEnum orderStatus;

    @Column(name="customer_id", nullable = false, updatable = false)
    private long customerId;

    @Column(name="customer_email", nullable = false)
    private String customerEmail;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Product> productListInOrder;

}

