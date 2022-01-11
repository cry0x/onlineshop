package com.api.exercise.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Product {

    @Id
    @Getter
    @Setter
    private Long productId;
    @Getter
    @Setter
    private long price;
    @Getter
    @Setter
    private long quantity;
    private Long id;

}
