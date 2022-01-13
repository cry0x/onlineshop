package com.onlineshop.product_service.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "products")
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Product implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private double price;
    @Getter
    @Setter
    private int quantity;
    @Getter
    @Setter
    @OneToOne
    private ProductPicture productPicture;
    @Getter
    @Setter
    @OneToOne
    private Product newProductVersion;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
