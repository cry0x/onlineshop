package com.onlineshop.order_service.repository;

import com.onlineshop.order_service.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT Customer c FROM Orders WHERE c.original_id = :realCustomerId")
    boolean existsCustomerByRealId(@Param("realCustomerId") Long realCustomerId);

}

