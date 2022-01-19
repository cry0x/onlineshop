package com.onlineshop.product_service.services;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import com.onlineshop.product_service.utilities.DefaultProductPicture;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest({"eureka.client.enabled:false"})
public class DefaultProductPictureTest {

    @Test
    void getDefaultProductPictureTest() throws IOException {
        String actualEncodedPicture = Base64.getEncoder().encodeToString(DefaultProductPicture.getBinaryData());
        InputStream fis = this.getClass().getResourceAsStream("/DefaultBase64ProductPictureTestData");
        String expectedEncodedPicture = IOUtils.toString(Objects.requireNonNull(fis), StandardCharsets.UTF_8);

        assertEquals(expectedEncodedPicture, actualEncodedPicture);
    }
}
