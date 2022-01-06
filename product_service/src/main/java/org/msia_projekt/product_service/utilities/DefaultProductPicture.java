package org.msia_projekt.product_service.utilities;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.io.IOException;

public class DefaultProductPicture {

    public static byte[] getDefaultProductPicture() {
        byte[] fileContent = new byte[0];

        try {
            Resource resource = new ClassPathResource("static\\images\\default_article_picture.jpg");
            FileInputStream fileInputStream = new FileInputStream(resource.getFile());
            fileContent = fileInputStream.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileContent;
    }

}
