package com.onlineshop.order_service.service;

import com.onlineshop.order_service.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public boolean existsCustomerByRealId(Long realCustomerId) {
        return this.customerRepository.existsCustomerByRealId(realCustomerId);
    }

}
