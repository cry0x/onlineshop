package com.onlineshop.product_service;

import com.onlineshop.product_service.services.ArticlePictureService;
import com.onlineshop.product_service.services.ArticleService;
import org.junit.jupiter.api.Test;
import com.onlineshop.product_service.controllers.ArticleController;
import com.onlineshop.product_service.repositories.IArticlePictureRepository;
import com.onlineshop.product_service.repositories.IArticleRepository;
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
