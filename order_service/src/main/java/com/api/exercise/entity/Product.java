package com.api.exercise.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Data
public class Product {

    @Getter
    @Setter
    private long productId;
    @Getter
    @Setter
    private long price;
    @Getter
    @Setter
    private long quantity;

}
