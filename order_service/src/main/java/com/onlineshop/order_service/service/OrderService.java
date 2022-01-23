package com.onlineshop.order_service.service;

import com.onlineshop.order_service.Exceptions.*;
import com.onlineshop.order_service.clients.IProductServiceClient;
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
    private IProductServiceClient productServiceClient;

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
        validateOrder(order);

        return this.iOrderRepository.save(order);
    }

    public double calculateTotalAmount(List<Product> productListInOrder) {
        double sum = 0;
        for (Product products : productListInOrder) {
            sum += (products.getPrice() * products.getQuantity());
        }

        return Math.round(sum * 100.0) / 100.0; // rundet auf 2 Nachkommastellen
    }

    public Order updateOrderStatus(Long orderId, StatusEnum statusEnum) {
        if (!this.iOrderRepository.existsById(orderId)) {
            throw new OrderNotFoundException(orderId);
        }
        order = getOrderById(orderId);
        validateOrder(order);
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
        // TODO Check for customerId exists
        /*if () {
            throw new CustomerDoesNotExistException(customerId);
        }*/
        return this.iOrderRepository.findByCustomerId(customerId);
    }

    public Product addProductToOrder(Long orderId, Product newProduct) {
        if (!this.iOrderRepository.existsById(orderId)) {
            throw new OrderNotFoundException(orderId);
        }
        validateProduct(newProduct);

        /* TODO Checks in ProductService if quantity of Product is higher than the Quantity of Product we want to add to the order
        if ( (checkProductQuantityInProductService(newProduct.getOriginalId()) - newProduct.getQuantity()) < 0 ) {
            newProduct.setQuantity(actualQuantityFromProductInProductService);
            throw new ProductNotAvailableException(orderId, "There's not enough product in stock");
        }
         */

        order = getOrderById(orderId);
        validateOrder(order);
        List<Product> productListInOrder = order.getProductListInOrder();
        Product currentProduct = null;
        Long currentProductQuantity = null;

        if (productListInOrder.size() > 0) {
            for (Product productInList : productListInOrder) {
                if (newProduct.getOriginalId() == productInList.getOriginalId()) {
                    currentProductQuantity = productInList.getQuantity();
                    currentProduct = productInList;
                }
            }
        }
        if (currentProductQuantity != null) {
            currentProduct.setQuantity(currentProductQuantity + newProduct.getQuantity());
            order.setTotalAmount(calculateTotalAmount(productListInOrder));
            validateOrder(order); // Überprüft totamAmount > 0

            return this.iProductRepository.save(currentProduct);

        } else {
            productListInOrder.add(createProduct(newProduct));
            order.setTotalAmount(calculateTotalAmount(productListInOrder));
            validateOrder(order); // Überprüft totamAmount > 0

            return newProduct;
        }

    }

    public Product createProduct(Product product) {
        return this.iProductRepository.save(product);
    }

    public void deleteProductInOrder(Long orderId, Long originalProductId) {
        if (!this.iOrderRepository.existsById(orderId)) {
            throw new OrderNotFoundException(orderId);
        }
        List<Product> productList = getOrderById(orderId).getProductListInOrder();
        for (Product productInList : productList) {
            if (productInList.getOriginalId() == originalProductId) {
                this.iProductRepository.deleteByOriginalIdInProductListInOrder(originalProductId, orderId);
            }
        }
    }

    // TODO
    private long checkProductQuantityInProductService(Long originalId) {
        return this.productServiceClient.getProductQuantity(originalId);
    }

    public void validateOrder(Order order) {
        if (order.getTotalAmount() < 0) {
            throw new NegativeOrderTotalAmountException(order);
        }
        // TODO Besserer Weg möglich?
        if (!order.getOrderStatus().equals(StatusEnum.PENDING) && !order.getOrderStatus().equals(StatusEnum.CANCELLED) &&
                    !order.getOrderStatus().equals(StatusEnum.COMPLETED) && !order.getOrderStatus().equals(StatusEnum.IN_DELIVERY)) {
            throw new StatusEnumDoesNotExist(order.getOrderStatus());
        }
        if (order.getCustomerId() < 1) {
            throw new CustomerIdIsZeroOrNegative(order);
        }
        if (order.getCustomerEmail().isEmpty()) {
            throw new CustomerEmailIsMissingException(order);
        }
    }

    public void validateProduct(Product product) {
        if (product.getOriginalId() == 0) {
            throw new MissingOriginalProductIdException(product);
        }
        if (product.getQuantity() < 0) {
            throw new NegativeProductQuantityException(product);
        }
        if (product.getPrice() < 0) {
            throw new NegativeProductPriceException(product);
        }
        if (product.getOriginalId() < 0) {
            throw new NegativeProductOriginalIdException(product);
        }
        if (product.getQuantity() == 0) {
            throw new ProductQuantityIsZeroException(product);
        }
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

