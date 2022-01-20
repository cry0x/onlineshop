package com.onlineshop.order_service.service;

import com.onlineshop.order_service.Exceptions.OrderNotFoundException;
import com.onlineshop.order_service.entity.Order;
import com.onlineshop.order_service.entity.Product;
import com.onlineshop.order_service.entity.StatusEnum;
import com.onlineshop.order_service.repository.IOrderRepository;
import com.onlineshop.order_service.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
@Service
public class OrderService {

    // TODO private EmailService emailService;

    private final IOrderRepository iOrderRepository;
    private final IProductRepository iProductRepository;

    @Autowired
    public OrderService(IOrderRepository iOrderRepository, IProductRepository iProductRepository) {
        this.iOrderRepository = iOrderRepository;
        this.iProductRepository = iProductRepository;
    }

    public Order getOrderById(Long id) {
        return this.iOrderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    public List<Order> getAllOrders() {
        return this.iOrderRepository.findAll();
    }

    public Order createOrder(Order order) {
        order.setTotalAmount(0);
        order.setOrderStatus(StatusEnum.PENDING);
        return this.iOrderRepository.save(order);
    }

    public double calculateTotalAmount(List<Product> productListInOrder) {
        double sum = 0;
        for (Product products : productListInOrder) {
            sum += (products.getPrice() * products.getQuantity());
        }
        return sum;
    }

    // Necessary?
    public Order updateOrder(Long id, Order newOrder) {
        if (!this.iOrderRepository.existsById(id)) {
            throw new OrderNotFoundException(id);
        }
        Order oldOrder = getOrderById(id);
        newOrder.setProductListInOrder(oldOrder.getProductListInOrder());
        newOrder.setId(id);

        return this.iOrderRepository.save(newOrder);
    }

    public void deleteOrder(Long id) {
        if (!this.iOrderRepository.existsById(id)) {
            throw new OrderNotFoundException(id);
        }
        this.iOrderRepository.deleteById(id);
    }

    public List<Order> getOrdersByCustomerId(Long customerId) {
        return this.iOrderRepository.findByCustomerId(customerId);
    }

    public List<Product> addProductToOrder(Long orderId, Product product) {
        if (!this.iOrderRepository.existsById(orderId)) {
            throw new OrderNotFoundException(orderId);
        }
        Order order = getOrderById(orderId);
        List<Product> productListInOrder = order.getProductListInOrder();
        boolean productExists = false;
        if (productListInOrder.size() > 0) {
            for (Product productInList : productListInOrder) {
                if (product.getOriginalId() == productInList.getOriginalId()) {
                    productExists = true;
                }
            }
        }
        if (productExists) {
            increaseProductQuantity(orderId, product.getQuantity(), product);
        } else {
            productListInOrder.add(createProduct(product));
        }
        order.setTotalAmount(calculateTotalAmount(productListInOrder));
        return productListInOrder;
    }

    // Necessary?
    public Product getProductById(Long id) {
        return this.iProductRepository.findById(id).get();
    }

    public Product createProduct(Product product) {
        return this.iProductRepository.save(product);
    }

    public List<Product> createAllProducts(List<Product> productList) {
        return this.iProductRepository.saveAll(productList);
    }

    public Product increaseProductQuantity(Long orderId, Long quantity, Product product) {
        if (!this.iOrderRepository.existsById(orderId)) {
            throw new OrderNotFoundException(orderId);
        }
        List<Product> productList = getOrderById(orderId).getProductListInOrder();
        for (Product productsInList : productList) {
            if (productsInList.getOriginalId() == product.getOriginalId()) {
                product.setQuantity(quantity + product.getQuantity());
            }
        }
        return this.iProductRepository.save(product);
    }

    // Necessary?
    public Product decreaseProductQuantity(Long orderId, Long quantity, Product product) {
        if (!this.iOrderRepository.existsById(orderId)) {
            throw new OrderNotFoundException(orderId);
        }
        List<Product> productList = getOrderById(orderId).getProductListInOrder();
        for (Product productsInList : productList) {
            if (productsInList.getOriginalId() == product.getOriginalId()) {
                if (quantity - product.getQuantity() < 0) {
                    // throw exception
                } else {
                    product.setQuantity(quantity - product.getQuantity());
                }
            }
        }
        return this.iProductRepository.save(product);
    }

    public void deleteProductInOrder(Long orderId, Long originalProductId) {
        if (!this.iOrderRepository.existsById(orderId)) {
            throw new OrderNotFoundException(orderId);
        }
        List<Product> productList = getOrderById(orderId).getProductListInOrder();
        for (Product product : productList) {
            if (product.getOriginalId() == originalProductId) {
                this.iProductRepository.deleteById(originalProductId);
            }
        }
    }

}

