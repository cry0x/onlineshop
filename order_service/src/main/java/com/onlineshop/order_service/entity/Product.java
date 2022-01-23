package com.onlineshop.order_service.entity;

import lombok.Data;

import javax.persistence.*;
<<<<<<< HEAD
import java.io.Serializable;
=======
>>>>>>> main

@Entity
@Table(name = "products")
@Data
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private long quantity;

    @Column(name = "original_id")
    private long originalId;
=======
    private Long productId;
    private long price;
    private long quantity;
    private Long original_id;
>>>>>>> main

}
