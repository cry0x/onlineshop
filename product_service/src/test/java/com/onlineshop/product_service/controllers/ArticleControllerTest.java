package com.onlineshop.product_service.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshop.product_service.controllers.ArticleController;
import com.onlineshop.product_service.controllers.ArticlePictureController;
import com.onlineshop.product_service.entities.Article;
import com.onlineshop.product_service.entities.ArticlePicture;
import com.onlineshop.product_service.services.ArticlePictureService;
import com.onlineshop.product_service.services.ArticleService;
import com.onlineshop.product_service.testUtilities.RandomData;
import com.onlineshop.product_service.utilities.HateoasUtilities;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.MessageResolver;
import org.springframework.hateoas.mediatype.hal.CurieProvider;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.hateoas.server.core.AnnotationLinkRelationProvider;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest({"eureka.client.enabled:false"})
@AutoConfigureMockMvc
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ArticleService articleService;
    @MockBean
    private ArticlePictureService articlePictureService;

    private static ArticlePicture testArticlePicture;
    private static ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll() {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(new Jackson2HalModule());
        objectMapper.setHandlerInstantiator(new Jackson2HalModule.HalHandlerInstantiator(new AnnotationLinkRelationProvider(), CurieProvider.NONE, MessageResolver.DEFAULTS_ONLY));

        testArticlePicture = new ArticlePicture();
        testArticlePicture.setId(1L);
        testArticlePicture.setName("Testartikelpicture");
        testArticlePicture.setData(RandomData.RandomByteArray(20));
    }

    @Test
    void getArticleTest() throws Exception {
        Long requestArticleId = 1L;

        Article expectedArticle = new Article();
        expectedArticle.setId(1L);
        expectedArticle.setName("Testartikel_1");
        expectedArticle.setDescription("Das ist ein Testartikel mit der ID 1");
        expectedArticle.setPrice(9.99);
        expectedArticle.setQuantity(5);
        expectedArticle.setArticlePicture(testArticlePicture);

        when(this.articleService.readArticleById(requestArticleId)).thenReturn(expectedArticle);

        EntityModel articlePictureEntityModel = EntityModel.of(expectedArticle,
                linkTo(methodOn(ArticleController.class).getArticle(expectedArticle.getId())).withSelfRel(),
                linkTo(methodOn(ArticlePictureController.class).getArticlePicture(expectedArticle.getArticlePicture().getId())).withRel("article_picture"));

        this.mockMvc.perform(get("/v1/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(content().json(objectMapper.writeValueAsString(articlePictureEntityModel)));
    }

    @Test
    void puArticleTestWrongParameter() throws Exception {
        this.mockMvc.perform(put(String.format("/v1/articles/%s", "a")).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void putArticleTest() throws Exception {
        Long articleId = 1L;


        Article updatedArticle = new Article();
        updatedArticle.setName("Updated Testarticle");
        updatedArticle.setDescription("This is a updated Testarticle");
        updatedArticle.setQuantity(10);
        updatedArticle.setPrice(99.99);
        updatedArticle.setArticlePicture(testArticlePicture);

        String updatedArticleJson = objectMapper.writeValueAsString(updatedArticle);

        when(this.articleService.readArticleById(articleId)).thenReturn(updatedArticle);

        Article updatedArticleWithId = new Article();
        updatedArticleWithId.setId(articleId);
        updatedArticleWithId.setName("Testarticle");
        updatedArticleWithId.setDescription("This is a updated Testarticle");
        updatedArticleWithId.setQuantity(10);
        updatedArticleWithId.setPrice(99.99);
        updatedArticleWithId.setArticlePicture(testArticlePicture);

        when(this.articleService.updateArticle(articleId, updatedArticle)).thenReturn(updatedArticleWithId);

        Article expectedArticle = new Article();
        expectedArticle.setId(articleId);
        expectedArticle.setName("Testarticle");
        expectedArticle.setDescription("This is a updated Testarticle");
        expectedArticle.setQuantity(10);
        expectedArticle.setPrice(99.99);
        expectedArticle.setArticlePicture(testArticlePicture);

        String expectedHateoasArticleJson = objectMapper.writeValueAsString(HateoasUtilities.buildArticleEntity(expectedArticle));

        this.mockMvc.perform(put(String.format("/v1/articles/%d", articleId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedArticleJson))
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(content().json(expectedHateoasArticleJson))
                .andExpect(status().isOk());
    }

    @Test
    void postArticle() throws Exception {
        Article actualArticle = RandomData.RandomArticleWithoutId();
        String actualArticleJson = objectMapper.writeValueAsString(actualArticle);

        Article expectedArticle = (Article) actualArticle.clone();
        expectedArticle.setId(1L);
        String expectedArticleJson = objectMapper.writeValueAsString(HateoasUtilities.buildArticleEntity(expectedArticle));

        when(this.articleService.createArticle(actualArticle)).thenReturn(expectedArticle);

        this.mockMvc.perform(post("/v1/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(actualArticleJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(content().json(expectedArticleJson));
    }

    @Test
    void deleteArticleByIdTest() throws Exception {
        Article article = RandomData.RandomArticle();
        article.setId(1L);
        article.getArticlePicture().setId(1L);
        Long articleId  = article.getId();

        when(this.articleService.readArticleById(articleId)).thenReturn(article);

        this.mockMvc.perform(delete(String.format("/v1/articles/%d", articleId)))
                .andExpect(status().isOk());
    }

}
