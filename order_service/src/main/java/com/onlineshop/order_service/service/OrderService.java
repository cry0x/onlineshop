package com.onlineshop.order_service.service;

import com.onlineshop.order_service.entity.Order;
import com.onlineshop.order_service.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}

