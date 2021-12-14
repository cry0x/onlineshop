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
        log.info("POST: /v1/articles has been called");

        return this.articleService.createArticle(article);
    }

    @GetMapping("/{id}")
    public Article getArticle(@PathVariable Long id) {
        log.info(String.format("GET: v1/articles/%d has been called", id));

        return this.articleService.readArticleById(id);
    }

    @GetMapping
    public List<Article> getAllArticles() {
        log.info("GET: /v1/articles has been called");

        return this.articleService.readAllArticles();
    }

    @PutMapping("/{id}")
    public Article putArticle(@PathVariable Long id,
                              @RequestBody Article article) {
        log.info(String.format("PUT: v1/articles/%d has been called", id));

        return this.articleService.updateArticle(id, article);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteArticleById(@PathVariable Long id) {
        log.info(String.format("DELETE: v1/articles/%d has been called", id));

        this.articleService.deleteArticleById(id);
    }

}
