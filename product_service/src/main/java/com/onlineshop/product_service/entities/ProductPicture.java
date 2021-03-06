package com.onlineshop.product_service.entities;

import lombok.*;
import com.onlineshop.product_service.utilities.DefaultProductPicture;

import javax.persistence.*;

/**
 * Entity class representing the data used inside a ProductPicture.
 * The schema of the table which stores the data of this entity will be generated automatically from this class.
 */
@Entity
@Table(name = "product_pictures")
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class ProductPicture implements Cloneable {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @Setter
    private String name = DefaultProductPicture.getName();
    @Lob
    @Getter
    @Setter
    private byte[] data = DefaultProductPicture.getBinaryData();

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
