package com.onlineshop.order_service.service;

import com.onlineshop.order_service.Exceptions.*;
import com.onlineshop.order_service.controller.OrderController;
import com.onlineshop.order_service.entity.Order;
import com.onlineshop.order_service.entity.Product;
import com.onlineshop.order_service.entity.StatusEnum;
import com.onlineshop.order_service.repository.IOrderRepository;
import com.onlineshop.order_service.repository.IProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The OrderService contain methods that are used for logging used in the OrderController
 * @author Simon Spang
 */
@Component
@Service
@Transactional
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final IOrderRepository iOrderRepository;
    private final IProductRepository iProductRepository;
    private EmailService emailService;
    private ProductService productService;

    private Order order;
    /**
     * Initializes all the objects that were created before
     * @param iOrderRepository Object of the OrderRepository Interface
     * @param iProductRepository Object of the ProductRepository Interface
     * @param emailService Object of the EmailService
     */
    @Autowired
    public OrderService(IOrderRepository iOrderRepository, IProductRepository iProductRepository, EmailService emailService) {
      //  this.iProductServiceClient = iProductServiceClient;
        this.iOrderRepository = iOrderRepository;
        this.iProductRepository = iProductRepository;
        this.emailService = emailService;
    }

    /**
     * Fetches an order by its identifier from the database
     * @param id of the order that gets fetched
     * @return the desired order, if it exists
     */
    public Order getOrderById(Long id) {
        return this.iOrderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    /**
     * Gets all orders in the database
     * @return a List of all orders
     */
    public List<Order> getAllOrders() {
        return this.iOrderRepository.findAll();
    }

    /**
     * Creates an order and adds it to the database. Initializes the total amount,
     * sets order status to PENDING and checks the validity of the order
     * @param order the order that gets added to the database
     * @return the order object of the created order
     */
    public Order createOrder(Order order) {
        order.setTotalAmount(0);
        order.setOrderStatus(StatusEnum.PENDING);
        validateOrder(order);

        return this.iOrderRepository.save(order);
    }

    /**
     * Helper method to calculate the total amount of an order.
     * Adds up all the products of an order multiplied by their quantity.
     * @param productListInOrder list of products that get summed up
     * @return the total amount of an order
     */
    public double calculateTotalAmount(List<Product> productListInOrder) {
        double sum = 0;
        for (Product products : productListInOrder) {
            sum += (products.getPrice() * products.getQuantity());
        }

        return Math.round(sum * 100.0) / 100.0;
    }

    /**
     * Updates the order status of an order by passing over the new order status.
     * Checks if the order exists and validates it and if successful sends a notification
     * email to the customer email using their email address to inform them of the new order status.
     * @param orderId of the order that gets updated
     * @param statusEnum new status of the order
     * @return the updated order object
     */
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

    /**
     * Similar to the method above - updates the order status to IN_DELIVERY to indicate the completion
     * of an order. Checks also if the product list in the order is empty to prevent the user from sending
     * an invalid/empty order. By success an automatic generated email gets send to the user, using their email
     * address to notify them of the new order status.
     * @param orderId used to specify the order that gets completed
     * @return
     */
    public Order completeOrder(Long orderId) {
        if (!this.iOrderRepository.existsById(orderId)) {
            throw new OrderNotFoundException(orderId);
        }
        order = getOrderById(orderId);
        validateOrder(order);
        if (order.getProductListInOrder().size() < 1) {
            throw new ProductListInOrderIsEmptyException(orderId);
        }
        order.setOrderStatus(StatusEnum.IN_DELIVERY);
        emailService.sendStatusUpdateEmail(order.getCustomerEmail(), orderId, StatusEnum.IN_DELIVERY.toString());

        return this.iOrderRepository.save(order);
    }

    /**
     * Deletes the specified order from the database. Checks if the order status is either IN_DELIVERY or COMPLETED,
     * which then prevents the program from deleting this order.
     * @param id identifier of the order that gets deleted
     */
    public void deleteOrder(Long id) {
        if (!this.iOrderRepository.existsById(id)) {
            throw new OrderNotFoundException(id);
        }
        if (this.iOrderRepository.getById(id).getOrderStatus().toString().equals("IN_DELIVERY") ||
                this.iOrderRepository.getById(id).getOrderStatus().toString().equals("COMPLETED")) {
            throw new OrderIsNotPendingOrCancelledException(id);
        }

        this.iOrderRepository.deleteById(id);
    }

    /**
     * Fetches all orders of a specific customer, identified by their customer id.
     * Throws exception if the desired customer does not exist in the database.
     * @param customerId of the customer whos orders get displayed
     * @return a list of all orders of that customer
     */
    public List<Order> getOrdersByCustomerId(Long customerId) {
        if (this.iOrderRepository.findByCustomerId(customerId).size() < 1) {
            throw new CustomerDoesNotExistException(customerId);
        }

        return this.iOrderRepository.findByCustomerId(customerId);
    }

    /**
     * Adds a new product (or updates the quantity of an existing product with the same ID) to the specified order
     * using the order id. Checks if the order exists, validates the product and fetches the product list of the order.
     * Checks if the new product already exists in the product list of the order by the original product id (that also
     * gets used by the Product Service).
     * In case the new product exists in the order only the quantity of the product gets updated - the new product will
     * not be added then.
     * In case the product does not exist in the order, add it to the product list. In both cases the total amount
     * of the order gets updated and checked if it has a positive value.
     * @param orderId identifier of the order
     * @param newProduct that gets added to the order
     * @return the product that has been added/updated
     */
    public Product addProductToOrder(Long orderId, Product newProduct) {
        if (!this.iOrderRepository.existsById(orderId)) {
            throw new OrderNotFoundException(orderId);
        }
        validateProduct(newProduct);

        order = getOrderById(orderId);


        if (productService.sufficientProductQuantity(newProduct) == null ) {
            if (productService.sufficientProductQuantity(newProduct).getQuantity() < newProduct.getQuantity()) {
                throw new ProductQuantityInsufficientException(newProduct.getOriginalId());
            }
        }

        /* TODO Checks in ProductService if quantity of Product is higher than the Quantity of Product we want to add to the order
        if ( (checkProductQuantityInProductService(newProduct.getOriginalId()) - newProduct.getQuantity()) < 0 ) {
            newProduct.setQuantity(actualQuantityFromProductInProductService);
            throw new ProductNotAvailableException(orderId, "There's not enough product in stock");
        }
         */

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
            validateOrder(order); // Checks totalAmount > 0

            return this.iProductRepository.save(currentProduct);

        } else {
            productListInOrder.add(createProduct(newProduct));
            order.setTotalAmount(calculateTotalAmount(productListInOrder));
            validateOrder(order); // Checks totalAmount > 0

            return newProduct;
        }

    }

    /**
     * Actual method to add a product to the database.
     * @param product that gets added
     * @return the newly added product
     */
    public Product createProduct(Product product) {
        return this.iProductRepository.save(product);
    }

    /**
     * Deletes a product in a specified order. Checks if the order exists, if the order status is IN_DELIVERY/COMPLETED
     * and if the product exists in the product list of the order. If so, it gets deleted from the reference table in
     * the database and from the product database (otherwise it would injure foreign key constraint).
     * @param orderId of the order, where the product gets deleted from
     * @param originalProductId of the product that gets removed from the database
     */
    public void deleteProductInOrder(Long orderId, Long originalProductId) {
        if (!this.iOrderRepository.existsById(orderId)) {
            throw new OrderNotFoundException(orderId);
        }
        if (order.getOrderStatus().toString().equals("IN_DELIVERY") || order.getOrderStatus().toString().equals("COMPLETED")) {
            throw new OrderIsNotPendingOrCancelledException(orderId, "The product (id: " + originalProductId + ") in the "+
                    "order (id: %d) cannot be deleted. The order is either IN_DELIVERY or COMPLETED!");
        }
        List<Product> productList = getOrderById(orderId).getProductListInOrder();
        for (Product productInList : productList) {
            if (productInList.getOriginalId() == originalProductId) {
                this.iProductRepository.deleteByOriginalIdInProductListInOrder(originalProductId, orderId);
                this.iProductRepository.deleteByOriginalIdProducts(originalProductId);
            }
        }
    }

    /**
     * Validates an order: checks for a negative total amount, eligible order status enums, for a positive customerID
     * and for the customer email address to be not empty.
     * @param order that gets checked
     */
    public void validateOrder(Order order) {
        if (order.getTotalAmount() < 0) {
            throw new NegativeOrderTotalAmountException(order);
        }
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

    /**
     * Validates a product: Checks for an existing/valid productId, for a non-negative product quantity, for a non-
     * negative product price, for a non-negative productID and if the quantity is zero, since this method is only
     * used while adding/updating a new product to/in an order.
     * @param product that gets checked
     */
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

    /**
     * Checks if a product exists by their real product id, that is also used by the Product Service.
     * This method is used only by the Product Service.
     * @param realProductId identifier of the product that gets checked
     * @return true or false, whether or not the product extists in the database
     */
    public boolean existsProductByRealId(Long realProductId) {
        return this.iProductRepository.existsProductByRealId(realProductId);
    }

    /**
     * Checks if a customer is involved in any orders in the database.
     * This method is used only by the Customer Service.
     * @param customerId identifier of the customer whos orders get checked
     * @return true or false, whether or not the customer has any orders in the database
     */
    public boolean getCustomerHasOrders(Long customerId) {
        return this.iOrderRepository.getCustomerHasOrders(customerId);
    }

/*
    // Checks in Product Service if there's sufficient quantity of the requested product available
    public boolean sufficientProductQuantity(Product product) {
        boolean value = false;
        try {
            value = this.productServiceClient.sufficientProductQuantity(product.getOriginalId(), product.getQuantity());
        } catch (Exception e) {
            log.error("Product service is not available!\n" + e.getLocalizedMessage());
        }
        return value;
    }*/


}

