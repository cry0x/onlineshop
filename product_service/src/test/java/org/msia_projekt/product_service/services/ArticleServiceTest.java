package org.msia_projekt.product_service.services;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.msia_projekt.product_service.entities.Article;
import org.msia_projekt.product_service.entities.ArticlePicture;
import org.msia_projekt.product_service.exceptions.ArticleDoesntExistsException;
import org.msia_projekt.product_service.repositories.IArticleRepository;
import org.msia_projekt.product_service.testUtilities.RandomData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @MockBean
    private IArticleRepository iArticleRepository;
    @MockBean
    private ArticlePictureService articlePictureService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createArticle() {
        Long articleId = 1L;

        Article newArticle = new Article();
        newArticle.setName("Table");
        newArticle.setDescription("Black table made of wood");
        newArticle.setPrice(129.99);
        newArticle.setQuantity(4);

        Article expectedArticle = new Article();
        expectedArticle.setId(articleId);
        expectedArticle.setName("Table");
        expectedArticle.setDescription("Black table made of wood");
        expectedArticle.setPrice(129.99);
        expectedArticle.setQuantity(4);

        when(this.iArticleRepository.save(newArticle)).thenReturn(expectedArticle);

        assertEquals(expectedArticle, this.articleService.createArticle(newArticle));
    }

    @Test
    void updateArticle() {
        Long articleId = 1L;

        when(this.iArticleRepository.existsById(articleId)).thenReturn(true);

        ArticlePicture oldArticlePicture = new ArticlePicture();
        oldArticlePicture.setId(1L);
        oldArticlePicture.setName("ArticlePicture");
        oldArticlePicture.setData(RandomData.RandomByteArray(20));

        Article oldArticle = new Article();
        oldArticle.setId(articleId);
        oldArticle.setName("Article");
        oldArticle.setDescription("This is a Article");
        oldArticle.setPrice(99.99);
        oldArticle.setQuantity(100);
        oldArticle.setArticlePicture(oldArticlePicture);

        when(this.iArticleRepository.findById(articleId)).thenReturn(Optional.of(oldArticle));

        byte[] newArticlePictureData = RandomData.RandomByteArray(20);

        ArticlePicture newArticlePicture = new ArticlePicture();
        newArticlePicture.setName("Updated ArticlePicture");
        newArticlePicture.setData(newArticlePictureData);

        Article newArticle = new Article();
        newArticle.setName("Updated Article");
        newArticle.setDescription("This is a updated Article");
        newArticle.setPrice(9.99);
        newArticle.setQuantity(10);
        newArticle.setArticlePicture(newArticlePicture);

        ArticlePicture expectedArticlePicture = new ArticlePicture();
        expectedArticlePicture.setId(1L);
        expectedArticlePicture.setName("Updated ArticlePicture");
        expectedArticlePicture.setData(newArticlePictureData);

        when(this.articlePictureService.updateArticlePicture(oldArticle.getArticlePicture().getId(), newArticlePicture)).thenReturn(expectedArticlePicture);


        Article expectedArticle = new Article();
        expectedArticle.setId(1L);
        expectedArticle.setName("Article");
        expectedArticle.setDescription("This is a updated Article");
        expectedArticle.setPrice(9.99);
        expectedArticle.setQuantity(10);
        expectedArticle.setArticlePicture(expectedArticlePicture);

        when(this.iArticleRepository.save(expectedArticle)).thenReturn(expectedArticle);

        assertEquals(expectedArticle, this.articleService.updateArticle(articleId, newArticle));
    }

    @Test
    void updateArticleThrowsArticleDoesntExistsException() {
        Long articleId = 1L;

        Article newArticle = new Article();
        newArticle.setName("Table");
        newArticle.setDescription("Red table made of wood");
        newArticle.setPrice(229.99);
        newArticle.setQuantity(6);

        when(this.iArticleRepository.existsById(articleId)).thenReturn(false);

        assertThrows(ArticleDoesntExistsException.class, () -> this.articleService.updateArticle(articleId, newArticle));
    }

    @Test
    void deleteArticleThrowsArticleDoesntExistsException() {
        Long articleId = 1L;

        when(this.iArticleRepository.existsById(articleId)).thenReturn(false);

        assertThrows(ArticleDoesntExistsException.class, () -> this.articleService.deleteArticleById(articleId));
    }

    @Test
    void readAllArticlesTest() {
        List<Article> expectedArticleList = RandomData.RandomArticleList(15);

        when(this.iArticleRepository.findAll()).thenReturn(expectedArticleList);

        assertEquals(expectedArticleList, this.articleService.readAllArticles());
    }

}