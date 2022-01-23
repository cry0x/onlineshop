package com.onlineshop.order_service.repository;

import com.onlineshop.order_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Query(value = "DELETE FROM orders_product_list_in_order WHERE product_list_in_order_id = ANY (SELECT products.id FROM products WHERE original_id = :original_id) AND order_id = :order_id", nativeQuery = true)
    void deleteByOriginalIdInProductListInOrder(@Param("original_id") Long originalProductId, @Param("order_id") Long orderId);

    @Modifying
    @Query(value = "DELETE FROM products WHERE original_id = :original_id", nativeQuery = true)
    void deleteByOriginalIdProducts(@Param("original_id") Long originalId);

    @Query(value = "SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Products p WHERE p.original_id = :realProductId", nativeQuery = true)
    boolean existsProductByRealId(@Param("realProductId") Long realProductId);




}
