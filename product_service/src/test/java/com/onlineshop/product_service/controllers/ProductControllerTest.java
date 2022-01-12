package com.onlineshop.product_service.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshop.product_service.entities.Product;
import com.onlineshop.product_service.entities.ProductPicture;
import com.onlineshop.product_service.services.ProductPictureService;
import com.onlineshop.product_service.services.ProductService;
import com.onlineshop.product_service.testUtilities.RandomData;
import com.onlineshop.product_service.utilities.HateoasUtilities;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.MessageResolver;
import org.springframework.hateoas.mediatype.hal.CurieProvider;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.hateoas.server.core.AnnotationLinkRelationProvider;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest({"eureka.client.enabled:false"})
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    @MockBean
    private ProductPictureService productPictureService;

    private static ProductPicture testProductPicture;
    private static ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll() {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(new Jackson2HalModule());
        objectMapper.setHandlerInstantiator(new Jackson2HalModule.HalHandlerInstantiator(new AnnotationLinkRelationProvider(), CurieProvider.NONE, MessageResolver.DEFAULTS_ONLY));

        testProductPicture = new ProductPicture();
        testProductPicture.setId(1L);
        testProductPicture.setName("Testproductpicture");
        testProductPicture.setData(RandomData.RandomByteArray(20));
    }

    @Test
    void getProductTest() throws Exception {
        Long requestProductId = 1L;

        Product expectedProduct = new Product();
        expectedProduct.setId(1L);
        expectedProduct.setName("Testproduct_1");
        expectedProduct.setDescription("Das ist ein Testproduct mit der ID 1");
        expectedProduct.setPrice(9.99);
        expectedProduct.setQuantity(5);
        expectedProduct.setProductPicture(testProductPicture);

        when(this.productService.readProductById(requestProductId)).thenReturn(expectedProduct);

        EntityModel productPictureEntityModel = EntityModel.of(expectedProduct,
                linkTo(methodOn(ProductController.class).getProduct(expectedProduct.getId())).withSelfRel(),
                linkTo(methodOn(ProductPictureController.class).getProductPicture(expectedProduct.getProductPicture().getId())).withRel("product_picture"));

        this.mockMvc.perform(get("/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(content().json(objectMapper.writeValueAsString(productPictureEntityModel)));
    }

    @Test
    void putProductTestWrongParameter() throws Exception {
        this.mockMvc.perform(put(String.format("/v1/products/%s", "a")).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void putProductTest() throws Exception {
        Long productId = 1L;

        Product updatedProduct = new Product();
        updatedProduct.setName("Updated Testproduct");
        updatedProduct.setDescription("This is a updated Testproduct");
        updatedProduct.setQuantity(10);
        updatedProduct.setPrice(99.99);
        updatedProduct.setProductPicture(testProductPicture);

        String updatedProductJson = objectMapper.writeValueAsString(updatedProduct);

        when(this.productService.readProductById(productId)).thenReturn(updatedProduct);

        Product updatedProductWithId = new Product();
        updatedProductWithId.setId(productId);
        updatedProductWithId.setName("Testproduct");
        updatedProductWithId.setDescription("This is a updated Testproduct");
        updatedProductWithId.setQuantity(10);
        updatedProductWithId.setPrice(99.99);
        updatedProductWithId.setProductPicture(testProductPicture);

        when(this.productService.updateProduct(productId, updatedProduct)).thenReturn(updatedProductWithId);

        Product expectedProduct = new Product();
        expectedProduct.setId(productId);
        expectedProduct.setName("Testproduct");
        expectedProduct.setDescription("This is a updated Testproduct");
        expectedProduct.setQuantity(10);
        expectedProduct.setPrice(99.99);
        expectedProduct.setProductPicture(testProductPicture);

        String expectedHateoasProductJson = objectMapper.writeValueAsString(HateoasUtilities.buildProductEntity(expectedProduct));

        this.mockMvc.perform(put(String.format("/v1/products/%d", productId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedProductJson))
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(content().json(expectedHateoasProductJson))
                .andExpect(status().isOk());
    }

    @Test
    void postProduct() throws Exception {
        Product actualProduct = RandomData.RandomProductWithoutId();
        String actualProductJson = objectMapper.writeValueAsString(actualProduct);

        Product expectedProduct = (Product) actualProduct.clone();
        expectedProduct.setId(1L);
        String expectedProductJson = objectMapper.writeValueAsString(HateoasUtilities.buildProductEntity(expectedProduct));

        when(this.productService.createProduct(actualProduct)).thenReturn(expectedProduct);

        this.mockMvc.perform(post("/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(actualProductJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(content().json(expectedProductJson));
    }

    @Test
    void deleteProductByIdTest() throws Exception {
        Product product = RandomData.RandomProductWithoutId();
        product.setId(1L);
        product.getProductPicture().setId(1L);

        Long productId  = product.getId();

        when(this.productService.readProductById(productId)).thenReturn(product);
        doNothing().when(this.productService).deleteProductById(productId);
        doNothing().when(this.productPictureService).deleteProductPictureById(productId);

        this.mockMvc.perform(delete(String.format("/v1/products/%d", productId)))
                .andExpect(status().isOk());
    }

}