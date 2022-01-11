package com.api.exercise.controller;

import com.api.exercise.dto.OrderDto;
import com.api.exercise.entity.Order;
import com.api.exercise.service.OrderService;
import org.modelmapper.ModelMapper;

import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    public Order getOrder(
            @PathVariable(value="id") Long orderId
    ) {
        log.info("Order '{}' has been requested.", orderId);
        var orderEntity = orderService.getOderById(orderId);
        var orderDto = modelMapper.map(orderEntity, OrderDto.class);
        return orderDto;
    }

    @PostMapping("")
    public OrderDto postOrder(
            @RequestBody OrderDto order
    ) {
        log.info("Creating new order.");
        var orderEntity = modelMapper.map(order, Order.class);
        return modelMapper.map(orderService.createOrder(orderEntity), OrderDto.class);
    }

    @PutMapping("/{id}")
    public OrderDto updateOrder(
            @PathVariable(value="id") Long id,
            @RequestBody OrderDto order
    ) {
        log.info("Updating order '{}'.", id);
        var orderEntity = modelMapper.map(order, Order.class);
        var returnDto = modelMapper.map(orderService.updateOrder(id, orderEntity), SpringDataJaxb.OrderDto.class);
        return returnDto;
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(
            @PathVariable(value="id") Long id
    ) {
        log.info("Deleting order '{}'.", id);
        orderService.deleteOrder(id);
    }

    @GetMapping("/{id}/orders")
    public List<Order> getAllOrders(
            @PathVariable(value="id") Long id
    ) {
        log.info("All orders '{}' have been requested.", id);
        var orders = orderService.getOderById(id);
        return modelMapper.map(orders, new TypeToken<List<Order>>() {}.getType());
    }
/*
    @GetMapping("/{bookId}/reviews/{reviewId}")
    public ReviewDto getReview(
            @PathVariable(value="bookId") Long bookId,
            @PathVariable(value="reviewId") Long reviewId
    ) {
        log.info("Review '{}' of Book '{}' has been requested.", reviewId, bookId);
        var review = bookService.getReviewByBookId(bookId, reviewId);
        return modelMapper.map(review, ReviewDto.class);
    }

    @PutMapping("/{bookId}/reviews/{reviewId}")
    public ReviewDto putReview(
            @PathVariable(value="bookId") Long bookId,
            @PathVariable(value="reviewId") Long reviewId,
            @RequestBody ReviewDto reviewDto
    ) {
        log.info("Updating Review '{}' of Book '{}'.", reviewId, bookId);
        var reviewEntity = modelMapper.map(reviewDto, Review.class);
        var updatedReview = bookService.updateReview(bookId, reviewId, reviewEntity);
        return modelMapper.map(updatedReview, ReviewDto.class);
    }

    @DeleteMapping("/{bookId}/reviews/{reviewId}")
    public void deleteReview(
            @PathVariable(value="bookId") Long bookId,
            @PathVariable(value="reviewId") Long reviewId
    ) {
        log.info("Deleting Review '{}' of Book '{}'.", reviewId, bookId);
        bookService.deleteReview(bookId, reviewId);
    }

    @PostMapping("/{id}/reviews")
    public ReviewDto postReview(
            @PathVariable(value="id") Long id,
            @RequestBody ReviewDto review
    ) {
        log.info("Reviews of Book with id '{}' has been requested.", id);
        var reviewAsEntity = modelMapper.map(review, Review.class);
        var entity = bookService.createReviewForBook(id, reviewAsEntity);
        return modelMapper.map(entity, ReviewDto.class);
    }*/
}

