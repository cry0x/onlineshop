package com.api.exercise.repository;

import com.api.exercise.entity.Order;
import com.api.exercise.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IOrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrderByOrderId(Long orderId);

    List<Order> findByCustomerId(Long customerId);

    Long deleteByOrderId(Long orderId);

}

