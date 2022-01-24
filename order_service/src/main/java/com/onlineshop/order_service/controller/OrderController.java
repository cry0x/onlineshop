package com.onlineshop.order_service.controller;

import com.onlineshop.order_service.dto.OrderDto;
import com.onlineshop.order_service.dto.ProductDto;
import com.onlineshop.order_service.entity.Order;
import com.onlineshop.order_service.entity.Product;
import com.onlineshop.order_service.service.OrderService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for sending REST requests via the appropriate addresses
 * @author Simon Spang
 */
@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private static final ModelMapper modelMapper = new ModelMapper();

    private OrderService orderService;
    private Order orderEntity;

    /**
     * Creating a OrderService object, that is used to access all the intern methods
     * that are needed by the Controller to operate properly
     * @param orderService object of the OrderService class
     */
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    /**
     * Creating a Order object, that is used to access all the methods
     * of the Order entity class
     * @param orderEntity of the Order class
     */
    public OrderController(Order orderEntity){
        this.orderEntity = orderEntity;
    }

    /**
     * Gets a specific order by a passed over order id
     * @param id of the desired order
     * @return an Order object
     */
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable(value="id") Long id) {
        log.info("Order (id: {}) has been requested.", id);
        orderEntity = orderService.getOrderById(id);
        return modelMapper.map(orderEntity, Order.class);
    }

    /**
     * Fetches all orders that exist in the database
     * @return a list of order objects
     */
    @GetMapping
    public List<Order> getAllOrders() {
        log.info("Getting all orders in a list...");
        return orderService.getAllOrders();
    }

    /**
     * Creates a new order
     * @param orderDto that is created in the process
     * @return the created order
     */
    @PostMapping
    public OrderDto createOrder(@RequestBody OrderDto orderDto) {
        log.info("Creating a new order.");
        orderEntity = modelMapper.map(orderDto, Order.class);
        Order newOrder = orderService.createOrder(orderEntity);
        return modelMapper.map(newOrder, OrderDto.class);
    }

    /**
     * Deletes an order with the specified id
     * @param id of the order to be deleted from the database
     */
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable(value="id") Long id) {
        log.info("Deleting order (id: {}).", id);
        orderService.deleteOrder(id);
    }

    /**
     * Fetches all orders that are linked to the indicated customer, using the customerId
     * @param id of the customer
     * @return a list of all orders that are associated with that very customer
     */
    @GetMapping("/customer/{customerId}")
    public List<Order> getAllOrdersByCustomerId(@PathVariable(value="customerId") Long id) {
        log.info("All orders of customer (customer id: {}) have been requested.", id);
        List<Order> orders = orderService.getOrdersByCustomerId(id);
        return modelMapper.map(orders, new TypeToken<List<Order>>() {}.getType());
    }

    /**
     * Creates a product and adds it to a specific order, using product object and order id
     * to fetch the order to which the new product gets added to
     * @param id is the order identifier
     * @param productDto represents the newly added product
     * @return the new product that gets created
     */
    @PostMapping("/products/{id}")
    public ProductDto createAndAddProduct(@PathVariable(value="id") Long id, @RequestBody ProductDto productDto) {
        log.info("Creating a new product and adding it to order (id: {}).", id);
        Product productEntity = modelMapper.map(productDto, Product.class);
        productEntity.setOriginalId(productDto.getId());
        Product newProduct = this.orderService.addProductToOrder(id, productEntity);
        return modelMapper.map(newProduct, ProductDto.class);
    }

    /**
     * Deletes a specific product in an order using the order id and the original product id (that is also used by
     * the product service)
     * @param orderId order identifier to reference the order
     * @param productId product indicator of the product that gets deleted
     */
    @DeleteMapping("/{order_id}/{product_id}")
    public void deleteProductInOrder(@PathVariable(value="order_id") Long orderId, @PathVariable(value="product_id") Long productId) {
        log.info("Deleting product (id: {}) from order (id: {}).", productId, orderId);
        orderService.deleteProductInOrder(orderId, productId);
    }

    /**
     * Updates the order status from PENDING to either COMPLETED or to CANCELLED
     * @param id of the order that gets updated
     * @param order to check the current status
     * @return the order with the new order status
     */
    @PutMapping("/{id}")
    public OrderDto updateOrderStatus(@PathVariable(value="id") Long id, @RequestBody OrderDto order) {
        log.info("Updating status of order (id: {}) | Auto-Generated E-Mail sent.", id);
        orderEntity = modelMapper.map(order, Order.class);
        return modelMapper.map(orderService.updateOrderStatus(id, order.getOrderStatus()), OrderDto.class);
    }

    /**
     * Similar to the method above, this method updates the order status of a specific order to
     * IN_DELIVERY to indicate the completion of an order.
     * @param orderId identifier of the order which gets updated
     * @param order used to map the update
     * @return the order with the new order status
     */
    @PutMapping("/orders/{orderId}")
    public OrderDto finishOrder(@PathVariable(value="orderId") Long orderId, @RequestBody OrderDto order) {
        log.info("Finishing order (id: {}) and preparing it for delivery | Auto-Generated E-Mail sent.", orderId);
        orderEntity = modelMapper.map(order, Order.class);
        return modelMapper.map(orderService.completeOrder(orderId), OrderDto.class);
    }


    // =========================== Externe Methoden ================================================================================

    /**
     * Checks if a product exists in any of the orders in the database.
     * Accessed by the product service.
     * @param realProductId identifier of the product that gets checked
     * @return true or false, whether the product exists or not
     */
    @GetMapping("/orders/products/{realProductId}")
    public boolean getIsProductInOrders(@PathVariable(value="realProductId") Long realProductId) {
        log.info("Verifies that the product with the id {} exists in orders.", realProductId);
        return this.orderService.existsProductByRealId(realProductId);
    }

    /**
     * Verifies that the customer with the specified id exists in any of the orders in the database.
     * Used by the Customer Service.
     * @param customerId specifies the user whos orders get checked
     * @return true or false, whether the user is involved in any orders or not
     */
    @GetMapping("/{customerId}/orders")
    public boolean getCustomerOrders(@PathVariable(value="customerId") Long customerId) {
        log.info("Verifies that the customer with the id {} is involved in orders", customerId);
        return this.orderService.getCustomerHasOrders(customerId);
    }

}

