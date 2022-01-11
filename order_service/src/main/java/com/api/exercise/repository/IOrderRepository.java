package com.api.exercise.repository;

import com.api.exercise.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IOrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrderByOrderId(Long orderId);

    List<Order> findByCustomerId(Long customerId);

    Long deleteByOrderId(Long orderId);

}

