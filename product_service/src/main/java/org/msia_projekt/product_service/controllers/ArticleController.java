package org.msia_projekt.product_service.controllers;

import org.msia_projekt.product_service.entities.Article;
import org.msia_projekt.product_service.services.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public EntityModel<Article> postArticle(@RequestBody Article article) {
        log.info("POST: /v1/articles has been called");

        Article createdArticle = this.articleService.createArticle(article);

        return EntityModel.of(createdArticle,
                linkTo(methodOn(ArticleController.class).getArticle(article.getId())).withSelfRel(),
                linkTo(methodOn(ArticleController.class).getAllArticles()).withRel("articles"));
    }

    @GetMapping("/{id}")
    public EntityModel<Article> getArticle(@PathVariable Long id) {
        log.info(String.format("GET: v1/articles/%d has been called", id));

        Article article = this.articleService.readArticleById(id);

        return EntityModel.of(article,
                linkTo(methodOn(ArticleController.class).getArticle(id)).withSelfRel(),
                linkTo(methodOn(ArticleController.class).getAllArticles()).withRel("articles"));
    }

    @GetMapping
    public CollectionModel<EntityModel<Article>> getAllArticles() {
        log.info("GET: /v1/articles has been called");

        List<EntityModel<Article>> articles = this.articleService.readAllArticles().stream()
                .map(article -> EntityModel.of(article,
                    linkTo(methodOn(ArticleController.class).getArticle(article.getId())).withSelfRel(),
                    linkTo(methodOn(ArticleController.class).getAllArticles()).withRel("articles")))
                .collect(Collectors.toList());

        return CollectionModel.of(articles,
                linkTo(methodOn(ArticleController.class).getAllArticles()).withSelfRel());
    }

    @PutMapping("/{id}")
    public EntityModel<Article> putArticle(@PathVariable Long id,
                                           @RequestBody Article article) {
        log.info(String.format("PUT: v1/articles/%d has been called", id));

        Article updatedArticle = this.articleService.updateArticle(id, article);

        return EntityModel.of(updatedArticle,
                linkTo(methodOn(ArticleController.class).getArticle(updatedArticle.getId())).withSelfRel(),
                linkTo(methodOn(ArticleController.class).getAllArticles()).withRel("articles"));
    }

    @DeleteMapping(path = "/{id}")
    public void deleteArticleById(@PathVariable Long id) {
        log.info(String.format("DELETE: v1/articles/%d has been called", id));

        this.articleService.deleteArticleById(id);
    }

}
