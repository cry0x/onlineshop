package org.msia_projekt.product_service.services;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.msia_projekt.product_service.entities.Article;
import org.msia_projekt.product_service.exceptions.ArticleDoesntExistsException;
import org.msia_projekt.product_service.repositories.IArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @MockBean
    private IArticleRepository iArticleRepository;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createArticle() {
        Article expectedArticle = new Article();
        expectedArticle.setId(1L);
        expectedArticle.setName("Table");
        expectedArticle.setDescription("Black table made of wood");
        expectedArticle.setPrice(129.99);
        expectedArticle.setStock(4);

        when(iArticleRepository.save(expectedArticle)).thenReturn(expectedArticle);

        assertEquals(expectedArticle, articleService.createArticle(expectedArticle));
    }

    @Test
    void updateArticle() {
        Article oldArticle = new Article();
        oldArticle.setId(1L);
        oldArticle.setName("Table");
        oldArticle.setDescription("Black table made of wood");
        oldArticle.setPrice(129.99);
        oldArticle.setStock(4);

        Article newArticle = new Article();
        newArticle.setId(1L);
        newArticle.setName("Table");
        newArticle.setDescription("Red table made of wood");
        newArticle.setPrice(229.99);
        newArticle.setStock(6);

        when(iArticleRepository.existsById(oldArticle.getId())).thenReturn(true);
        when(iArticleRepository.save(newArticle)).thenReturn(newArticle);

        Article actualArticle = this.articleService.updateArticle(oldArticle.getId(), newArticle);

        assertEquals(newArticle, actualArticle);
        assertNotEquals(oldArticle, actualArticle);
    }

    @Test
    void updateArticleThrowsArticleDoesntExistsException() {
        Article article = new Article();
        article.setId(1L);
        article.setName("Table");
        article.setDescription("Red table made of wood");
        article.setPrice(229.99);
        article.setStock(6);

        when(iArticleRepository.existsById(article.getId())).thenReturn(false);
        when(iArticleRepository.save(article)).thenReturn(article);

        assertThrows(ArticleDoesntExistsException.class, () -> this.articleService.updateArticle(article.getId(), article));
    }

    @Test
    void deleteArticleThrowsArticleDoesntExistsException() {
        Article article = new Article();
        article.setId(1L);
        article.setName("Table");
        article.setDescription("Red table made of wood");
        article.setPrice(229.99);
        article.setStock(6);

        when(iArticleRepository.existsById(article.getId())).thenReturn(false);

        assertThrows(ArticleDoesntExistsException.class, () -> this.articleService.deleteArticleById(article.getId()));
    }

}