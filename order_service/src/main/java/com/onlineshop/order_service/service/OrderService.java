package com.onlineshop.order_service.service;

import com.onlineshop.order_service.Exceptions.OrderNotFoundException;
import com.onlineshop.order_service.Exceptions.StatusEnumDoesNotExist;
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

    private final IOrderRepository iOrderRepository;
    private final IProductRepository iProductRepository;
    private Order order;
    private EmailService emailService;

    @Autowired
    public OrderService(IOrderRepository iOrderRepository, IProductRepository iProductRepository, EmailService emailService) {
        this.iOrderRepository = iOrderRepository;
        this.iProductRepository = iProductRepository;
        this.emailService = emailService;
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
        return Math.round(sum*100.0)/100.0; // rundet auf 2 Nachkommastellen
    }

    public Order updateOrderStatus(Long orderId, StatusEnum statusEnum){
        if (!this.iOrderRepository.existsById(orderId)) {
            throw new OrderNotFoundException(orderId);
        } else if (!statusEnum.equals(StatusEnum.PENDING) && !statusEnum.equals(StatusEnum.CANCELLED) &&
                !statusEnum.equals(StatusEnum.COMPLETED) && !statusEnum.equals(StatusEnum.IN_DELIVERY)){
           throw new StatusEnumDoesNotExist(statusEnum);
        }
        order = getOrderById(orderId);
        order.setOrderStatus(statusEnum);

        emailService.sendStatusUpdateEmail(order.getCustomerEmail(), orderId, statusEnum.toString());
        return this.iOrderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        if (!this.iOrderRepository.existsById(id)) {
            throw new OrderNotFoundException(id);
        }
        this.iOrderRepository.deleteById(id);
    }

    public List<Order> getOrdersByCustomerId(Long customerId) {
        // Check for customerId exists
        return this.iOrderRepository.findByCustomerId(customerId);
    }

    public Product addProductToOrder(Long orderId, Product newProduct) {
        if (!this.iOrderRepository.existsById(orderId)) {
            throw new OrderNotFoundException(orderId);
        }
        order = getOrderById(orderId);
        List<Product> productListInOrder = order.getProductListInOrder();
        Product currentProduct = null;
        Long currentProductQuantity = null;

        if (productListInOrder.size() > 0) {
            for (Product productInList : productListInOrder) {
                if (newProduct.getOriginalId() == productInList.getOriginalId()) {
                    currentProductQuantity = productInList.getQuantity() ;
                    currentProduct = productInList;
                }
            }
        }
        // Geht eleganter als 2 zusätzliche Variablen anzulegen?
        if (currentProductQuantity != null) {
            currentProduct.setQuantity(currentProductQuantity + newProduct.getQuantity());
            order.setTotalAmount(calculateTotalAmount(productListInOrder));
            return this.iProductRepository.save(currentProduct);
        } else {
            productListInOrder.add(createProduct(newProduct));
            order.setTotalAmount(calculateTotalAmount(productListInOrder));
            return newProduct;
        }
    }

    public void deleteProductInOrder(Long orderId, Long originalProductId) {
        if (!this.iOrderRepository.existsById(orderId)) {
            throw new OrderNotFoundException(orderId);
        }
        List<Product> productList = getOrderById(orderId).getProductListInOrder();
        for (Product productInList : productList) {
            if (productInList.getOriginalId() == originalProductId) {
                this.iProductRepository.deleteByOriginalId(originalProductId); // TODO blocked by foreignKey constraint
            }
        }
    }

    // Necessary?
    public Product getProductById(Long id) {
        return this.iProductRepository.findById(id).get();
    }

    public Product createProduct(Product product) {
        return this.iProductRepository.save(product);
    }

    // Necessary?
    public List<Product> createAllProducts(List<Product> productList) {
        return this.iProductRepository.saveAll(productList);
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
}

