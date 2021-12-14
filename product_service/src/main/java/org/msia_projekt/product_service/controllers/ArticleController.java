package org.msia_projekt.product_service.controllers;

import org.msia_projekt.product_service.entities.Article;
import org.msia_projekt.product_service.services.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final static Logger log = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public Article postArticle(@RequestBody Article article) {
        return this.articleService.createArticle(article);
    }

    @GetMapping("/{id}")
    public Article getArticle(@PathVariable Long id) {
        return this.articleService.readArticleById(id).get();
    }

    @GetMapping
    public List<Article> getAllArticles() {
        return this.articleService.readAllArticles();
    }

    @PutMapping("/{id}")
    public Article updateArticle(@PathVariable Long id,
                              @RequestBody Article article) {
        return this.articleService.updateArticle(id, article);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteArticleById(@PathVariable Long id) {
        this.articleService.deleteArticleById(id);
    }

}
