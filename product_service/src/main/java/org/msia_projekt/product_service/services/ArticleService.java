package org.msia_projekt.product_service.services;

import org.apache.commons.io.FileUtils;
import org.msia_projekt.product_service.exceptions.ArticleDoesntExistsException;
import org.msia_projekt.product_service.entities.Article;
import org.msia_projekt.product_service.repositories.IArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
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

    public Article updateArticle(Long id, Article updatedArticle) {
        if (!this.iArticleRepository.existsById(id))
            throw new ArticleDoesntExistsException(id);

        updatedArticle.setId(id);
        return this.iArticleRepository.save(updatedArticle);
    }

    public void deleteArticleById(Long id) {
        if (!this.iArticleRepository.existsById(id))
            throw new ArticleDoesntExistsException(id);

        this.iArticleRepository.deleteById(id);
    }

    public Article readArticleById(Long id) {
        return this.iArticleRepository.findById(id).orElseThrow(() -> new ArticleDoesntExistsException(id));
    }

    public List<Article> readAllArticles() {
        return this.iArticleRepository.findAll();
    }

    public String readBase64PictureFromArticle(Long id) throws IOException {
        Article article = this.iArticleRepository.findById(id).orElseThrow(() -> new ArticleDoesntExistsException(id));

        byte[] fileContent = FileUtils.readFileToByteArray(new File(article.getPicture()));
        return Base64.getEncoder().encodeToString(fileContent);
    }
}
