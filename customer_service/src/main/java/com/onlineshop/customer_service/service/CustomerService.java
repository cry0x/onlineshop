package com.onlineshop.customer_service.service;

import java.util.List;

import com.onlineshop.customer_service.client.OrderServiceClient;
import com.onlineshop.customer_service.entity.Customer;
import com.onlineshop.customer_service.exception.CustomerIdMismatchException;
import com.onlineshop.customer_service.exception.CustomerNotFoundException;
import com.onlineshop.customer_service.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class in which the logiq of the methods later used in the CusomerController
 * is defined.
 * @author Nico Welsch
 * @version 1.0
 */
@Service
public class CustomerService {

    // create customer repository object
    private final CustomerRepository customerRepository;
    // create orderClient(FeignClient) object
    private final OrderServiceClient orderClient;

    /**
     * Initialize above created objects
     * @param customerRepository    Object of the CustomerRepository Interface
     * @param orderClient           Object of the OrderServiceClient Interface
     */
    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           OrderServiceClient orderClient) {
        this.customerRepository = customerRepository;
        this.orderClient = orderClient;
    }

    /**
     * Fetch all customers currently registered in the database
     * @return  list of customers
     */
    public List<Customer> allcustomers() {
        return customerRepository.findAll();
    }

    /**
     * Fetch a specified customer from the database
     * @param id    customerId of the customer
     * @return      customer
     */
    public Customer find(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
    }

    /**
    * Creates a customer and posts the object to the database
    * @param customer  customer to create
    * @return          customer
    */
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    
    /**
     * Deletes a specified customer if said customer doesn't have any
     * outstanding orders.
     * If there are orders left, return an error.
     * @param id    customer to delete
     */
    public void delete(long id){
        if (!checkOrders(id)){
            customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
            customerRepository.deleteById(id);
        } else {
            System.out.println("There are still open orders on this customer!");
        }
    }

    /**
     * Updates a already existing customer in the database.
     * @param customer  updated customer object
     * @param id        customer to update
     * @return          customer
     */
    public Customer updatecustomer(Customer customer, Long id) {
        if (customer.getId() != id) {
            throw new CustomerIdMismatchException();
        }
        customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
        return customerRepository.save(customer);
    }

    /**
     * Checks if the current customer has any outstanding orders.
     * @param customerId    customer to check
     * @return              true or false
     */
    public boolean checkOrders(Long customerId){
        return this.orderClient.getCustomerOrders(customerId);
    }
}
