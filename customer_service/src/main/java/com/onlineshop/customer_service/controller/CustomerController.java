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

@RestController()
@RequestMapping("/v1/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final static Logger log = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

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
    public List<Customer> getAllCustomers() {
        log.info("FETCHING ALL CUSTOMERS...");
        return customerService.allcustomers();
    }

    // fetch customer by id
    @Operation(summary = "This is to fetch a single customer from the DB specified by it's ID")
    @GetMapping("customer/{id}")
    public Customer getCustomer(@PathVariable Long id) {
        log.info("FETCHING CUSTOMER WITH ID '{}'...", id);
        return customerService.find(id);
    }

    // create customers
    @Operation(summary = "This is to create a new customer and store it in the DB")
    @PostMapping("customer/")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer postCustomer(@RequestBody Customer customer) {
        return customerService.create(customer);
    }

    // delete customers
    // TODO: ADD EXCEPTION IF ORDERS EXIST
    @Operation(summary = "This is to delete a customer stored in the DB")
    @DeleteMapping("customer/{id}")
    public void deleteCustomer(@PathVariable long id) {
        log.info("DELETING CUSTOMER WITH ID '{}'...", id);
        this.customerService.delete(id);
    }

    // update customers
    @Operation(summary = "This is to update a customer stored in the DB")
    @PutMapping("customer/{id}")
    public Customer putCustomer(@RequestBody Customer customer, @PathVariable Long id) {
        log.info("UPDATING CUSTOMER WITH ID '{}'...", id);
        return customerService.updatecustomer(customer, id);
    }

}