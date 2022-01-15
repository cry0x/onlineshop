package com.onlineshop.order_service.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Product implements Serializable {



    @Id
    @Column(name="order_id",nullable = false,updatable = false)
    private long id;
/*
    @PrimaryKeyJoinColumn(name = "order_id") // needed because it's also foreign key
    Order order;*/

    @Column(name = "product_id", nullable = false)
    private long productId;

    @Column(name="price",nullable = false)
    private double price;

    @Column(name="quantity",nullable = false)
    private long quantity;

}
