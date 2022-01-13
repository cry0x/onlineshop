package com.onlineshop.product_service.services;

import com.onlineshop.product_service.entities.ProductPicture;
import com.onlineshop.product_service.exceptions.ProductPictureDoesntExistException;
import com.onlineshop.product_service.testUtilities.RandomData;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import com.onlineshop.product_service.repositories.IProductPictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest({"eureka.client.enabled:false"})
public class ProductPictureServiceTest {

    @Autowired
    @InjectMocks
    private ProductPictureService productPictureService;
    @MockBean
    private IProductPictureRepository iProductPictureRepository;

    @Test
    void readProductPictureByIdTest() {
        Long productId = RandomData.RandomLong();

        ProductPicture expectedProductPicture = RandomData.RandomProductPicture();

        when(this.iProductPictureRepository.findById(productId)).thenReturn(Optional.of(expectedProductPicture));

        assertEquals(expectedProductPicture, this.productPictureService.readProductPictureById(productId));
    }

    @Test
    void readProductPictureByIdExceptionTest() {
        Long productId = RandomData.RandomLong();

        when(this.iProductPictureRepository.findById(productId)).thenThrow(new ProductPictureDoesntExistException(productId));

        assertThrows(ProductPictureDoesntExistException.class, () -> this.productPictureService.readProductPictureById(productId));
    }

    @Test
    void createProductPictureTest() throws CloneNotSupportedException {
        Long productId = RandomData.RandomLong();

        ProductPicture actualProductPicture = RandomData.RandomProductPictureWithoutId();
        ProductPicture expectedProductPicture = (ProductPicture) actualProductPicture.clone();
        expectedProductPicture.setId(productId);

        when(this.iProductPictureRepository.save(actualProductPicture)).thenReturn(expectedProductPicture);

        assertEquals(expectedProductPicture, this.productPictureService.createProductPicture(actualProductPicture));
    }

    @Test
    void readAllProductPictures() {
        List<ProductPicture> expectedProductPictureList = RandomData.RandomProductPictureList(15);

        when(this.iProductPictureRepository.findAll()).thenReturn(expectedProductPictureList);

        assertEquals(expectedProductPictureList, this.productPictureService.readAllProductPictures());
    }

    @Test
    void updateProductPictureTest() throws CloneNotSupportedException {
        Long productId = RandomData.RandomLong();

        ProductPicture productPicture = RandomData.RandomProductPictureWithoutId();
        ProductPicture expectedProductPicture = (ProductPicture) productPicture.clone();
        expectedProductPicture.setId(productId);

        when(this.iProductPictureRepository.existsById(productId)).thenReturn(true);
        when(this.iProductPictureRepository.save(expectedProductPicture)).thenReturn(expectedProductPicture);

        assertEquals(expectedProductPicture, this.productPictureService.updateProductPicture(productId, productPicture));
    }

    @Test
    void updateProductPictureThrowsProductPictureDoesntExistExceptionTest() {
        Long productId = RandomData.RandomLong();
        ProductPicture product = RandomData.RandomProductPicture();

        when(this.iProductPictureRepository.existsById(productId)).thenReturn(false);

        assertThrows(ProductPictureDoesntExistException.class, () -> this.productPictureService.updateProductPicture(productId, product));
    }

}
