package com.onlineshop.product_service.entities;

import lombok.*;
import com.onlineshop.product_service.utilities.DefaultProductPicture;

import javax.persistence.*;

@Entity
@Table(name = "article_pictures")
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class ArticlePicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String name = DefaultProductPicture.getName();
    @Lob
    @Getter
    @Setter
    private byte[] data = DefaultProductPicture.getBinaryData();

}
