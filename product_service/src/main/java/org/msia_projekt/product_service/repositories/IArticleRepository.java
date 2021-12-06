package org.msia_projekt.product_service.repositories;

import org.msia_projekt.product_service.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IArticleRepository extends JpaRepository<Article, Long> {
}
