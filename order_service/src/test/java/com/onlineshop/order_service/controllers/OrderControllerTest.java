package com.onlineshop.order_service.controllers;

import com.onlineshop.order_service.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;<<<<<<< HEAD
public class OrderControllerTest {


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static status;
import static when;

@SpringBootTest({"eureka.client.enabled:false"})
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;

    @Test
    public void getExistsProductByRealIdTrueTest() throws Exception {
        Long productId = 12345L;

        when(this.orderService.existsProductByRealId(productId)).thenReturn(true);

        this.mockMvc.perform(get(String.format("/v1/orders/orders/products/%d", productId)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void getExistsProductByRealIdFalseTest() throws Exception {
        Long productId = 12345L;

        when(this.orderService.existsProductByRealId(productId)).thenReturn(false);

        this.mockMvc.perform(get(String.format("/v1/orders/orders/products/%d", productId)))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
>>>>>>> main
}
