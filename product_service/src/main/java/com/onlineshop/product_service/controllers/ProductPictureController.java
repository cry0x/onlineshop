package com.onlineshop.product_service.controllers;

import com.onlineshop.product_service.entities.ProductPicture;
import com.onlineshop.product_service.services.ProductPictureService;
import com.onlineshop.product_service.utilities.HateoasUtilities;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Fetches the productpicture with the given id from the database and returns it as HATEOAS+Json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Fetched the productpicture",
                    content = {@Content(mediaType = "application/hal+json")})
    })
    @GetMapping(value = "/{productPictureId}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<ProductPicture> getProductPicture(@PathVariable Long productPictureId) {
        log.info(String.format("GET: /v1/productpictures/%d has been called", productPictureId));

        ProductPicture productPicture = this.productPictureService.readProductPictureById(productPictureId);

        return HateoasUtilities.buildProductPictureEntity(productPicture);
    }

    @Operation(summary = "Fetches all productpictures from the database and returns them as HATEOAS+Json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Fetched all productpictures",
                    content = {@Content(mediaType = "application/hal+json")})
    })
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<ProductPicture>> getAllProductPictures() {
        log.info("GET: /v1/productpictures has been called");

        return HateoasUtilities.buildProductPictureCollection(this.productPictureService.readAllProductPictures());
    }

    @Operation(summary = "Deletes the productpicture with the given id from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Deleted the productpicture")
    })
    @DeleteMapping(value = "/{productPictureId}")
    public void deleteProductPictureById(@PathVariable Long productPictureId) {
        log.info(String.format("DELETE: /v1/productpictures/%d has been called", productPictureId));

        this.productPictureService.deleteProductPictureById(productPictureId);
    }
}
