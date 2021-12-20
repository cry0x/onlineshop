package com.customer_service.customer_service.entity;

import javax.persistence.Column;
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

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String adress;

    // Getters and Setters
    public Long getId() {
		return this.id;
	}

	public String getFirstName() {
		return this.firstName;
	}

    public String getLastName() {
		return this.lastName;
	}

    public String getEmail() {
		return this.email;
	}

    public String getAdress() {
		return this.adress;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

    public void setLastName(String lastName) {
		this.lastName = lastName;
	}

    public void setEmail(String email) {
		this.email = email;
	}

    public void setAdress(String adress) {
		this.adress = adress;
	}

    public String toString(){  
        return "First Name: " + firstName + "\n" 
            +  "Last Name: "  + lastName  + "\n"
            +  "Email: "  + email  + "\n"
            +  "Adress: "  + adress  + "\n";
       }  

}
