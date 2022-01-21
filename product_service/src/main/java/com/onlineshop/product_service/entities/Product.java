package com.onlineshop.product_service.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class Product implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private int quantity;
    @OneToOne
    private ProductPicture productPicture;
    @OneToOne
    private Product newProductVersion;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
