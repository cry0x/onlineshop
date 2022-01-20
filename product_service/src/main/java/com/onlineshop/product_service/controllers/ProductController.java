package com.onlineshop.product_service.controllers;

import com.onlineshop.product_service.entities.Product;
import com.onlineshop.product_service.entities.ProductPicture;
import com.onlineshop.product_service.services.ProductPictureService;
import com.onlineshop.product_service.services.ProductService;
import com.onlineshop.product_service.utilities.HateoasUtilities;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/v1/products")
public class ProductController {

    private final ProductService productService;
    private final ProductPictureService productPictureService;
    private final static Logger log = Logger.getLogger(ProductController.class.getName());

    @Autowired
    public ProductController(ProductService productService,
                             ProductPictureService productPictureService) {
        this.productService = productService;
        this.productPictureService = productPictureService;
    }

    @PostMapping(produces = { MediaTypes.HAL_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    public EntityModel<Product> postProduct(@RequestBody Product product) {
        log.info("POST: /v1/products has been called");

        if (product.getProductPicture() == null)
            product.setProductPicture(this.productPictureService.createProductPicture(new ProductPicture()));

        return HateoasUtilities.buildProductEntity(this.productService.createProduct(product));
    }

    @GetMapping(value = "/{productId}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Product> getProduct(@PathVariable Long productId) {
        log.info(String.format("GET: /v1/products/%d has been called", productId));

        return HateoasUtilities.buildProductEntity(this.productService.readProductById(productId));
    }

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public CollectionModel<EntityModel<Product>> getAllProducts() {
        log.info("GET: /v1/products has been called");

        return HateoasUtilities.buildProductCollection(this.productService.readAllProducts());
    }

    @PutMapping(value = "/{productId}", produces = MediaTypes.HAL_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<Product> putProduct(@PathVariable Long productId,
                                           @RequestBody Product product) {
        log.info(String.format("PUT: /v1/products/%d has been called", productId));

        Product existingProduct = this.productService.readProductById(productId);

        if (product.getProductPicture() == null)
            product.setProductPicture(this.productPictureService.createProductPicture(new ProductPicture()));
        else
            product.setProductPicture(existingProduct.getProductPicture());

        product = this.productService.updateProduct(productId, product);

        return HateoasUtilities.buildProductEntity(product);
    }

    @PostMapping(value = "/{productId}/productpicture")
    public EntityModel<Product> putProductPictureOfProductById(@PathVariable Long productId,
                                                               @RequestBody MultipartFile file) throws IOException {
        log.info(String.format("PUT: /v1/products/%d/productpicture has been called", productId));

        Product product = this.productService.readProductById(productId);

        ProductPicture productPicture = new ProductPicture();
        productPicture.setName(file.getOriginalFilename());
        productPicture.setData(file.getInputStream().readAllBytes());

        productPicture = this.productPictureService.updateProductPicture(product.getProductPicture().getId(), productPicture);

        product.setProductPicture(productPicture);

        return HateoasUtilities.buildProductEntity(this.productService.updateProduct(product.getId(), product));
    }

    @DeleteMapping(path = "/{productId}")
    public void deleteProductById(@PathVariable Long productId) {
        log.info(String.format("DELETE: /v1/products/%d has been called", productId));

        Long productPictureId = this.productService.readProductById(productId).getProductPicture().getId();

        this.productService.deleteProductById(productId);
        this.productPictureService.deleteProductPictureById(productPictureId);
    }

}
