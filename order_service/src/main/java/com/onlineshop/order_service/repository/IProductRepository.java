package com.onlineshop.order_service.repository;

import com.onlineshop.order_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, Long> {

    void deleteByOriginalId(Long originalId);

}
