package com.onlineshop.order_service.repository;

import com.onlineshop.order_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
<<<<<<< HEAD

public interface IProductRepository extends JpaRepository<Product, Long> {


    @Modifying
    @Query(value = "DELETE FROM orders_product_list_in_order WHERE product_list_in_order_id = ANY (SELECT product.id FROM product WHERE original_id = :original_id) AND order_id = :order_id", nativeQuery = true)
    void deleteByOriginalIdInProductListInOrder(@Param("original_id") Long originalProductId, @Param("order_id") Long orderId);

    @Modifying
    @Query(value = "DELETE FROM product WHERE original_id = :original_id", nativeQuery = true)
    void deleteByOriginalIdProducts(@Param("original_id") Long originalId);
    void deleteByOriginalId(Long originalId);

=======
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Product p WHERE p.original_id = :realProductId")
    boolean existsProductByRealId(@Param("realProductId") Long realProductId);
>>>>>>> main

}
