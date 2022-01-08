package org.msia_projekt.product_service.utilities;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.io.IOException;

public class DefaultProductPicture {

    public static String getName() {
        return new ClassPathResource("static\\images\\default_article_picture.jpg").getFilename();
    }

    public static byte[] getBinaryData() {
        byte[] fileData = new byte[0];

        try {
            Resource resource = new ClassPathResource("static\\images\\default_article_picture.jpg");
            FileInputStream fileInputStream = new FileInputStream(resource.getFile());
            fileData = fileInputStream.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileData;
    }

}
