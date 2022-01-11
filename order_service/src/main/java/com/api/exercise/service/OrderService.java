package com.api.exercise.service;

import com.api.exercise.entity.Order;
import com.api.exercise.entity.Product;
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

    public Order getOrderById(Long id) {
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
        // Checks if order exists (error handling omitted)
        iOrderRepository.findById(id).get();

        return IOrderRepository.findOrderByOrderId(id);
    }

    public List<Order> getOrdersByCustomerId(Long customerId) {
        return IOrderRepository.findByCustomerId(customerId).orElseThrow();
    }

}

