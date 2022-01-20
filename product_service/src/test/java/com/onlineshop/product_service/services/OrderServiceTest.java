package com.onlineshop.product_service.services;

import com.onlineshop.product_service.clients.IOrderServiceClient;
import com.onlineshop.product_service.testUtilities.RandomData;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest({"eureka.client.enabled:false"})
public class OrderServiceTest {

    @Autowired
    @InjectMocks
    private OrderService orderService;

    @MockBean
    private IOrderServiceClient iOrderServiceClient;

    @Test
    public void existsProductInOrderTrueTest() {
        final Long productId = RandomData.RandomLong();

        when(this.iOrderServiceClient.getIsProductInOrders(productId)).thenReturn(true);

        assertTrue(this.orderService.existsProductInOrder(productId));
    }

    @Test
    public void existsProductInOrderFalseTest() {
        final Long productId = RandomData.RandomLong();

        when(this.iOrderServiceClient.getIsProductInOrders(productId)).thenReturn(false);

        assertFalse(this.orderService.existsProductInOrder(productId));
    }
}
