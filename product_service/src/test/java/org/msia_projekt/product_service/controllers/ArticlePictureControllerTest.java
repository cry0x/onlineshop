package org.msia_projekt.product_service.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.msia_projekt.product_service.entities.ArticlePicture;
import org.msia_projekt.product_service.services.ArticlePictureService;
import org.msia_projekt.product_service.testUtilities.RandomData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.MessageResolver;
import org.springframework.hateoas.mediatype.hal.CurieProvider;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.hateoas.server.core.AnnotationLinkRelationProvider;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ArticlePictureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticlePictureService articlePictureService;

    private static ObjectMapper objectMapper;
    private static ArticlePicture testArticlePicture1;
    private static ArticlePicture testArticlePicture2;

    @BeforeAll
    static void before() {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(new Jackson2HalModule());
        objectMapper.setHandlerInstantiator(new Jackson2HalModule.HalHandlerInstantiator(new AnnotationLinkRelationProvider(), CurieProvider.NONE, MessageResolver.DEFAULTS_ONLY));

        testArticlePicture1 = new ArticlePicture();
        testArticlePicture1.setId(1L);
        testArticlePicture1.setName("Testpicture_1");
        testArticlePicture1.setData(RandomData.RandomByteArray(20));

        testArticlePicture2 = new ArticlePicture();
        testArticlePicture2.setId(2L);
        testArticlePicture2.setName("Testpicture_2");
        testArticlePicture2.setData(RandomData.RandomByteArray(20));
    }

    @Test
    void getArticlePictureTest() throws Exception {
        when(this.articlePictureService.readArticlePictureById(1L)).thenReturn(testArticlePicture1);

        EntityModel test = EntityModel.of(testArticlePicture1,
                linkTo(methodOn(ArticlePictureController.class).getArticlePicture(testArticlePicture1.getId())).withSelfRel());

        this.mockMvc.perform(get("/v1/articlepictures/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(content().json(objectMapper.writeValueAsString(test)));
    }

    @Test
    void getAllArticlePicturesTest() throws Exception {
        List<ArticlePicture> articlePictureList = List.of(testArticlePicture1, testArticlePicture2);
        articlePictureList.stream()
                .map(articlePicture -> EntityModel.of(articlePicture,
                    linkTo(methodOn(ArticlePictureController.class).getArticlePicture(articlePicture.getId())).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<ArticlePicture> expectedArticlePictureModel = CollectionModel.of(articlePictureList,
                linkTo(methodOn(ArticlePictureController.class).getAllArticlePictures()).withSelfRel());

        this.mockMvc.perform(get("/v1/articlepictures"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }
}
