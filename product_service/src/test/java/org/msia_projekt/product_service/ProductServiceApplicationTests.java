package org.msia_projekt.product_service;

import org.junit.jupiter.api.Test;
import org.msia_projekt.product_service.controllers.ArticleController;
import org.msia_projekt.product_service.repositories.IArticleRepository;
import org.msia_projekt.product_service.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ProductServiceApplicationTests {

    @Autowired
    private ArticleController articleController;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private IArticleRepository iArticleRepository;
    @Autowired
    private ArticlePictureService articlePictureService;
    @Autowired
    private IArticlePictureRepository iArticlePictureRepository;

    @Test
    void contextLoads() {
        assertNotNull(articleController);
        assertNotNull(articleService);
        assertNotNull(iArticleRepository);
        assertNotNull(articlePictureService);
        assertNotNull(iArticlePictureRepository);
    }

}
