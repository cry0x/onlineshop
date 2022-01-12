package com.onlineshop.order_service.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Product implements Serializable {

    @JoinColumn(name = "customer_id")
    private long orderId;
    @Getter
    @Setter
    @Id
    @Column(name="product_id",nullable = false,updatable = false)
    private long id;
    @Getter
    @Setter
    @Column(name="price",nullable = false)
    private long price;
    @Getter
    @Setter
    @Column(name="quantity",nullable = false)
    private long quantity;

}
