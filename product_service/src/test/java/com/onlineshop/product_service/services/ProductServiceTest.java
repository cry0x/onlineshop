package com.onlineshop.product_service.services;

import com.onlineshop.product_service.clients.IOrderServiceClient;
import com.onlineshop.product_service.entities.Product;
import com.onlineshop.product_service.entities.ProductPicture;
import com.onlineshop.product_service.exceptions.*;
import com.onlineshop.product_service.testUtilities.RandomData;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import com.onlineshop.product_service.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    private IOrderServiceClient iOrderServiceClient;
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
        when(this.iOrderServiceClient.getIsProductInOrders(oldProduct.getId())).thenReturn(false);

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
        Product updatedProduct = RandomData.RandomProductWithoutId();
        Product unchangedProduct = RandomData.RandomProduct();
        Long productId = unchangedProduct.getId();

        when(this.iProductRepository.findById(productId)).thenReturn(Optional.of(unchangedProduct));
        when(this.orderService.existsProductInOrder(productId)).thenReturn(true);
        Product updatedProductWithId = (Product) updatedProduct.clone();
        updatedProductWithId.setId(RandomData.RandomLong());
        when(this.iProductRepository.save(updatedProduct)).thenReturn(updatedProductWithId);
        Product unchangedProductWithNewVersion = (Product) unchangedProduct.clone();
        unchangedProductWithNewVersion.setNewProductVersion(updatedProductWithId);

        assertEquals(updatedProductWithId, this.productService.updateProduct(productId, updatedProduct));
        verify(this.iProductRepository, times(1)).save(unchangedProductWithNewVersion);
    }

    @Test
    void validateProductPriceNegativeTest() {
        Product product = RandomData.RandomProduct();
        product.setPrice(-1);

        assertThrows(ProductPriceNegativeException.class, () -> this.productService.validateProduct(product));
    }

    @Test
    void validateProductQuantityNegativeTest() {
        Product product = RandomData.RandomProduct();
        product.setQuantity(-1);

        assertThrows(ProductQuantityNegativeException.class, () -> this.productService.validateProduct(product));
    }

    @Test
    void validateProductNameEmptyTest() {
        Product product = RandomData.RandomProduct();
        product.setName("");

        assertThrows(ProductNameEmptyException.class, () -> this.productService.validateProduct(product));
    }

    @Test
    void deleteProductThrowsProductExistsInOrderException() {
        Long productId = 1L;

        when(this.iProductRepository.existsById(productId)).thenReturn(true);
        when(this.orderService.existsProductInOrder(productId)).thenReturn(true);

        assertThrows(ProductExistsInOrderException.class, () -> this.productService.deleteProductById(productId));
    }

    @Test
    void deleteProductByIdTest() {
        Long productId = RandomData.RandomLong();

        when(this.iProductRepository.existsById(productId)).thenReturn(true);
        when(this.orderService.existsProductInOrder(productId)).thenReturn(false);

        this.productService.deleteProductById(productId);

        verify(this.iProductRepository, times(1)).deleteById(productId);
    }

}