package com.onlineshop.order_service.controller;

import com.onlineshop.order_service.dto.OrderDto;
import com.onlineshop.order_service.dto.ProductDto;
import com.onlineshop.order_service.entity.Order;
import com.onlineshop.order_service.entity.Product;
import com.onlineshop.order_service.service.OrderService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private static final ModelMapper modelMapper = new ModelMapper();


    private OrderService orderService;
    private Order orderEntity;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public OrderController(Order orderEntity){
        this.orderEntity = orderEntity;
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable(value="id") Long id) {
        log.info("Order (id: {}) has been requested.", id);
        orderEntity = orderService.getOrderById(id);
        return modelMapper.map(orderEntity, Order.class);
    }

    @GetMapping("")
    public List<Order> getAllOrders() {
        log.info("Getting all orders in a list...");
        return orderService.getAllOrders();
    }

    @PostMapping("")
    public OrderDto createOrder(@RequestBody OrderDto orderDto) {
        log.info("Creating a new order.");
        orderEntity = modelMapper.map(orderDto, Order.class);
        orderEntity.setProductListInOrder(this.orderService.createAllProducts(orderEntity.getProductListInOrder()));
        Order newOrder = orderService.createOrder(orderEntity);
        return modelMapper.map(newOrder, OrderDto.class);
    }

    // Not needed?! Add, remove, update product is enough
    @PutMapping("/{id}")
    public OrderDto updateOrder(@PathVariable(value="id") Long id, @RequestBody OrderDto order) {
        log.info("Updating order (id: {}).", id);
        orderEntity = modelMapper.map(order, Order.class);
        return modelMapper.map(orderService.updateOrder(id, orderEntity), OrderDto.class);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable(value="id") Long id) {
        log.info("Deleting order (id: {}).", id);
        orderService.deleteOrder(id);
    }

    @GetMapping("/orders/{customerId}")
    public List<Order> getAllOrdersByCustomerId(@PathVariable(value="customerId") Long id) {
        log.info("All orders of customer (customer id: {}) have been requested.", id);
        List<Order> orders = orderService.getOrdersByCustomerId(id);
        return modelMapper.map(orders, new TypeToken<List<Order>>() {}.getType());
    }

    @PostMapping("/products/{id}")
    public List<ProductDto> createAndAddProduct(@PathVariable(value="id") Long id, @RequestBody ProductDto productDto) {
        log.info("Creating a new product and adding it to order (id: {}).", id);
        Product productEntity = modelMapper.map(productDto, Product.class);
        productEntity.setOriginalId(productDto.getId());
        List<Product> newProducts = this.orderService.addProductToOrder(id, productEntity);
        return modelMapper.map(newProducts, new TypeToken<List<ProductDto>>() {}.getType());
    }

    @DeleteMapping("/{order_id}/{product_id}")
    public void deleteProductInOrder(@PathVariable(value="order_id") Long orderId, @PathVariable(value="product_id") Long productId) {
        log.info("Deleting product (id: {}) from order (id: {}.", productId, orderId);
        orderService.deleteProductInOrder(orderId, productId);
    }

}

