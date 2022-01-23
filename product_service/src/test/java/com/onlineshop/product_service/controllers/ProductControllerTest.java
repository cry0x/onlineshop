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

import java.util.List;

import static org.mockito.Mockito.*;
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
    void getProductHateoasTest() throws Exception {
        Long requestProductId = 1L;

        Product expectedProduct = new Product();
        expectedProduct.setId(1L);
        expectedProduct.setName("Testproduct_1");
        expectedProduct.setDescription("Das ist ein Testproduct mit der ID 1");
        expectedProduct.setPrice(9.99);
        expectedProduct.setQuantity(5);
        expectedProduct.setProductPicture(testProductPicture);

        when(this.productService.readProductById(requestProductId)).thenReturn(expectedProduct);

        EntityModel<Product> productPictureEntityModel = HateoasUtilities.buildProductEntity(expectedProduct);

        this.mockMvc.perform(get("/v1/products/hateoas/1"))
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
    void postProductHateoasTest() throws Exception {
        Product actualProduct = RandomData.RandomProductWithoutId();
        String actualProductJson = objectMapper.writeValueAsString(actualProduct);

        Product expectedProduct = (Product) actualProduct.clone();
        expectedProduct.setId(1L);
        String expectedProductJson = objectMapper.writeValueAsString(HateoasUtilities.buildProductEntity(expectedProduct));

        when(this.productService.createProduct(actualProduct)).thenReturn(expectedProduct);

        this.mockMvc.perform(post("/v1/products/hateoas")
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

    @Test
    void getAllProductsHateoasTest() throws Exception {
        List<Product> productList = RandomData.RandomProductList(15);

        when(this.productService.readAllProducts()).thenReturn(productList);

        this.mockMvc.perform(get("/v1/products/hateoas"))
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void postProductNewPictureHateoasTest() throws Exception {
        Product actualProduct = RandomData.RandomProductWithoutIdAndPicture();
        String actualProductJson = objectMapper.writeValueAsString(actualProduct);

        ProductPicture newProductPictureWithoutId = RandomData.RandomProductPictureWithoutId();
        ProductPicture newProductPicture = (ProductPicture) newProductPictureWithoutId.clone();
        newProductPicture.setId(1L);

        Product expectedProduct = (Product) actualProduct.clone();
        expectedProduct.setId(1L);
        expectedProduct.setProductPicture(newProductPicture);
        String expectedProductJson = objectMapper.writeValueAsString(HateoasUtilities.buildProductEntity(expectedProduct));

        when(this.productPictureService.createProductPicture(newProductPictureWithoutId)).thenReturn(newProductPicture);
        when(this.productService.createProduct(actualProduct)).thenReturn(expectedProduct);

        this.mockMvc.perform(post("/v1/products/hateoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(actualProductJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(content().json(expectedProductJson));
    }

    @Test
    void putProductNewPictureHateoasTest() throws Exception {
        Long id = 1L;
        Product newProductWithoutId = RandomData.RandomProductWithoutIdAndPicture();
        String newProductJson = objectMapper.writeValueAsString(newProductWithoutId);

        Product existingProduct = RandomData.RandomProduct();

        when(this.productService.readProductById(id)).thenReturn(existingProduct);

        ProductPicture newProductPictureWithoutId = RandomData.RandomProductPictureWithoutId();
        ProductPicture newProductPicture = (ProductPicture)newProductPictureWithoutId.clone();
        newProductPicture.setId(id);

        when(this.productPictureService.createProductPicture(newProductPictureWithoutId)).thenReturn(newProductPicture);

        Product newProduct = (Product) newProductWithoutId.clone();
        newProduct.setId(id);
        newProduct.setProductPicture(newProductPicture);

        when(this.productService.updateProduct(id, newProductWithoutId)).thenReturn(newProduct);

        String expectedHateoasProductJson = objectMapper.writeValueAsString(HateoasUtilities.buildProductEntity(newProduct));

        this.mockMvc.perform(put(String.format("/v1/products/hateoas/%d", id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newProductJson))
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(content().json(expectedHateoasProductJson))
                .andExpect(status().isOk());
    }

    @Test
    void putProductHateoasTest() throws Exception {
        Product existingProduct = RandomData.RandomProduct();

        Product newProduct = RandomData.RandomProductWithoutId();
        newProduct.setName(existingProduct.getName());
        String newProductJson = objectMapper.writeValueAsString(newProduct);

        Product expectedProduct = (Product) newProduct.clone();
        expectedProduct.setId(expectedProduct.getId());
        String expectedProductJson = objectMapper.writeValueAsString(expectedProduct);

        when(this.productService.updateProduct(existingProduct.getId(), newProduct)).thenReturn(expectedProduct);

        this.mockMvc.perform(put(String.format("/v1/products/hateoas/%d", existingProduct.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newProductJson))
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(content().json(expectedProductJson))
                .andExpect(status().isOk());
    }

    @Test
    void postProductTest() throws Exception {
        Product actualProduct = RandomData.RandomProductWithoutId();
        String actualProductJson = objectMapper.writeValueAsString(actualProduct);

        Product expectedProduct = (Product) actualProduct.clone();
        expectedProduct.setId(1L);
        String expectedProductJson = objectMapper.writeValueAsString(expectedProduct);

        when(this.productService.createProduct(actualProduct)).thenReturn(expectedProduct);

        this.mockMvc.perform(post("/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(actualProductJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedProductJson));
    }

    @Test
    void postProductNewPictureTest() throws Exception {
        Product actualProduct = RandomData.RandomProductWithoutIdAndPicture();
        String actualProductJson = objectMapper.writeValueAsString(actualProduct);

        ProductPicture newProductPictureWithoutId = RandomData.RandomProductPictureWithoutId();
        ProductPicture newProductPicture = (ProductPicture) newProductPictureWithoutId.clone();
        newProductPicture.setId(1L);

        Product expectedProduct = (Product) actualProduct.clone();
        expectedProduct.setId(1L);
        expectedProduct.setProductPicture(newProductPicture);
        String expectedProductJson = objectMapper.writeValueAsString(expectedProduct);

        when(this.productPictureService.createProductPicture(newProductPictureWithoutId)).thenReturn(newProductPicture);
        when(this.productService.createProduct(actualProduct)).thenReturn(expectedProduct);

        this.mockMvc.perform(post("/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(actualProductJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(expectedProductJson));
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

        this.mockMvc.perform(get("/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedProduct)));
    }

    @Test
    void getAllProductsTest() throws Exception {
        List<Product> productList = RandomData.RandomProductList(15);

        when(this.productService.readAllProducts()).thenReturn(productList);

        this.mockMvc.perform(get("/v1/products"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void putProductTest() throws Exception {
        Product existingProduct = RandomData.RandomProduct();

        Product newProduct = RandomData.RandomProductWithoutId();
        newProduct.setName(existingProduct.getName());
        String newProductJson = objectMapper.writeValueAsString(newProduct);

        Product expectedProduct = (Product) newProduct.clone();
        expectedProduct.setId(expectedProduct.getId());
        String expectedProductJson = objectMapper.writeValueAsString(expectedProduct);

        when(this.productService.updateProduct(existingProduct.getId(), newProduct)).thenReturn(expectedProduct);

        this.mockMvc.perform(put(String.format("/v1/products/%d", existingProduct.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newProductJson))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(expectedProductJson))
                .andExpect(status().isOk());
    }

    @Test
    void putChangeProductQuantityAddTest() throws Exception {
        Product product = RandomData.RandomProduct();

        Product productAddedQuantity = (Product) product.clone();
        productAddedQuantity.setQuantity(product.getQuantity() + 1);

        when(this.productService.changeQuantity(product.getId(), 1)).thenReturn(productAddedQuantity);

        this.mockMvc.perform(put(String.format("/v1/products/%d/quantity/%d", product.getId(), 1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(objectMapper.writeValueAsString(productAddedQuantity)));
    }

    @Test
    void putChangeProductQuantityReduceTest() throws Exception {
        Product product = RandomData.RandomProduct();

        Product productAddedQuantity = (Product) product.clone();
        productAddedQuantity.setQuantity(product.getQuantity() - 1);

        when(this.productService.changeQuantity(product.getId(), -1)).thenReturn(productAddedQuantity);

        this.mockMvc.perform(put(String.format("/v1/products/%d/quantity/%d", product.getId(), -1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(objectMapper.writeValueAsString(productAddedQuantity)));
    }

    @Test
    void putOrderProductTest() throws Exception {
        Product productInDb = RandomData.RandomProduct();

        Product expectedProduct = (Product) productInDb.clone();
        expectedProduct.setQuantity(2);

        this.mockMvc.perform(put(String.format("/v1/products/%d/%d", productInDb.getId(), expectedProduct.getQuantity()))).
                andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedProduct)));
    }

}
