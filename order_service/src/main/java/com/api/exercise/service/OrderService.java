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

    private final IOrderRepository iOrderRepository;

    @Autowired
    public OrderService(IOrderRepository iOrderRepository) {
        this.iOrderRepository = iOrderRepository;
    }

    public Order getOrderById(Long id) {
        return this.iOrderRepository.findById(id).get();
    }

    public Order createOrder(Order order) {
        return this.iOrderRepository.save(order);
    }

    public Order updateOrder(Long id, Order order) {
        order.setOrderId(id);
        return this.iOrderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        this.iOrderRepository.deleteById(id);
    }

    public List<Product> getProductsByOrderId(Long id) throws Exception {
        if (this.iOrderRepository.existsById(id))
            throw new Exception(String.format("The order with Id: %d doesnt exist!", id));

        return this.iOrderRepository.findOrderByOrderId(id).getProductInformation();
    }

    public List<Order> getOrdersByCustomerId(Long customerId) {
        return this.iOrderRepository.findByCustomerId(customerId);
    }

}

