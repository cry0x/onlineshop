package com.onlineshop.product_service.utilities;

import com.onlineshop.product_service.controllers.ProductController;
import com.onlineshop.product_service.controllers.ProductPictureController;
import com.onlineshop.product_service.entities.Product;
import com.onlineshop.product_service.entities.ProductPicture;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public final class HateoasUtilities {

    public static EntityModel<Product> buildProductEntity(Product product) {
        return EntityModel.of(product,
                linkTo(methodOn(ProductController.class).getProduct(product.getId())).withSelfRel(),
                linkTo(methodOn(ProductPictureController.class).getProductPicture(product.getProductPicture().getId())).withRel("product_picture"));
    }

    public static EntityModel<ProductPicture> buildProductPictureEntity(ProductPicture productPicture) {
        return EntityModel.of(productPicture,
                linkTo(methodOn(ProductPictureController.class).getProductPicture(productPicture.getId())).withSelfRel());
    }

    public static CollectionModel<EntityModel<ProductPicture>> buildProductPictureCollection(List<ProductPicture> productPictureList) {
        List<EntityModel<ProductPicture>> productPictures = productPictureList.stream()
                .map(productPicture -> EntityModel.of(productPicture,
                        linkTo(methodOn(ProductPictureController.class).getProductPicture(productPicture.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(productPictures,
                linkTo(methodOn(ProductPictureController.class).getAllProductPictures()).withSelfRel());
    }

}
