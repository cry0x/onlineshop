package com.onlineshop.product_service.services;

import com.onlineshop.product_service.entities.Product;
import com.onlineshop.product_service.entities.ProductPicture;
import com.onlineshop.product_service.testUtilities.RandomData;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import com.onlineshop.product_service.exceptions.ProductDoesntExistsException;
import com.onlineshop.product_service.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest({"eureka.client.enabled:false"})
class ProductServiceTest {

    @Autowired
    @InjectMocks
    private ProductService productService;

    @MockBean
    private IProductRepository iProductRepository;
    @MockBean
    private ProductPictureService productPictureService;
    @MockBean
    private OrderService orderService;

    @Test
    void createProduct() {
        Long productId = 1L;

        Product newProduct = new Product();
        newProduct.setName("Table");
        newProduct.setDescription("Black table made of wood");
        newProduct.setPrice(129.99);
        newProduct.setQuantity(4);

        Product expectedProduct = new Product();
        expectedProduct.setId(productId);
        expectedProduct.setName("Table");
        expectedProduct.setDescription("Black table made of wood");
        expectedProduct.setPrice(129.99);
        expectedProduct.setQuantity(4);

        when(this.iProductRepository.save(newProduct)).thenReturn(expectedProduct);

        assertEquals(expectedProduct, this.productService.createProduct(newProduct));
    }

    @Test
    void updateProduct() {
        Long productId = 1L;

        when(this.iProductRepository.existsById(productId)).thenReturn(true);

        ProductPicture oldProductPicture = new ProductPicture();
        oldProductPicture.setId(1L);
        oldProductPicture.setName("ProductPicture");
        oldProductPicture.setData(RandomData.RandomByteArray(20));

        Product oldProduct = new Product();
        oldProduct.setId(productId);
        oldProduct.setName("Product");
        oldProduct.setDescription("This is a Product");
        oldProduct.setPrice(99.99);
        oldProduct.setQuantity(100);
        oldProduct.setProductPicture(oldProductPicture);

        when(this.iProductRepository.findById(productId)).thenReturn(Optional.of(oldProduct));
        when(this.orderService.checkProductInOrder(oldProduct)).thenReturn(false);

        byte[] newProductPictureData = RandomData.RandomByteArray(20);

        ProductPicture newProductPicture = new ProductPicture();
        newProductPicture.setName("Updated ProductPicture");
        newProductPicture.setData(newProductPictureData);

        Product newProduct = new Product();
        newProduct.setName("Updated Product");
        newProduct.setDescription("This is a updated Product");
        newProduct.setPrice(9.99);
        newProduct.setQuantity(10);
        newProduct.setProductPicture(newProductPicture);

        ProductPicture expectedProductPicture = new ProductPicture();
        expectedProductPicture.setId(1L);
        expectedProductPicture.setName("Updated ProductPicture");
        expectedProductPicture.setData(newProductPictureData);

        when(this.productPictureService.updateProductPicture(oldProduct.getProductPicture().getId(), newProductPicture)).thenReturn(expectedProductPicture);


        Product expectedProduct = new Product();
        expectedProduct.setId(1L);
        expectedProduct.setName("Product");
        expectedProduct.setDescription("This is a updated Product");
        expectedProduct.setPrice(9.99);
        expectedProduct.setQuantity(10);
        expectedProduct.setProductPicture(expectedProductPicture);

        when(this.iProductRepository.save(expectedProduct)).thenReturn(expectedProduct);

        assertEquals(expectedProduct, this.productService.updateProduct(productId, newProduct));
    }

    @Test
    void updateProductThrowsProductDoesntExistsException() {
        Long productId = 1L;

        Product newProduct = new Product();
        newProduct.setName("Table");
        newProduct.setDescription("Red table made of wood");
        newProduct.setPrice(229.99);
        newProduct.setQuantity(6);

        when(this.iProductRepository.existsById(productId)).thenReturn(false);

        assertThrows(ProductDoesntExistsException.class, () -> this.productService.updateProduct(productId, newProduct));
    }

    @Test
    void deleteProductThrowsProductDoesntExistsException() {
        Long productId = 1L;

        when(this.iProductRepository.existsById(productId)).thenReturn(false);

        assertThrows(ProductDoesntExistsException.class, () -> this.productService.deleteProductById(productId));
    }

    @Test
    void readAllProductsTest() {
        List<Product> expectedProductList = RandomData.RandomProductList(15);

        when(this.iProductRepository.findAll()).thenReturn(expectedProductList);

        assertEquals(expectedProductList, this.productService.readAllProducts());
    }

    @Test
    void updateProductInOrder() throws CloneNotSupportedException {
        Product existingProduct = RandomData.RandomProduct();
        Product updatedProduct = RandomData.RandomProductWithoutId();

        Product expectedProduct = (Product) updatedProduct.clone();
        expectedProduct.setId(RandomData.RandomLong());
        expectedProduct.setName(existingProduct.getName());

        when(this.iProductRepository.existsById(existingProduct.getId())).thenReturn(true);
        when(this.iProductRepository.findById(existingProduct.getId())).thenReturn(Optional.of(existingProduct));
        when(this.orderService.checkProductInOrder(existingProduct)).thenReturn(true);
        when(this.iProductRepository.save(updatedProduct)).thenReturn(expectedProduct);

        Product existingProductWithNewVersion = (Product) existingProduct.clone();
        existingProductWithNewVersion.setNewProductVersion(expectedProduct);
        when(this.iProductRepository.save(existingProductWithNewVersion)).thenReturn(existingProductWithNewVersion);

        assertEquals(expectedProduct, this.productService.updateProduct(existingProduct.getId(), updatedProduct));
    }

}