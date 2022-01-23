package com.onlineshop.order_service.repository;

import com.onlineshop.order_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {
<<<<<<< HEAD

    List<Order> findByCustomerId(Long customerId);

=======
>>>>>>> main
}

