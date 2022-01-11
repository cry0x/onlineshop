package com.onlineshop.product_service.repositories;

import com.onlineshop.product_service.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IArticleRepository extends JpaRepository<Article, Long> {
}
