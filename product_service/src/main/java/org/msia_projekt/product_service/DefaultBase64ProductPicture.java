package org.msia_projekt.product_service;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Base64;

public class DefaultBase64ProductPicture {

    public static String getDefaultBase64ProductPicture() {
        byte[] fileContent = new byte[0];

        try {
            Resource resource = new ClassPathResource("static\\images\\default_article_picture.jpg");

            fileContent = FileUtils.readFileToByteArray(resource.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Base64.getEncoder().encodeToString(fileContent);
    }
}
