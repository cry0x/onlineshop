package com.onlineshop.order_service.testUtilities;

import com.onlineshop.order_service.entity.Order;
import com.onlineshop.order_service.entity.Product;
import com.onlineshop.order_service.entity.StatusEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomData {

    public static Long RandomLong() {
        Long retValue = new Random().nextLong();
        return retValue < 0 ? retValue * -1 : retValue;
    }

    public static double RandomDouble() {
        double retValue = new Random().nextInt();
        return retValue < 0 ? retValue * -1 : retValue;
    }

    public static StatusEnum RandomStatusEnum(){
        StatusEnum[] statusEnums = StatusEnum.values();
        int length = statusEnums.length;
        int randomIndex = new Random().nextInt(length);

        return statusEnums[randomIndex];
    }

    public static Product RandomProduct() {
        Product product = new Product();
        product.setId(RandomLong());
        product.setPrice(RandomDouble());
        product.setQuantity(RandomLong());
        product.setOriginalId(RandomLong());

        return product;
    }

    public static Product RandomProductWithoutId() {
        Product product = new Product();
        product.setQuantity(RandomLong());
        product.setPrice(RandomDouble());
        product.setOriginalId(RandomLong());

        return product;
    }

    public static Order RandomOrder() {
        Order order = new Order();
        order.setId(RandomLong());
        order.setOrderStatus(RandomStatusEnum());
        order.setCustomerId(RandomLong());
        //order.setCustomerEmail(RandomString(10)); needs to fit *@*.* form
        order.setProductListInOrder(RandomProductsInOrderList(10));

        return order;
    }

    public static List<Order> RandomOrderList(int listSize) {
        List<Order> orderList = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            orderList.add(RandomOrder());
        }
        return orderList;
    }

    public static List<Product> RandomProductsInOrderList(int listSize) {
        List<Product> productsInOrderList = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            productsInOrderList.add(RandomProduct());
        }

        return productsInOrderList;
    }



}