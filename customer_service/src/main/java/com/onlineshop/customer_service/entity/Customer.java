package com.onlineshop.customer_service.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * The class is annotated with @Entity which indicates that it is a JPA entity.
 * The customer object attributes are defined inside this entity.
 * @author Nico Welsch
 * @version 1.0
 */
@Entity
@Data
public class Customer {
    // The id attribute is annotated with both @Id and @GeneratedValue annotations.
    // This means that it is the object's auto-generated primary key.
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