package com.onlineshop.customer_service.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

// The class is annotated with @Entity which indicates that it is a JPA entity.
// The id attribute is annotated with both @Id and @GeneratedValue annotations.
// This means that it is the object's auto-generated primary key.
@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private String city;
    private String street;

    private int postalCode;
    private int streetNr;

}