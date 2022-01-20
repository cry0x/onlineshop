package com.onlineshop.customer_service.service;

import java.util.List;

import com.onlineshop.customer_service.client.OrderServiceClient;
import com.onlineshop.customer_service.entity.Customer;
import com.onlineshop.customer_service.exception.CustomerIdMismatchException;
import com.onlineshop.customer_service.exception.CustomerNotFoundException;
import com.onlineshop.customer_service.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderServiceClient orderClient;

    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           OrderServiceClient orderClient) {
        this.customerRepository = customerRepository;
        this.orderClient = orderClient;
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
        if (!checkOrders(id)){
            customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
            customerRepository.deleteById(id);
        } else {
            System.out.println("There are still open orders on this customer!");
        }
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

    // check if there are open orders
    public boolean checkOrders(Long customerId){
        return this.orderClient.getCustomerOrders(customerId);
    }
}
