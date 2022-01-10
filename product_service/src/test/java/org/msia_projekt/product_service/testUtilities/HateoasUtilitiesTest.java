package org.msia_projekt.product_service.testUtilities;

import org.junit.jupiter.api.Test;
import org.msia_projekt.product_service.controllers.ArticleController;
import org.msia_projekt.product_service.controllers.ArticlePictureController;
import org.msia_projekt.product_service.entities.Article;
import org.msia_projekt.product_service.entities.ArticlePicture;
import org.msia_projekt.product_service.utilities.HateoasUtilities;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@SpringBootTest
public class HateoasUtilitiesTest {

    @Test
    void buildArticleEntityTest() {
        ArticlePicture articlePicture = new ArticlePicture();
        articlePicture.setId(1L);
        articlePicture.setName("");
        articlePicture.setData(RandomData.RandomByteArray(20));

        Article article = new Article();
        article.setId(1L);
        article.setName("Testarticle");
        article.setDescription("This a Testarticle");
        article.setPrice(99.99);
        article.setQuantity(20);
        article.setArticlePicture(articlePicture);

        EntityModel<Article> expectedArticleEntityModel = EntityModel.of(article,
                linkTo(methodOn(ArticleController.class).getArticle(article.getId())).withSelfRel(),
                linkTo(methodOn(ArticlePictureController.class).getArticlePicture(article.getArticlePicture().getId())).withRel("article_picture"));

        assertEquals(expectedArticleEntityModel, HateoasUtilities.buildArticleEntity(article));
    }

    @Test
    void buildArticlePictureEntityTest() {
        ArticlePicture articlePicture = new ArticlePicture();
        articlePicture.setId(1L);
        articlePicture.setName("article_picture.jpg");
        articlePicture.setData(RandomData.RandomByteArray(20));

        EntityModel<ArticlePicture> expectedArticleEntityModel = EntityModel.of(articlePicture,
                linkTo(methodOn(ArticlePictureController.class).getArticlePicture(articlePicture.getId())).withSelfRel());

        assertEquals(expectedArticleEntityModel, HateoasUtilities.buildArticlePictureEntity(articlePicture));
    }

}
