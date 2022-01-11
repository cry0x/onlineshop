package com.onlineshop.product_service.services;

import org.junit.Before;
import org.mockito.MockitoAnnotations;
import com.onlineshop.product_service.repositories.IArticlePictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
