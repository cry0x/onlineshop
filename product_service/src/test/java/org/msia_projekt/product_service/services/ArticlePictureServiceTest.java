package org.msia_projekt.product_service.services;

import org.checkerframework.checker.units.qual.A;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.msia_projekt.product_service.entities.Article;
import org.msia_projekt.product_service.entities.ArticlePicture;
import org.msia_projekt.product_service.exceptions.ArticlePictureDoesntExistException;
import org.msia_projekt.product_service.repositories.IArticlePictureRepository;
import org.msia_projekt.product_service.testUtilities.RandomData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyChar;
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

    @Test
    void readArticlePictureByIdTest() {
        Long articleId = RandomData.RandomLong();

        ArticlePicture expectedArticlePicture = RandomData.RandomArticlePicture();

        when(this.iArticlePictureRepository.findById(articleId)).thenReturn(Optional.of(expectedArticlePicture));

        assertEquals(expectedArticlePicture, this.articlePictureService.readArticlePictureById(articleId));
    }

    @Test
    void readArticlePictureByIdExceptionTest() {
        Long articleId = RandomData.RandomLong();

        when(this.iArticlePictureRepository.findById(articleId)).thenThrow(new ArticlePictureDoesntExistException(articleId));

        assertThrows(ArticlePictureDoesntExistException.class, () -> this.articlePictureService.readArticlePictureById(articleId));
    }

    @Test
    void createArticlePictureTest() throws CloneNotSupportedException {
        Long articleId = RandomData.RandomLong();

        ArticlePicture actualArticlePicture = RandomData.RandomArticlePictureWithoutId();
        ArticlePicture expectedArticlePicture = (ArticlePicture) actualArticlePicture.clone();
        expectedArticlePicture.setId(articleId);

        when(this.iArticlePictureRepository.save(actualArticlePicture)).thenReturn(expectedArticlePicture);

        assertEquals(expectedArticlePicture, this.articlePictureService.createArticlePicture(actualArticlePicture));
    }

    @Test
    void readAllArticlePictures() {
        List<ArticlePicture> expectedArticlePictureList = RandomData.RandomArticlePictureList(15);

        when(this.iArticlePictureRepository.findAll()).thenReturn(expectedArticlePictureList);

        assertEquals(expectedArticlePictureList, this.articlePictureService.readAllArticlePictures());
    }

    @Test
    void updateArticlePictureTest() throws CloneNotSupportedException {
        Long articleId = RandomData.RandomLong();

        ArticlePicture articlePicture = RandomData.RandomArticlePictureWithoutId();
        ArticlePicture expectedArticlePicture = (ArticlePicture) articlePicture.clone();
        expectedArticlePicture.setId(articleId);

        when(this.iArticlePictureRepository.existsById(articleId)).thenReturn(true);
        when(this.iArticlePictureRepository.save(expectedArticlePicture)).thenReturn(expectedArticlePicture);

        assertEquals(expectedArticlePicture, this.articlePictureService.updateArticlePicture(articleId, articlePicture));
    }

    @Test
    void updateArticlePictureThrowsArticlePictureDoesntExistExceptionTest() {
        Long articleId = RandomData.RandomLong();
        ArticlePicture article = RandomData.RandomArticlePicture();

        when(this.iArticlePictureRepository.existsById(articleId)).thenReturn(false);

        assertThrows(ArticlePictureDoesntExistException.class, () -> this.articlePictureService.updateArticlePicture(articleId, article));
    }

}
