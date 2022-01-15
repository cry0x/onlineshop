package com.onlineshop.order_service.service;

import com.onlineshop.order_service.entity.Product;
import com.onlineshop.order_service.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@Service
public class ProductService {

    private final IProductRepository iProductRepository;

    @Autowired
    public ProductService(IProductRepository iProductRepository) {
        this.iProductRepository = iProductRepository;
    }

    public Product getProductById(Long id) {
        return this.iProductRepository.findById(id).get();
    }

    public Product createProduct(Product product) {
        return this.iProductRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        product.setId(id);
        return this.iProductRepository.save(product);
    }

    public void deleteProduct(Long id) {
        this.iProductRepository.deleteById(id);
    }

}

