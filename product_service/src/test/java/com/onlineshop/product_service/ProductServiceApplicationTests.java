package com.onlineshop.product_service;

import com.onlineshop.product_service.controllers.ArticlePictureController;
import com.onlineshop.product_service.services.ArticlePictureService;
import com.onlineshop.product_service.services.ArticleService;
import org.junit.jupiter.api.Test;
import com.onlineshop.product_service.controllers.ArticleController;
import com.onlineshop.product_service.repositories.IArticlePictureRepository;
import com.onlineshop.product_service.repositories.IArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest({"eureka.client.enabled:false"})
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
