package com.onlineshop.product_service.repositories;

import com.onlineshop.product_service.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Interface which uses the standard-implementation of JpaRepository to make standard calls to the database without
 * further need to implement the queries. Represents the Product table.
 */
public interface IProductRepository extends JpaRepository<Product, Long> {

    /**
     * This method represents a custom query which is used to get to know if a ProductPicture is still referenced inside
     * a product.
     * @param productPictureId
     * @return
     */
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Product p WHERE p.productPicture.id = :productPictureId")
    boolean existsProductPictureInProduct(@Param("productPictureId") Long productPictureId);

}
