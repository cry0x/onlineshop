package com.onlineshop.order_service.service;

import com.onlineshop.order_service.entity.Order;
import com.onlineshop.order_service.entity.Product;
import com.onlineshop.order_service.entity.StatusEnum;
import com.onlineshop.order_service.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
@Service
public class OrderService {

    //private EmailService emailService;
    private final IOrderRepository iOrderRepository;

    @Autowired
    ProductService productService;

    @Autowired
    public OrderService(IOrderRepository iOrderRepository) {
        this.iOrderRepository = iOrderRepository;
    }

    public Order getOrderById(Long id) {
        return this.iOrderRepository.findById(id).get();
    }

    public List<Order> getAllOrders() {
        return this.iOrderRepository.findAll();
    }

    public Order createOrder(Order order) {
        order.setTotalAmount(calculateTotalAmount(order.getProductListInOrder()));
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

    public Order updateOrder(Long id, Order order) {
        order.setId(id);
        return this.iOrderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        this.iOrderRepository.deleteById(id);
    }

    public List<Order> getOrdersByCustomerId(Long customerId) {
        return this.iOrderRepository.findByCustomerId(customerId);
    }

    public List<Product> addProductToOrder(Long order_id, Product product) {
        Order order = getOrderById(order_id);
        List<Product> productListInOrder = order.getProductListInOrder();
        // TODO check for same originalProductId -> only update quantity - don't add product to productListInOrder
        /*if (productListInOrder != null) {
            for (Product products : productListInOrder) {
                if (product.getOriginalId() == products.getOriginalId()) {
                    productService.updateProduct(order_id, product.getQuantity(), product);
                } else {
                    productListInOrder.add(productService.createProduct(product));
                }
            }
        } else {*/
            productListInOrder.add(productService.createProduct(product));
            // Calculate total -> Maybe create new method, bc add + remove need calculate
        //}
        order.setTotalAmount(calculateTotalAmount(productListInOrder));
        return productListInOrder;
    }

    public void updateOrderStatus(long id, StatusEnum statusEnum) {

        long customerId = getOrderById(id).getCustomerId();

        // TODO
        // RestCustomer customer = RestCustomer.getCustomerById(customerid);
        // emailService.sendStatusUpdate(customer,id,status.name());

    }

}

