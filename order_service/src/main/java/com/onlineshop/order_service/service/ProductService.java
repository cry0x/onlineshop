package com.onlineshop.order_service.service;

import com.onlineshop.order_service.entity.Product;
import com.onlineshop.order_service.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
@Service
public class ProductService {

    private final IProductRepository iProductRepository;

    OrderService orderService = null;

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

    public List<Product> createAllProducts(List<Product> productList) {
        return this.iProductRepository.saveAll(productList);
    }

    public Product updateProduct(Long orderId, Long quantity, Product product) {
        List<Product> productList = orderService.getOrderById(orderId).getProductListInOrder();
        for (Product products : productList) {
            if(products.getOriginalId() == product.getOriginalId()){
                product.setQuantity(quantity + product.getQuantity());
            }
        }

        return this.iProductRepository.save(product);
    }

    public void deleteProductInOrder(Long orderId, Long originalProductId) {
        List<Product> productList = orderService.getOrderById(orderId).getProductListInOrder();
        for (Product product : productList) {
            if(product.getOriginalId() == originalProductId) {
                this.iProductRepository.deleteById(originalProductId);
            }
        }
    }

}

