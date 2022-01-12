package com.onlineshop.product_service.repositories;

import com.onlineshop.product_service.entities.ProductPicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductPictureRepository extends JpaRepository<ProductPicture, Long> {
}
