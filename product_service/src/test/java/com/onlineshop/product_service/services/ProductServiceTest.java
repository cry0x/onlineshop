package com.onlineshop.product_service.services;

import com.onlineshop.product_service.clients.IOrderServiceClient;
import com.onlineshop.product_service.entities.Product;
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
    void validateProductPriceNegativeTest() {
        Product product = RandomData.RandomProduct();
        product.setPrice(-1);

        assertThrows(ProductPriceNegativeException.class, () -> this.productService.validateProduct(product));
    }

    @Test
    void validateProductQuantityNegativeTest() {
        Product product = RandomData.RandomProduct();
        product.setQuantity(-1);

        assertThrows(ProdcutQuantityNegative.class, () -> this.productService.validateProduct(product));
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

    @Test
    void updateProductTest() throws CloneNotSupportedException {
        Product existingProduct = RandomData.RandomProduct();

        Product newProduct = RandomData.RandomProductWithoutId();

        Product expectedProduct = (Product) newProduct.clone();
        expectedProduct.setId(existingProduct.getId());
        expectedProduct.setName(existingProduct.getName());
        expectedProduct.getProductPicture().setId(RandomData.RandomLong());

        when(this.iProductRepository.findById(existingProduct.getId())).thenReturn(Optional.of(existingProduct));
        when(this.orderService.existsProductInOrder(existingProduct.getId())).thenReturn(false);
        when(this.productPictureService.updateProductPicture(existingProduct.getProductPicture().getId(), newProduct.getProductPicture())).thenReturn(expectedProduct.getProductPicture());
        when(this.iProductRepository.save(newProduct)).thenReturn(expectedProduct);

        assertEquals(expectedProduct, this.productService.updateProduct(existingProduct.getId(), newProduct));
    }

    @Test
    void updateProductNewPictureTest() throws CloneNotSupportedException {
        Product existingProduct = RandomData.RandomProduct();

        Product newProduct = RandomData.RandomProductWithoutId();

        Product expectedProduct = (Product) newProduct.clone();
        expectedProduct.setId(existingProduct.getId());
        expectedProduct.setName(existingProduct.getName());
        expectedProduct.setProductPicture(newProduct.getProductPicture());
        expectedProduct.getProductPicture().setId(existingProduct.getProductPicture().getId());

        when(this.iProductRepository.findById(existingProduct.getId())).thenReturn(Optional.of(existingProduct));
        when(this.orderService.existsProductInOrder(existingProduct.getId())).thenReturn(false);
        when(this.productPictureService.updateProductPicture(existingProduct.getProductPicture().getId(), newProduct.getProductPicture())).thenReturn(newProduct.getProductPicture());
        when(this.iProductRepository.save(newProduct)).thenReturn(expectedProduct);

        assertEquals(expectedProduct, this.productService.updateProduct(existingProduct.getId(), newProduct));
    }

    @Test
    void updateProductInOrderTest() throws CloneNotSupportedException {
        Product existingProduct = RandomData.RandomProduct();

        Product newProduct = RandomData.RandomProductWithoutId();
        Product expectedProduct = (Product) newProduct.clone();

        when(this.iProductRepository.findById(existingProduct.getId())).thenReturn(Optional.of(existingProduct));
        when(this.orderService.existsProductInOrder(existingProduct.getId())).thenReturn(true);
        when(this.iProductRepository.save(newProduct)).thenReturn(expectedProduct);

        existingProduct.setNewProductVersion(expectedProduct);

        assertEquals(expectedProduct, this.productService.updateProduct(existingProduct.getId(), newProduct));

        verify(this.iProductRepository, times(1)).save(existingProduct);
    }

    @Test
    void changeQuantityAddTest() throws CloneNotSupportedException {
        int amount = 123;
        Product product = RandomData.RandomProduct();

        Product expectedProdcut = (Product) product.clone();
        expectedProdcut.setQuantity(product.getQuantity() + amount);

        when(this.iProductRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(this.iProductRepository.save(expectedProdcut)).thenReturn(expectedProdcut);

        assertEquals(expectedProdcut, this.productService.changeQuantity(product.getId(), amount));
    }

    @Test
    void changeQuantityReduceTest() throws CloneNotSupportedException {
        int amount = -123;
        Product product = RandomData.RandomProduct();

        Product expectedProdcut = (Product) product.clone();
        expectedProdcut.setQuantity(product.getQuantity() + amount);

        when(this.iProductRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(this.iProductRepository.save(expectedProdcut)).thenReturn(expectedProdcut);

        assertEquals(expectedProdcut, this.productService.changeQuantity(product.getId(), amount));
    }

}