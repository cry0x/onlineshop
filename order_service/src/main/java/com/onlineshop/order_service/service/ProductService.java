/*package com.onlineshop.order_service.service;

import com.onlineshop.order_service.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
@Service
public class ProductService {


    private final RepositoryService repositoryService;

    private final OrderService orderService;

    @Autowired
    public ProductService(RepositoryService repositoryService, OrderService orderService){
        this.repositoryService = repositoryService;
        this.orderService = orderService;
    }


    public Product getProductById(Long id) {
        return this.repositoryService.getProductRepository().findById(id).get();
    }

    public Product createProduct(Product product) {
        return this.repositoryService.getProductRepository().save(product);
    }

    public List<Product> createAllProducts(List<Product> productList) {
        return this.repositoryService.getProductRepository().saveAll(productList);
    }

    public Product addProductQuantity(Long orderId, Long quantity, Product product) {
        List<Product> productList = orderService.getOrderById(orderId).getProductListInOrder();
        for (Product productsInList : productList) {
            if(productsInList.getOriginalId() == product.getOriginalId()){
                product.setQuantity(quantity + product.getQuantity());
            }
        }

        return this.repositoryService.getProductRepository().save(product);
    }

    public void deleteProductInOrder(Long orderId, Long originalProductId) {
        List<Product> productList = orderService.getOrderById(orderId).getProductListInOrder();
        for (Product product : productList) {
            if(product.getOriginalId() == originalProductId) {
                this.repositoryService.getOrderRepository().deleteById(originalProductId);
            }
        }
    }

}
*/
