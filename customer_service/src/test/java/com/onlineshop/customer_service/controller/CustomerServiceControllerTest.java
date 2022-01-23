package com.onlineshop.customer_service.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshop.customer_service.entity.Customer;
import com.onlineshop.customer_service.service.CustomerService;
import com.onlineshop.customer_service.utilities.RandomData;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Tests each method contained in the CustomerController which
 * in turn tests the entire application since the controller
 * is it's highest abstraction level.
 * @author Nico Welsch
 * @version 1.0
 */
@SpringBootTest({"eureka.client.enabled:false"})
@AutoConfigureMockMvc
public class CustomerServiceControllerTest {
    
    @Autowired
    private MockMvc mock;

    @MockBean
    private CustomerService customerService;

    private static ObjectMapper objectMapper;

    /**
     * Initializes an object mapper before any test is executed.
     */
    @BeforeAll
    static void beforeAll() {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * Tests the getAllCustomers() method by creating a list of random customer objects
     * and checking if they're inside the database afterwards.
     * @throws Exception
     */
    @Test
    void getAllCustomersTest() throws Exception {
        List<Customer> CustomerList = RandomData.RandomCustomerList(15);

        when(this.customerService.getAllCustomers()).thenReturn(CustomerList);

        this.mock.perform(get("/v1/Customers")).andExpect(status().isOk());
    }

    /**
     * Tests the getCustomer() method by creating a customer object
     * and checking if it's inside the database afterwards.
     * @throws Exception
     */
    @Test
    void getCustomerTest() throws Exception {
        Long requestCustomerId = 1L;

        Customer expectedCustomer = new Customer();
        expectedCustomer.setId(1L);
        expectedCustomer.setFirstName("Max");
        expectedCustomer.setLastName("Mustermann");
        expectedCustomer.setEmail("max@mustermann.com");
        expectedCustomer.setCountry("Musterland");
        expectedCustomer.setCity("Musterstadt");
        expectedCustomer.setStreet("Musterstrasse");
        expectedCustomer.setPostalCode(12345);
        expectedCustomer.setStreetNr(12);

        when(this.customerService.find(requestCustomerId)).thenReturn(expectedCustomer);

        this.mock.perform(get("/v1/Customers/1")).andExpect(status().isOk());
    }

    /**
     * Tests the postCustomer() method by creating a random customer object
     * and checking if it's inside the database afterwards.
     * @throws Exception
     */
    @Test
    void postCustomerTest() throws Exception {
        Customer actualCustomer = RandomData.RandomCustomerWithoutId();

        Customer expectedCustomer = (Customer) actualCustomer.clone();
        expectedCustomer.setId(1L);

        when(this.customerService.create(actualCustomer)).thenReturn(expectedCustomer);

        this.mock.perform(post("/v1/Customers")).andExpect(status().isOk());

    }

    /**
     * Tests the deleteCustomer() method by creating a random customer object
     * and checking if it's inside the database afterwards.
     * Then it deletes the customer and checks the deletion itself.
     * @throws Exception
     */
    @Test
    void deleteCustomerTest() throws Exception {
        Customer Customer = RandomData.RandomCustomerWithoutId();
        Customer.setId(1L);

        Long customerId  = Customer.getId();

        when(this.customerService.find(customerId)).thenReturn(Customer);
        doNothing().when(this.customerService).delete(customerId);

        this.mock.perform(delete(String.format("/v1/Customers/%d", customerId))).andExpect(status().isOk());
    }

    /**
     * Tests the putCustomer() method by creating a customer object, updating it
     * and checking if it's actually updated inside the database afterwards.
     * @throws Exception
     */
    @Test
    void putCustomerTest() throws Exception {
        Long customerId = 1L;

        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(1L);
        updatedCustomer.setFirstName("Max");
        updatedCustomer.setLastName("Musterfrau");
        updatedCustomer.setEmail("max@musterfrau.com");
        updatedCustomer.setCountry("Musterland");
        updatedCustomer.setCity("Musterstadt");
        updatedCustomer.setStreet("Musterstrasse");
        updatedCustomer.setPostalCode(54321);
        updatedCustomer.setStreetNr(21);

        when(this.customerService.find(customerId)).thenReturn(updatedCustomer);

        Customer updatedCustomerWithId = new Customer();
        updatedCustomerWithId.setId(customerId);
        updatedCustomerWithId.setFirstName("Max");
        updatedCustomerWithId.setLastName("Musterfrau");
        updatedCustomerWithId.setEmail("max@musterfrau.com");
        updatedCustomerWithId.setCountry("Musterland");
        updatedCustomerWithId.setCity("Musterstadt");
        updatedCustomerWithId.setStreet("Musterstrasse");
        updatedCustomerWithId.setPostalCode(54321);
        updatedCustomerWithId.setStreetNr(21);

        when(this.customerService.update(updatedCustomer, customerId)).thenReturn(updatedCustomerWithId);

        Customer expectedCustomer = new Customer();
        expectedCustomer.setId(customerId);
        expectedCustomer.setFirstName("Max");
        expectedCustomer.setLastName("Musterfrau");
        expectedCustomer.setEmail("max@musterfrau.com");
        expectedCustomer.setCountry("Musterland");
        expectedCustomer.setCity("Musterstadt");
        expectedCustomer.setStreet("Musterstrasse");
        expectedCustomer.setPostalCode(54321);
        expectedCustomer.setStreetNr(21);

        this.mock.perform(put(String.format("/v1/Customers/%d", customerId))).andExpect(status().isOk());
    }

}
