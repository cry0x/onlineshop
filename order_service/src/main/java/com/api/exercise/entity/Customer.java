package com.api.exercise.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;

@Entity
@Data
public class Customer {

    @Getter
    @Setter
    private long customerId;
    @Getter
    @Setter
    private long customerEmail;

}
