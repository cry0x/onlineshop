package com.onlineshop.product_service;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("customer-service")
public interface ICustomerServiceClient {
}
