package org.msia_projekt.product_service.utilities;

import org.msia_projekt.product_service.controllers.ArticleController;
import org.msia_projekt.product_service.controllers.ArticlePictureController;
import org.msia_projekt.product_service.entities.Article;
import org.msia_projekt.product_service.entities.ArticlePicture;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public final class HateoasUtilities {

    public static EntityModel<Article> buildArticleEntity(Article article) {
        return EntityModel.of(article,
                linkTo(methodOn(ArticleController.class).getArticle(article.getId())).withSelfRel(),
                linkTo(methodOn(ArticlePictureController.class).getArticlePicture(article.getArticlePicture().getId())).withRel("article_picture"));
    }

    public static EntityModel<ArticlePicture> buildArticlePictureEntity(ArticlePicture articlePicture) {
        return EntityModel.of(articlePicture,
                linkTo(methodOn(ArticlePictureController.class).getArticlePicture(articlePicture.getId())).withSelfRel());
    }

    public static CollectionModel<EntityModel<ArticlePicture>> buildArticlePictureCollection(List<ArticlePicture> articlePictureList) {
        List<EntityModel<ArticlePicture>> articlePictures = articlePictureList.stream()
                .map(articlePicture -> EntityModel.of(articlePicture,
                        linkTo(methodOn(ArticlePictureController.class).getArticlePicture(articlePicture.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(articlePictures,
                linkTo(methodOn(ArticlePictureController.class).getAllArticlePictures()).withSelfRel());
    }

}
