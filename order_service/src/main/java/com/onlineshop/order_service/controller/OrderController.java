package com.onlineshop.order_service.controller;

import java.util.List;

import com.onlineshop.order_service.dto.OrderDto;
import com.onlineshop.order_service.entity.Order;
import com.onlineshop.order_service.service.CustomerService;
import com.onlineshop.order_service.service.OrderService;
import com.onlineshop.order_service.service.ProductService;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private OrderService orderService;
    @Autowired
    private static ProductService productService;
    @Autowired
    private static CustomerService customerService;

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable(value="id") Long orderId) {
        log.info("Order '{}' has been requested.", orderId);
        var orderEntity = orderService.getOrderById(orderId);
        var orderDto = modelMapper.map(orderEntity, Order.class);
        return orderDto;
    }

    @PostMapping
    public OrderDto postOrder(@RequestBody OrderDto order) {
        log.info("Creating new order.");
        var orderEntity = modelMapper.map(order, Order.class);
        return modelMapper.map(orderService.createOrder(orderEntity), OrderDto.class);
    }

    @PutMapping("/{id}")
    public OrderDto updateOrder(@PathVariable(value="id") Long id, @RequestBody OrderDto order) {
        log.info("Updating order '{}'.", id);
        var orderEntity = modelMapper.map(order, Order.class);
        var returnDto = modelMapper.map(orderService.updateOrder(id, orderEntity), OrderDto.class);
        return returnDto;
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable(value="id") Long id) {
        log.info("Deleting order '{}'.", id);
        orderService.deleteOrder(id);
    }

    @GetMapping("/{id}/orders")
    public List<Order> getAllOrders(@PathVariable(value="id") Long id) {
        log.info("All orders '{}' have been requested.", id);
        var orders = orderService.getOrderById(id);
        return modelMapper.map(orders, new TypeToken<List<Order>>() {}.getType());
    }

    @GetMapping("/orders/products/{realProductId}")
    public boolean getExistsProductByRealId(@PathVariable(value="realProductId") Long realProductId) {
        return this.productService.existsProductByRealId(realProductId);
    }

    @GetMapping("/orders/customers/{realCustomerId}")
    public boolean getExistsCustomerByRealId(@PathVariable(value="realCustomerId") Long realCustomerId) {
        return this.customerService.existsCustomerByRealId(realCustomerId);
    }

}

