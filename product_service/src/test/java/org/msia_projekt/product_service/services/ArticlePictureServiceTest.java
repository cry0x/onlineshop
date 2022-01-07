package org.msia_projekt.product_service.services;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.msia_projekt.product_service.ArticlePicture;
import org.msia_projekt.product_service.ArticlePictureService;
import org.msia_projekt.product_service.IArticlePictureRepository;
import org.msia_projekt.product_service.testUtilities.RandomData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ArticlePictureServiceTest {

    @Autowired
    private ArticlePictureService articlePictureService;

    @MockBean
    private IArticlePictureRepository iArticlePictureRepository;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

}
