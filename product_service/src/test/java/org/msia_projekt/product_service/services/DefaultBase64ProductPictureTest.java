package org.msia_projekt.product_service.services;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.msia_projekt.product_service.utilities.DefaultBase64ProductPicture;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DefaultBase64ProductPictureTest {

    @Test
    void getDefaultBase64ProductPictureTest() throws IOException {
        String actualEncodedPicture = DefaultBase64ProductPicture.getDefaultBase64ProductPicture();

        FileInputStream fis = new FileInputStream("src\\test\\resources\\DefaultBase64ProductPictureTestData");
        String expectedEncodedPicture = IOUtils.toString(fis, StandardCharsets.UTF_8);

        assertEquals(expectedEncodedPicture, actualEncodedPicture);
    }
}
