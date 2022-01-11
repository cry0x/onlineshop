package com.onlineshop.product_service.services;

import com.onlineshop.product_service.entities.ArticlePicture;
import com.onlineshop.product_service.exceptions.ArticleDoesntExistsException;
import com.onlineshop.product_service.entities.Article;
import com.onlineshop.product_service.repositories.IArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final IArticleRepository iArticleRepository;
    private final ArticlePictureService articlePictureService;

    @Autowired
    public ArticleService(IArticleRepository iArticleRepository, ArticlePictureService articlePictureService) {
        this.iArticleRepository = iArticleRepository;
        this.articlePictureService = articlePictureService;
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

    public Article updateArticle(Long articleId, Article updatedArticle) {
        checkArticleExistsById(articleId);

        Article unchangedArticle = readArticleById(articleId);

        updatedArticle.setId(articleId);
        updatedArticle.setName(unchangedArticle.getName());

        ArticlePicture updatedArticlePicture = this.articlePictureService.updateArticlePicture(unchangedArticle.getArticlePicture().getId(), updatedArticle.getArticlePicture());
        updatedArticle.setArticlePicture(updatedArticlePicture);

        return this.iArticleRepository.save(updatedArticle);
    }

    public void deleteArticleById(Long articleId) {
        checkArticleExistsById(articleId);

        this.iArticleRepository.deleteById(articleId);
    }

    private final void checkArticleExistsById(Long articleId) {
        if (!this.iArticleRepository.existsById(articleId))
            throw new ArticleDoesntExistsException(articleId);
    }

}
