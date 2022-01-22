package com.onlineshop.product_service.utilities;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * Helper class to get the default ProductPicture when creating a new Product.
 */
public class DefaultProductPicture {
    private static final String defaultPicturePath = "static\\images\\default_product_picture.jpg";
    /**
     * Return the name of the specified default ProductPicture.
     *
     * @return String containing the files name
     */
    public static String getName() {
        return new ClassPathResource(defaultPicturePath).getFilename();
    }

    /**
     * Method to read the specified default ProductPicture's binary data.
     *
     * @return byte[] containing the picture's data
     */
    public static byte[] getBinaryData() {
        byte[] fileData = new byte[0];

        try {
            Resource resource = new ClassPathResource(defaultPicturePath);
            fileData = resource.getInputStream().readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileData;
    }

}
