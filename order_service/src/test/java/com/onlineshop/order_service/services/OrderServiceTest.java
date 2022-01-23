package com.onlineshop.order_service.services;

import com.onlineshop.order_service.Exceptions.NegativeOrderTotalAmountException;
import com.onlineshop.order_service.Exceptions.NegativeProductPriceException;
import com.onlineshop.order_service.Exceptions.NegativeProductQuantityException;
import com.onlineshop.order_service.Exceptions.OrderNotFoundException;
import com.onlineshop.order_service.clients.IProductServiceClient;
import com.onlineshop.order_service.entity.Order;
import com.onlineshop.order_service.entity.Product;
import com.onlineshop.order_service.entity.StatusEnum;
import com.onlineshop.order_service.repository.IOrderRepository;
import com.onlineshop.order_service.repository.IProductRepository;
import com.onlineshop.order_service.service.OrderService;
import com.onlineshop.order_service.testUtilities.RandomData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest({"eureka.client.enabled:false"})
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    private IOrderRepository iOrderRepository;
    private IProductRepository iProductRepository;
    private IProductServiceClient iProductServiceClient;

    @Test
    void createOrder() {
        Long orderId = 1L;

        Order newOrder = new Order();
        List<Product> productList = new ArrayList<>();

        newOrder.setOrderStatus(StatusEnum.PENDING);
        newOrder.setCustomerId(1234);
        newOrder.setCustomerEmail("simon.spang@web.de");
        productList.add(createProduct(99, 1.05, 432));
        productList.add(createProduct(12, 29.99, 555));
        productList.add(createProduct(4, 89.76, 9908));
        newOrder.setProductListInOrder(productList);

        Order expectedOrder = new Order();
        expectedOrder.setId(orderId);
        expectedOrder.setOrderStatus(StatusEnum.PENDING);
        expectedOrder.setCustomerId(1234);
        expectedOrder.setCustomerEmail("simon.spang@web.de");
        expectedOrder.setProductListInOrder(productList);

        when(this.iOrderRepository.save(newOrder)).thenReturn(expectedOrder);

        assertEquals(expectedOrder, this.orderService.createOrder(newOrder));
    }

    public Product createProduct(long quantity, double price, long originalId) {
        Product product = new Product();
        product.setQuantity(quantity);
        product.setPrice(price);
        product.setOriginalId(originalId);

        return product;
    }

    @Test
    void getAllOrdersTest() {
        List<Order> expectedOrderList = RandomData.RandomOrderList(10);
        when(this.iOrderRepository.findAll()).thenReturn(expectedOrderList);
        assertEquals(expectedOrderList, this.orderService.getAllOrders());
    }

    @Test
    void deleteOrderTest() {
        Long orderId = 1L;
        when(this.iOrderRepository.existsById(orderId)).thenReturn(false);
        assertThrows(OrderNotFoundException.class, () -> this.orderService.deleteOrder(orderId));
    }

    @Test
    void validateNegativeProductQuantityTest() {
        Product product = RandomData.RandomProduct();
        product.setQuantity(-1);

        assertThrows(NegativeProductQuantityException.class, () -> this.orderService.validateProduct(product));
    }

    @Test
    void validateNegativeProductPriceTest() {
        Product product = RandomData.RandomProduct();
        product.setPrice(-1);

        assertThrows(NegativeProductPriceException.class, () -> this.orderService.validateProduct(product));
    }

    @Test
    void validateNegativeOrderTotalAmountTest() {
        Order order = RandomData.RandomOrder();
        order.setTotalAmount(-1);

        assertThrows(NegativeOrderTotalAmountException.class, () -> this.orderService.validateOrder(order));
    }

    // TODO validateProductOriginalId not 0 or negative value
    // TODO price of a product can be 0 --> Rabattartikel / Bonus etc.
    // TODO validate CustomerId and CustomerEmail in order








}



