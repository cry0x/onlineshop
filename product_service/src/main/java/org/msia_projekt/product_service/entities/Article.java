package org.msia_projekt.product_service.entities;

import lombok.*;
import org.msia_projekt.product_service.utilities.DefaultBase64ProductPicture;

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
    @Column(columnDefinition="TEXT")
    @Getter
    @Setter
    private String image = DefaultBase64ProductPicture.getDefaultBase64ProductPicture();
    @Getter
    @Setter
    private double price;
    @Getter
    @Setter
    private int quantity;
    @Lob
    @Getter
    @Setter
    private byte[] image_new;
}
