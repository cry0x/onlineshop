package com.onlineshop.product_service.repositories;

import com.onlineshop.product_service.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Product p WHERE p.productPicture.id = :productPictureId")
    boolean existsProductPictureInProduct(@Param("productPictureId") Long productPictureId);

}
