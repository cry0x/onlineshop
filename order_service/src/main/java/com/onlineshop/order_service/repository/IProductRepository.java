package com.onlineshop.order_service.repository;

import com.onlineshop.order_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IProductRepository extends JpaRepository<Product, Long> {


    @Modifying
    @Query(value = "DELETE FROM orders_product_list_in_order WHERE product_list_in_order_id = ANY (SELECT product.id FROM product WHERE original_id = :original_id) AND order_id = :order_id", nativeQuery = true)
    void deleteByOriginalIdInProductListInOrder(@Param("original_id") Long originalProductId, @Param("order_id") Long orderId);

    @Modifying
    @Query(value = "DELETE FROM product WHERE original_id = :original_id", nativeQuery = true)
    void deleteByOriginalIdProducts(@Param("original_id") Long originalId);

}
