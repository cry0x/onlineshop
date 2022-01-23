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

/**
 * Helper class to make the code of the controller classes more readable.
 * Used to generate Entity- and CollectionModel from a given entity.
 */
public final class HateoasUtilities {
    /**
     * Converts a given Product into an EntityModel.
     * Depending on the state of some referenced object's in the Product, a link to the new Product-Version is also added.
     *
     * @param product Product to create the EntityModel for
     * @return EntityModel containing the Product's information with links
     */
    public static EntityModel<Product> buildProductEntity(Product product) {
        if (product.getNewProductVersion() != null) {
            return EntityModel.of(product,
                    linkTo(methodOn(ProductController.class).getProduct(product.getId())).withSelfRel(),
                    linkTo(methodOn(ProductPictureController.class).getProductPicture(product.getProductPicture().getId())).withRel("ProductPicture"),
                    linkTo(methodOn(ProductController.class).getProduct(product.getNewProductVersion().getId())).withRel("NewProductVersion"));
        } else {
            return EntityModel.of(product,
                    linkTo(methodOn(ProductController.class).getProduct(product.getId())).withSelfRel(),
                    linkTo(methodOn(ProductPictureController.class).getProductPicture(product.getProductPicture().getId())).withRel("ProductPicture"));
        }
    }

    /**
     * Converts a list of given Product's into an CollectionModel and utilizes the 'buildProductEntity'-Method for this.
     *
     * @param productList List of Product's to create the CollectionModel for
     * @return CollectionModel containing the information of all given Product's with corresponding links
     */
    public static CollectionModel<EntityModel<Product>> buildProductCollection(List<Product> productList) {
        List<EntityModel<Product>> products  = productList.stream()
                .map(HateoasUtilities::buildProductEntity)
                .collect(Collectors.toList());

        return CollectionModel.of(products,
                linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());
    }

    /**
     * Converts a given product-picture into an EntityModel.
     *
     * @param productPicture ProductPicture to create the EntityModel for
     * @return EntityModel of ProductPicture containing links and object-information
     */
    public static EntityModel<ProductPicture> buildProductPictureEntity(ProductPicture productPicture) {
        return EntityModel.of(productPicture,
                linkTo(methodOn(ProductPictureController.class).getProductPicture(productPicture.getId())).withSelfRel());
    }

    /**
     * Converts a list of given product-pictures into an CollectionModel and utilizes the buildProductPictureEntity-Method for this.
     *
     * @param productPictureList List of ProductPicture's to create the CollectionModel for
     * @return CollectionModel containing the information of all given ProductPicture's with corresponding links
     */
    public static CollectionModel<EntityModel<ProductPicture>> buildProductPictureCollection(List<ProductPicture> productPictureList) {
        List<EntityModel<ProductPicture>> productPictures = productPictureList.stream()
                .map(productPicture -> EntityModel.of(productPicture,
                        linkTo(methodOn(ProductPictureController.class).getProductPicture(productPicture.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(productPictures,
                linkTo(methodOn(ProductPictureController.class).getAllProductPictures()).withSelfRel());
    }

}
