package com.onlineshop.customer_service.controller;

import java.util.List;

import com.onlineshop.customer_service.entity.Customer;
import com.onlineshop.customer_service.service.CustomerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * Controller which enables the user to send REST Requests
 * by specifying the method adresses and behaviour.
 * @author Nico Welsch
 * @version 1.0
 */
@RestController()
@RequestMapping("/v1/customer")
public class CustomerController {

    // create customer service object
    private final CustomerService customerService;
    // create logging object
    private final static Logger log = LoggerFactory.getLogger(CustomerController.class);

    /**
     * Initialize customerService object
     * @param customerService Object of the CustomerService class.
     */
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Fetch all customers currently registered in the database
     * @return  list of customers
     */
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
    @GetMapping("customer/")
    public List<Customer> getAllCustomers() {
        log.info("FETCHING ALL CUSTOMERS...");
        return customerService.getAllCustomers();
    }

    /**
     * Fetch a specified customer from the database
     * @param id    customerId of the customer
     * @return      customer
     */
    @Operation(summary = "This is to fetch a single customer from the DB specified by it's ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
        description = "Fetched customer from the DB",
        content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "404",
            description = "Not Available",
            content = @Content)
    })
    @GetMapping("customer/{id}")
    public Customer getCustomer(@PathVariable Long id) {
        log.info("FETCHING CUSTOMER WITH ID '{}'...", id);
        return customerService.find(id);
    }

    /**
     * Creates a customer and posts the object to the database
     * @param customer  customer to create
     * @return          customer
     */
    @Operation(summary = "This is to create a new customer and store it in the DB")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
        description = "Posted new customer to the DB",
        content = {@Content(mediaType = "application/json")})
    })
    @PostMapping("customer/")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer postCustomer(@RequestBody Customer customer) {
        return customerService.create(customer);
    }

    /**
     * Deletes a specified customer from the database
     * If there are orders left, return an error.
     * @param id    customer to delete
     */
    @Operation(summary = "This is to delete a customer stored in the DB")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
        description = "Deleted customer from the DB",
        content = {@Content(mediaType = "application/json")})
    })
    @DeleteMapping("customer/{id}")
    public void deleteCustomer(@PathVariable long id) {
        log.info("DELETING CUSTOMER WITH ID '{}'...", id);
        this.customerService.delete(id);
    }

    /**
     * Updates a already existing customer in the database.
     * @param customer  updated customer object
     * @param id        customer to update
     * @return          customer
     */
    @Operation(summary = "This is to update a customer stored in the DB")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
        description = "Put updated customer into the DB",
        content = {@Content(mediaType = "application/json")})
    })
    @PutMapping("customer/{id}")
    public Customer putCustomer(@RequestBody Customer customer, @PathVariable Long id) {
        log.info("UPDATING CUSTOMER WITH ID '{}'...", id);
        return customerService.update(customer, id);
    }

}