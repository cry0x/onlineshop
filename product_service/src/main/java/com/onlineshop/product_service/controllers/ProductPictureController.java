package com.onlineshop.product_service.controllers;

import com.onlineshop.product_service.entities.ProductPicture;
import com.onlineshop.product_service.services.ProductPictureService;
import com.onlineshop.product_service.utilities.HateoasUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/productpictures")
public class ProductPictureController {

    private final ProductPictureService productPictureService;
    private final static Logger log = LoggerFactory.getLogger(ProductPictureController.class);

    @Autowired
    public ProductPictureController(ProductPictureService productPictureService) {
        this.productPictureService = productPictureService;
    }

    @GetMapping(value = "/{productPictureId}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<ProductPicture> getProductPicture(@PathVariable Long productPictureId) {
        log.info(String.format("GET: /v1/productpictures/%d has been called", productPictureId));

        ProductPicture productPicture = this.productPictureService.readProductPictureById(productPictureId);

        return HateoasUtilities.buildProductPictureEntity(productPicture);
    }

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<ProductPicture>> getAllProductPictures() {
        log.info("GET: /v1/productpictures has been called");

        return HateoasUtilities.buildProductPictureCollection(this.productPictureService.readAllProductPictures());
    }

    @DeleteMapping(value = "/{productPictureId}")
    public void deleteProductPictureById(@PathVariable Long productPictureId) {
        log.info(String.format("DELETE: /v1/productpictures/%d has been called", productPictureId));

        this.productPictureService.deleteProductPictureById(productPictureId);
    }
}
