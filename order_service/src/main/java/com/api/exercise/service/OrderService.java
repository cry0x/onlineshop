package com.api.exercise.service;

import com.api.exercise.entity.Order;
import com.api.exercise.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional

@Service
public class OrderService {

    @Autowired
    private final IOrderRepository iOrderRepository;



    public Order getOderById(Long id) {
        return iOrderRepository.findById(id).get();
    }

    public Order createOrder(Order order) {
        return IOrderRepository.save(order);
    }

    public Order updateOrder(Long id, Order order) {
        order.setOrderId(id);
        return iOrderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        iOrderRepository.deleteById(id);
    }

    public List<Product> getProductsByOrderId(Long id) {
        // Checks if book exists (error handling omitted)
        iOrderRepository.findById(id).get();

        return IOrderRepository.findOrderByOrderId(id);
    }

    public List<Order> getOrdersByCustomerId(Long customerId) {
        // Checks if review for this book exists (error handling omitted)
        return IOrderRepository.findByCustomerId(customerId).orElseThrow();
    }

    // Genauer anschauen!!!!!!!! TODO
    public Order updateOrder(Long orderId, List<Product> productList) {
        // Checks if review for this book exists (error handling omitted)
        IOrderRepository.findByOrderIdAndProductId(orderId, productId).orElseThrow();

        var book = bookRepository.findById(bookId).get();

        review.setBook(book);
        review.setId(reviewId);
        return reviewRepository.save(review);
    }

    /*
    public Review createReviewForBook(Long bookId, Review review) {
        var book = bookRepository.findById(bookId).get();
        review.setBook(book);
        reviewRepository.save(review);
        return review;
    }*/

}

