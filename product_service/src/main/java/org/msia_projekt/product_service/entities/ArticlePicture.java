package org.msia_projekt.product_service.entities;

import lombok.*;
import org.msia_projekt.product_service.utilities.DefaultProductPicture;

import javax.persistence.*;

@Entity
@Table(name = "article_pictures")
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class ArticlePicture implements Cloneable {

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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
