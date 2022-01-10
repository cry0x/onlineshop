package org.msia_projekt.product_service.utilities;

import org.msia_projekt.product_service.controllers.ArticleController;
import org.msia_projekt.product_service.controllers.ArticlePictureController;
import org.msia_projekt.product_service.entities.Article;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class HateoasUtilities {

    public static EntityModel<Article> buildArticleEntity(Article article) {
        return EntityModel.of(article,
                linkTo(methodOn(ArticleController.class).getArticle(article.getId())).withSelfRel(),
                linkTo(methodOn(ArticlePictureController.class).getArticlePicture(article.getArticlePicture().getId())).withRel("article_picture"));
    }

    public static void buildArticlePictureEntity() {
    }

}
