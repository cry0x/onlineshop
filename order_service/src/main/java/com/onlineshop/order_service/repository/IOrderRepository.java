package com.onlineshop.order_service.repository;

import com.onlineshop.order_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This interface is used for implementing methods used for database access of Orders, extending the Spring data
 * JPARepository interface.
 * @author Simon Spang
 */
@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM Orders WHERE customer_id = :customerId", nativeQuery = true)
    List<Order> findByCustomerId(@Param("customerId") Long customerId);

    @Query(value = "SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM Orders o WHERE o.customer_id = :customerId", nativeQuery = true)
    boolean getCustomerHasOrders(@Param("customerId") Long customerId);

}

