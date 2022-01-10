package org.msia_projekt.product_service;

import org.junit.jupiter.api.Test;
import org.msia_projekt.product_service.controllers.ArticleController;
import org.msia_projekt.product_service.controllers.ArticlePictureController;
import org.msia_projekt.product_service.repositories.IArticlePictureRepository;
import org.msia_projekt.product_service.repositories.IArticleRepository;
import org.msia_projekt.product_service.services.ArticlePictureService;
import org.msia_projekt.product_service.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ProductServiceApplicationTests {

    @Autowired
    private IArticleRepository iArticleRepository;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleController articleController;
    @Autowired
    private IArticlePictureRepository iArticlePictureRepository;
    @Autowired
    private ArticlePictureService articlePictureService;
    @Autowired
    private ArticlePictureController articlePictureController;

    @Test
    void contextLoads() {
        assertNotNull(iArticleRepository);
        assertNotNull(articleService);
        assertNotNull(articleController);
        assertNotNull(iArticlePictureRepository);
        assertNotNull(articlePictureService);
        assertNotNull(articlePictureController);
    }

}
