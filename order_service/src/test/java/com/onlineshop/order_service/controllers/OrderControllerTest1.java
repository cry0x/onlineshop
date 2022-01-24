/*package com.onlineshop.order_service.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshop.order_service.entity.Order;
import com.onlineshop.order_service.service.OrderService;
import com.onlineshop.order_service.testUtilities.RandomData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests OrderController and with that a large amount of the whole service
 *
 * @author Simon Spang

@SpringBootTest({"eureka.client.enabled:false"})
@AutoConfigureMockMvc
public class OrderControllerTest1 {

    @Autowired
    private MockMvc mockMvc;

    private OrderService orderService;
    private static ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll() {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Test
    void getAllCustomersTest() throws Exception {
        List<Order> OrderList = RandomData.RandomOrderList(15);

        when(this.orderService.getAllOrders()).thenReturn(OrderList);

        this.mockMvc.perform(get("/v1/orders")).andExpect(status().isOk());
    }
}
*/