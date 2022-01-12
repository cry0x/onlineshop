package com.onlineshop.product_service.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshop.product_service.entities.ProductPicture;
import com.onlineshop.product_service.repositories.IProductPictureRepository;
import com.onlineshop.product_service.services.ProductPictureService;
import com.onlineshop.product_service.testUtilities.RandomData;
import com.onlineshop.product_service.utilities.HateoasUtilities;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.MessageResolver;
import org.springframework.hateoas.mediatype.hal.CurieProvider;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.hateoas.server.core.AnnotationLinkRelationProvider;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest({"eureka.client.enabled:false"})
@AutoConfigureMockMvc
public class ProductPictureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductPictureService productPictureService;
    @MockBean
    private IProductPictureRepository iProductPictureRepository;

    private static ObjectMapper objectMapper;
    private static ProductPicture testProductPicture1;
    private static ProductPicture testProductPicture2;

    @BeforeAll
    static void before() {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(new Jackson2HalModule());
        objectMapper.setHandlerInstantiator(new Jackson2HalModule.HalHandlerInstantiator(new AnnotationLinkRelationProvider(), CurieProvider.NONE, MessageResolver.DEFAULTS_ONLY));

        testProductPicture1 = new ProductPicture();
        testProductPicture1.setId(1L);
        testProductPicture1.setName("Testpicture_1");
        testProductPicture1.setData(RandomData.RandomByteArray(20));

        testProductPicture2 = new ProductPicture();
        testProductPicture2.setId(2L);
        testProductPicture2.setName("Testpicture_2");
        testProductPicture2.setData(RandomData.RandomByteArray(20));
    }

    @Test
    void getProductPictureTest() throws Exception {
        when(this.productPictureService.readProductPictureById(testProductPicture1.getId())).thenReturn(testProductPicture1);

        String expectedHateoasProductJson = objectMapper.writeValueAsString(HateoasUtilities.buildProductPictureEntity(testProductPicture1));

        this.mockMvc.perform(get("/v1/productpictures/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(content().json(expectedHateoasProductJson));
    }

    @Test
    void getAllProductPicturesTest() throws Exception {
        List<ProductPicture> expectedProductPictureList = RandomData.RandomProductPictureList(15);

        when(this.productPictureService.readAllProductPictures()).thenReturn(expectedProductPictureList);

        this.mockMvc.perform(get("/v1/productpictures"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }
}
