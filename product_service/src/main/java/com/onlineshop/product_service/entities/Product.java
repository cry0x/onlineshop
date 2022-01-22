package com.onlineshop.product_service.entities;

import com.onlineshop.product_service.exceptions.ProdcutQuantityMustNotBeNegative;
import lombok.*;

import javax.persistence.*;

/**
 * Entity class representing the data used inside a Product.
 * The schema of the table which stores the data of this entity will be generated automatically from this class.
 * It also contains a reference to the ProductPicture-table and a reference on the Product-table.
 */
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

    public int changeQuantity(int amount) {
        int newQuantity = getQuantity() + amount;

        if (newQuantity < 0)
            throw new ProdcutQuantityMustNotBeNegative(newQuantity);

        setQuantity(newQuantity);

        return getQuantity();
    }

}
