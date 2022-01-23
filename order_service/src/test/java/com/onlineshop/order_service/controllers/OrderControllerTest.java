package com.onlineshop.order_service.controllers;

<<<<<<< HEAD
public class OrderControllerTest {
=======
import com.onlineshop.order_service.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

@SpringBootTest({"eureka.client.enabled:false"})
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;

    @Test
    public void getExistsProductByRealIdTrueTest() throws Exception {
        Long productId = 12345L;

        when(this.productService.existsProductByRealId(productId)).thenReturn(true);

        this.mockMvc.perform(get(String.format("/v1/orders/orders/products/%d", productId)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void getExistsProductByRealIdFalseTest() throws Exception {
        Long productId = 12345L;

        when(this.productService.existsProductByRealId(productId)).thenReturn(false);

        this.mockMvc.perform(get(String.format("/v1/orders/orders/products/%d", productId)))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
>>>>>>> main
}
