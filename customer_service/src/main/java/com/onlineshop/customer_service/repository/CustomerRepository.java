package com.onlineshop.customer_service.repository;

import com.onlineshop.customer_service.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Interface to implement database methods.
 * We are extending the Spring Data JPA CrudRepository interface 
 * to inherit several methods for manipulating Customer entities.
 * @author Nico Welsch
 * @version 1.0
 */
@Service
public interface CustomerRepository extends JpaRepository<Customer,Long> {
}