package com.onlineshop.order_service.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * JPA entity class, where product object attributes are defined
 * @author Simon Spang
 */
@Entity
@Table(name = "products")
@Data
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private long quantity;

    @Column(name = "original_id")
    private long originalId;


}
