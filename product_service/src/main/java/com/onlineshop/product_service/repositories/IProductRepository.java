package com.onlineshop.product_service.repositories;

import com.onlineshop.product_service.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, Long> {
}