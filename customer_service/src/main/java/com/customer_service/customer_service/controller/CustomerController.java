package com.customer_service.customer_service.controller;

import java.util.List;

import com.customer_service.customer_service.entity.Customer;
import com.customer_service.customer_service.exception.CustomerIdMismatchException;
import com.customer_service.customer_service.exception.CustomerNotFoundException;
import com.customer_service.customer_service.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController()
@RequestMapping(value = "/api/v1/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    // Basic API Documentation for fetching all customers from the DB
    @Operation(summary = "This is to fetch all customers stored in the DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Fetched all customers from the DB",
            content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
            description = "Not Available",
            content = @Content)
    })
    // fetch all customers
    @GetMapping("customer/")
    public List<Customer> allcustomers() {
        return customerRepository.findAll();
    }

    // fetch customer by id
    @Operation(summary = "This is to fetch a single customer from the DB specified by it's ID")
    @GetMapping("customer/{id}")
    public Customer find(@PathVariable Long id) {
        return customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
    }

    // create customers
    @Operation(summary = "This is to create a new customer and store it in the DB")
    @PostMapping("customer/")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer create(@RequestBody Customer customer)
    {
        return customerRepository.save(customer);
    }

    
    // delete customers
    // TODO: ADD EXCEPTION IF ORDERS EXIST
    @Operation(summary = "This is to delete a customer stored in the DB")
    @DeleteMapping("customer/{id}")
    public void delete(@PathVariable long id)
    {
        customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
        customerRepository.deleteById(id);
    }

    @Operation(summary = "This is to update a customer stored in the DB")
    // update customers
    @PutMapping("customer/{id}")
    public Customer updatecustomer(@RequestBody Customer customer, @PathVariable Long id) {
        if (customer.getId() != id) {
            throw new CustomerIdMismatchException();
        }
        customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
        return customerRepository.save(customer);
    }

}