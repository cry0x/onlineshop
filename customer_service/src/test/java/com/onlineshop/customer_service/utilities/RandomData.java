package com.onlineshop.customer_service.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.onlineshop.customer_service.entity.Customer;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * Provides the developer with functions to generate random customer objects.
 * @author Nico Welsch
 * @version 1.0
 */
public class RandomData {

    public static byte[] RandomByteArray(int size) {
        Random random = new Random();
        byte[] bytes = new byte[size];
        random.nextBytes(bytes);

        return bytes;
    }

    public static String RandomString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static Long RandomLong() {
        Long retValue = new Random().nextLong();
        return retValue < 0 ? retValue * -1 : retValue;
    }

    public static int RandomInt() {
        int retValue = new Random().nextInt();
        return retValue < 0 ? retValue * -1 : retValue;
    }

    public static double RandomDouble() {
        double retValue = new Random().nextInt();
        return retValue < 0 ? retValue * -1 : retValue;
    }

    public static Customer RandomCustomerWithoutId() {
        Customer customer = new Customer();

        customer.setFirstName(RandomString(20));
        customer.setLastName(RandomString(20));
        customer.setEmail(RandomString(30));
        customer.setCountry(RandomString(20));
        customer.setCity(RandomString(20));
        customer.setStreet(RandomString(20));

        customer.setPostalCode(RandomInt());
        customer.setStreetNr(RandomInt());

        return customer;
    }

    public static Customer RandomCustomer() {
        Customer customer = new Customer();

        customer.setId(RandomLong());

        customer.setFirstName(RandomString(20));
        customer.setLastName(RandomString(20));
        customer.setEmail(RandomString(30));
        customer.setCountry(RandomString(20));
        customer.setCity(RandomString(20));
        customer.setStreet(RandomString(20));

        customer.setPostalCode(RandomInt());
        customer.setStreetNr(RandomInt());

        return customer;
    }

    public static List<Customer> RandomCustomerList(int size) {
        List<Customer> customerList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            customerList.add(RandomCustomer());
        }

        return customerList;
    }


}