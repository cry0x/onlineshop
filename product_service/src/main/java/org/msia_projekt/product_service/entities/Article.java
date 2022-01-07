package org.msia_projekt.product_service.entities;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
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
    private ArticlePicture articlePicture;

}
