package com.onlineshop.product_service.testUtilities;

import com.onlineshop.product_service.entities.Product;
import com.onlineshop.product_service.entities.ProductPicture;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomData {

    public static byte[] RandomByteArray(int size) {
        Random random = new Random();
        byte[] bytes = new byte[size];
        random.nextBytes(bytes);

        return bytes;
    }

    public static String RandomString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static Long RandomLong() {
        return new Random().nextLong();
    }

    public static int RandomInt() {
        return new Random().nextInt();
    }

    public static double RandomDouble() {
        return new Random().nextDouble();
    }

    public static ProductPicture RandomProductPictureWithoutId() {
        ProductPicture productPicture = new ProductPicture();
        productPicture.setName(RandomString(15));
        productPicture.setData(RandomByteArray(50));

        return productPicture;
    }

    public static ProductPicture RandomProductPicture() {
        ProductPicture productPicture = new ProductPicture();
        productPicture.setId(RandomLong());
        productPicture.setName(RandomString(15));
        productPicture.setData(RandomByteArray(50));

        return productPicture;
    }

    public static List<ProductPicture> RandomProductPictureList(int size) {
        List<ProductPicture> productPictureList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            productPictureList.add(RandomProductPicture());
        }

        return productPictureList;
    }

    public static Product RandomProductWithoutId() {
        Product product = new Product();
        product.setName(RandomString(15));
        product.setDescription(RandomString(50));
        product.setQuantity(RandomInt());
        product.setPrice(RandomDouble());
        product.setProductPicture(RandomProductPictureWithoutId());

        return product;
    }

    public static Product RandomProduct() {
        Product product = new Product();
        product.setId(RandomLong());
        product.setName(RandomString(15));
        product.setDescription(RandomString(50));
        product.setQuantity(RandomInt());
        product.setPrice(RandomDouble());
        product.setProductPicture(RandomProductPicture());

        return product;
    }

    public static List<Product> RandomProductList(int size) {
        List<Product> productList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            productList.add(RandomProduct());
        }

        return productList;
    }


}
