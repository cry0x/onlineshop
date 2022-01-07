package org.msia_projekt.product_service.services;

import org.msia_projekt.product_service.exceptions.ArticleDoesntExistsException;
import org.msia_projekt.product_service.entities.Article;
import org.msia_projekt.product_service.repositories.IArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final IArticleRepository iArticleRepository;

    @Autowired
    public ArticleService(IArticleRepository iArticleRepository) {
        this.iArticleRepository = iArticleRepository;
    }

    public Article createArticle(Article article) {
        return this.iArticleRepository.save(article);
    }

    public Article readArticleById(Long id) {
        return this.iArticleRepository.findById(id).orElseThrow(() -> new ArticleDoesntExistsException(id));
    }

    public List<Article> readAllArticles() {
        return this.iArticleRepository.findAll();
    }

    public Article updateArticle(Long id, Article updatedArticle) {
        if (!this.iArticleRepository.existsById(id))
            throw new ArticleDoesntExistsException(id);

        Article unchangedArticle = readArticleById(id);

        updatedArticle.setName(unchangedArticle.getName());
        updatedArticle.setId(id);

        return this.iArticleRepository.save(updatedArticle);
    }

    public void deleteArticleById(Long id) {
        if (!this.iArticleRepository.existsById(id))
            throw new ArticleDoesntExistsException(id);

        this.iArticleRepository.deleteById(id);
    }

}
