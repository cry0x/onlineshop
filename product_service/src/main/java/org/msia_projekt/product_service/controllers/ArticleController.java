package org.msia_projekt.product_service.controllers;

import org.msia_projekt.product_service.entities.Article;
import org.msia_projekt.product_service.entities.ArticlePicture;
import org.msia_projekt.product_service.services.ArticlePictureService;
import org.msia_projekt.product_service.services.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/v1/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final ArticlePictureService articlePictureService;
    private final static Logger log = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    public ArticleController(ArticleService articleService, ArticlePictureService articlePictureService) {
        this.articleService = articleService;
        this.articlePictureService = articlePictureService;
    }

    @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    public EntityModel<Article> postArticle(@RequestBody Article article) {
        log.info("POST: /v1/articles has been called");

        if (article.getArticlePicture() == null)
            article.setArticlePicture(this.articlePictureService.createArticlePicture(new ArticlePicture()));

        Article createdArticle = this.articleService.createArticle(article);

        return EntityModel.of(createdArticle,
                linkTo(methodOn(ArticleController.class).getArticle(createdArticle.getId())).withSelfRel(),
                linkTo(methodOn(ArticlePictureController.class).getArticlePicture(createdArticle.getArticlePicture().getId())).withRel("article_picture"));
    }

    @GetMapping(value = "/{articleId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public EntityModel<Article> getArticle(@PathVariable Long articleId) {
        log.info(String.format("GET: v1/articles/%d has been called", articleId));

        Article article = this.articleService.readArticleById(articleId);

        return EntityModel.of(article,
                linkTo(methodOn(ArticleController.class).getArticle(article.getId())).withSelfRel(),
                linkTo(methodOn(ArticlePictureController.class).getArticlePicture(article.getArticlePicture().getId())).withRel("article_picture"));
    }

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public CollectionModel<EntityModel<Article>> getAllArticles() {
        log.info("GET: /v1/articles has been called");

        List<EntityModel<Article>> articles = this.articleService.readAllArticles().stream()
                .map(article -> EntityModel.of(article,
                    linkTo(methodOn(ArticleController.class).getArticle(article.getId())).withSelfRel(),
                    linkTo(methodOn(ArticlePictureController.class).getArticlePicture(article.getArticlePicture().getId())).withRel("article_picture")))
                .collect(Collectors.toList());

        return CollectionModel.of(articles,
                linkTo(methodOn(ArticleController.class).getAllArticles()).withSelfRel());
    }

    @PutMapping(value = "/{articleId}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    public EntityModel<Article> putArticle(@PathVariable Long articleId,
                                           @RequestBody Article article) {
        log.info(String.format("PUT: v1/articles/%d has been called", articleId));

        ArticlePicture articlePicture = this.articleService.readArticleById(articleId).getArticlePicture();
        article.setArticlePicture(articlePicture);

        Article updatedArticle = this.articleService.updateArticle(articleId, article);

        return EntityModel.of(updatedArticle,
                linkTo(methodOn(ArticleController.class).getArticle(updatedArticle.getId())).withSelfRel(),
                linkTo(methodOn(ArticlePictureController.class).getArticlePicture(updatedArticle.getArticlePicture().getId())).withRel("article_picture"));
    }

    @PutMapping(value = "/{articleId}/articlepicture")
    public EntityModel<Article> putArticlePictureOfArticleById(@PathVariable Long articleId,
                                                               @RequestBody MultipartFile file) throws IOException {
        log.info(String.format("PUT: v1/articles/%d/articlepicture has been called", articleId));

        Article article = this.articleService.readArticleById(articleId);

        ArticlePicture articlePicture = new ArticlePicture();
        articlePicture.setName(file.getOriginalFilename());
        articlePicture.setData(file.getInputStream().readAllBytes());

        articlePicture = this.articlePictureService.updateArticlePicture(article.getArticlePicture().getId(), articlePicture);

        article.setArticlePicture(articlePicture);

        article = this.articleService.updateArticle(article.getId(), article);

        return EntityModel.of(article,
                linkTo(methodOn(ArticleController.class).getArticle(article.getId())).withSelfRel(),
                linkTo(methodOn(ArticlePictureController.class).getArticlePicture(article.getArticlePicture().getId())).withRel("articlepicture"));
    }

    @DeleteMapping(path = "/{articleId}")
    public void deleteArticleById(@PathVariable Long articleId) {
        log.info(String.format("DELETE: v1/articles/%d has been called", articleId));

        this.articlePictureService.deleteArticlePictureById(this.articleService.readArticleById(articleId).getArticlePicture().getId());
        this.articleService.deleteArticleById(articleId);
    }

}
