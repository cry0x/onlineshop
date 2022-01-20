package com.onlineshop.order_service.service;

import com.onlineshop.order_service.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

    private final IProductRepository iProductRepository;

    @Autowired
    public ProductService(IProductRepository iProductRepository) {
        this.iProductRepository = iProductRepository;
    }

    public boolean existsProductByRealId(Long realProductId) {
        return this.iProductRepository.existsProductByRealId(realProductId);
    }

}
