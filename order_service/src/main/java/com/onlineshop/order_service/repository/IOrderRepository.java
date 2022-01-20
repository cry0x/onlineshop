package com.onlineshop.order_service.repository;

import com.onlineshop.order_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {

//    Order findOrderByOrderId(Long orderId);

//    List<Order> findByCustomerId(Long customerId);

//    Long deleteByOrderId(Long orderId);

}

