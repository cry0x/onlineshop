package com.onlineshop.customer_service.service;

import java.util.List;

import com.onlineshop.customer_service.entity.Customer;
import com.onlineshop.customer_service.exception.CustomerIdMismatchException;
import com.onlineshop.customer_service.exception.CustomerNotFoundException;
import com.onlineshop.customer_service.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // fetch all customers
    public List<Customer> allcustomers() {
        return customerRepository.findAll();
    }

    // fetch customer by id
    public Customer find(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
    }

    // create customers
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    
    // delete customers
    // TODO: ADD EXCEPTION IF ORDERS EXIST
    public void delete(long id){
        customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
        customerRepository.deleteById(id);
    }

    // update customers
    public Customer updatecustomer(Customer customer, Long id) {
        if (customer.getId() != id) {
            throw new CustomerIdMismatchException();
        }
        customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
        return customerRepository.save(customer);
    }
}
