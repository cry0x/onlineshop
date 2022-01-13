package com.onlineshop.product_service.utilities;

import com.onlineshop.product_service.controllers.ProductController;
import com.onlineshop.product_service.controllers.ProductPictureController;
import com.onlineshop.product_service.entities.Product;
import com.onlineshop.product_service.entities.ProductPicture;
import com.onlineshop.product_service.testUtilities.RandomData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@SpringBootTest({"eureka.client.enabled:false"})
public class HateoasUtilitiesTest {

    @Test
    void buildProductEntityTest() {
        ProductPicture productPicture = new ProductPicture();
        productPicture.setId(1L);
        productPicture.setName("");
        productPicture.setData(RandomData.RandomByteArray(20));

        Product product = new Product();
        product.setId(1L);
        product.setName("Testproduct");
        product.setDescription("This a Testproduct");
        product.setPrice(99.99);
        product.setQuantity(20);
        product.setProductPicture(productPicture);

        EntityModel<Product> expectedProductEntityModel = EntityModel.of(product,
                linkTo(methodOn(ProductController.class).getProduct(product.getId())).withSelfRel(),
                linkTo(methodOn(ProductPictureController.class).getProductPicture(product.getProductPicture().getId())).withRel("product_picture"));

        assertEquals(expectedProductEntityModel, HateoasUtilities.buildProductEntity(product));
    }

    @Test
    void buildProductPictureEntityTest() {
        ProductPicture productPicture = new ProductPicture();
        productPicture.setId(1L);
        productPicture.setName("product_picture.jpg");
        productPicture.setData(RandomData.RandomByteArray(20));

        EntityModel<ProductPicture> expectedProductEntityModel = EntityModel.of(productPicture,
                linkTo(methodOn(ProductPictureController.class).getProductPicture(productPicture.getId())).withSelfRel());

        assertEquals(expectedProductEntityModel, HateoasUtilities.buildProductPictureEntity(productPicture));
    }

    @Test
    void buildProductPictureCollectionTest() {
        List<ProductPicture> productPictureList = RandomData.RandomProductPictureList(10);

        List<EntityModel<ProductPicture>> productPictures = productPictureList.stream()
                .map(productPicture -> EntityModel.of(productPicture,
                        linkTo(methodOn(ProductPictureController.class).getProductPicture(productPicture.getId())).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<ProductPicture>> expectedProductPictureCollection = CollectionModel.of(productPictures,
                linkTo(methodOn(ProductPictureController.class).getAllProductPictures()).withSelfRel());

        assertEquals(expectedProductPictureCollection, HateoasUtilities.buildProductPictureCollection(productPictureList));
    }

}
