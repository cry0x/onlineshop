package com.onlineshop.order_service.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Customer {

    @Id
    @Getter
    @Setter
    private Long customerId;
    @Getter
    @Setter
    private long customerEmail;

}
