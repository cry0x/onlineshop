package com.onlineshop.product_service;

import com.onlineshop.product_service.entities.Product;
import com.onlineshop.product_service.exceptions.ProdcutQuantityNegative;
import com.onlineshop.product_service.testUtilities.RandomData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest({"eureka.client.enabled:false"})
public class ProductTest {

    @Test
    void changeQuantityAddTest() throws CloneNotSupportedException {
        int amount = 312;

        Product product = RandomData.RandomProduct();

        Product expectedProduct = (Product) product.clone();
        expectedProduct.setQuantity(product.getQuantity() + amount);

        product.changeQuantity(amount);

        assertEquals(expectedProduct, product);
    }

    @Test
    void changeQuantityReduceTest() throws CloneNotSupportedException {
        int amount = -312;

        Product product = RandomData.RandomProduct();

        Product expectedProduct = (Product) product.clone();
        expectedProduct.setQuantity(product.getQuantity() + amount);

        product.changeQuantity(amount);

        assertEquals(expectedProduct, product);
    }

    @Test
    void changeQuantityThrowsProdcutQuantityMustNotBeNegativeTest() {
        Product product = RandomData.RandomProduct();
        product.setQuantity(1);

        assertThrows(ProdcutQuantityNegative.class, () -> product.changeQuantity(-2));
    }
}
