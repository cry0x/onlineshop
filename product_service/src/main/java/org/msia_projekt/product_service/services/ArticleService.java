package org.msia_projekt.product_service.services;

import org.msia_projekt.product_service.entities.Article;
import org.msia_projekt.product_service.repositories.IArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final IArticleRepository iArticleRepository;

    @Autowired
    public ArticleService(IArticleRepository iArticleRepository) {
        this.iArticleRepository = iArticleRepository;
    }

    public Article createArticle(Article article) {
        return this.iArticleRepository.saveAndFlush(article);
    }

    public Article updateArticle(Long id, Article article) {
        article.setId(id);
        return this.iArticleRepository.saveAndFlush(article);
    }

    public void deleteArticleById(Long id) {
        this.iArticleRepository.deleteById(id);
    }

    public Optional<Article> readArticleById(Long id) {
        return this.iArticleRepository.findById(id);
    }

    public List<Article> readAllArticles() {
        return this.iArticleRepository.findAll();
    }
}
