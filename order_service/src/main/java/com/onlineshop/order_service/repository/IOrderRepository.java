package com.onlineshop.order_service.repository;

import com.onlineshop.order_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderRepository extends JpaRepository<Order, Long> {

    Order findOrderByOrderId(Long orderId);

    List<Order> findByCustomerId(Long customerId);

    Long deleteByOrderId(Long orderId);

}

