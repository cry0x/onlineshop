package org.msia_projekt.product_service;

import lombok.*;
import org.msia_projekt.product_service.utilities.DefaultProductPicture;

import javax.persistence.*;

@Entity
@Table(name = "article_pictures")
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class ArticlePicture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;
    @Lob
    @Getter
    @Setter
    private byte[] image = DefaultProductPicture.getDefaultProductPicture();

}
