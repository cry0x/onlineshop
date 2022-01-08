package org.msia_projekt.product_service.repositories;

import org.msia_projekt.product_service.entities.ArticlePicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IArticlePictureRepository extends JpaRepository<ArticlePicture, Long> {
}
