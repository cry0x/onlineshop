package com.customer_service.customer_service.repository;

import com.customer_service.customer_service.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

// The CustomerRepository interface is very much self-explanatory. 
// We are extending the Spring Data JPA CrudRepository interface 
// to inherit several methods for manipulating Customer entities.
@Service
public interface CustomerRepository extends JpaRepository<Customer,Long> {
}