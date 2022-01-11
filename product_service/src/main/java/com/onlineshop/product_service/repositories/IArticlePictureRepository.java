package com.onlineshop.product_service.repositories;

import com.onlineshop.product_service.entities.ArticlePicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IArticlePictureRepository extends JpaRepository<ArticlePicture, Long> {
}
