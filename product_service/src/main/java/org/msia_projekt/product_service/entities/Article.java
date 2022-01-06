package org.msia_projekt.product_service.entities;

import lombok.*;
import org.msia_projekt.product_service.utilities.DefaultProductPicture;

import javax.persistence.*;

@Entity
@Table(name = "articles")
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String description;
    @Lob
    @Getter
    @Setter
    private byte[] image = DefaultProductPicture.getDefaultProductPicture();
    @Getter
    @Setter
    private double price;
    @Getter
    @Setter
    private int quantity;

}
