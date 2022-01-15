package com.onlineshop.order_service.controller;

import com.onlineshop.order_service.dto.OrderDto;
import com.onlineshop.order_service.dto.ProductDto;
import com.onlineshop.order_service.entity.Order;
import com.onlineshop.order_service.entity.Product;
import com.onlineshop.order_service.service.OrderService;
import com.onlineshop.order_service.service.ProductService;
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

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable(value="id") Long id) {
        log.info("Order '{}' has been requested.", id);
        var orderEntity = orderService.getOrderById(id);
        var orderDto = modelMapper.map(orderEntity, Order.class);
        return orderDto;
    }
    // Testmapping
    @GetMapping("/hallo")
    public String test(){
        return "Hallo";
    }

    @PostMapping("")
    public OrderDto createOrder(@RequestBody OrderDto orderDto) {
        log.info("Creating a new order.");
        var orderEntity = modelMapper.map(orderDto, Order.class);
        var newOrder = orderService.createOrder(orderEntity);

        return modelMapper.map(newOrder, OrderDto.class);
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

    @GetMapping("/orders/{customerId}")
    public List<Order> getAllOrdersByCustomerId(@PathVariable(value="customerId") Long id) {
        log.info("All orders '{}' have been requested.", id);
        var orders = orderService.getOrdersByCustomerId(id);
        return modelMapper.map(orders, new TypeToken<List<Order>>() {}.getType());
    }

    @PostMapping("/products/")
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        log.info("Creating a new product for a order.");
        var productEntity = modelMapper.map(productDto, Product.class);
        var newProduct = productService.createProduct(productEntity);
        return modelMapper.map(newProduct, ProductDto.class);
    }



}

