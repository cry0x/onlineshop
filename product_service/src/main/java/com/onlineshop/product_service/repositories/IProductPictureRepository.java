package com.onlineshop.product_service.repositories;

import com.onlineshop.product_service.entities.ProductPicture;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface which uses the standard-implementation of JpaRepository to make standard calls to the database without
 * further need to implement the queries. Represents the ProductPicture table.
 */
public interface IProductPictureRepository extends JpaRepository<ProductPicture, Long> {
}
