package com.onlineshop.product_service.controllers;

import com.onlineshop.product_service.entities.Product;
import com.onlineshop.product_service.entities.ProductPicture;
import com.onlineshop.product_service.services.ProductPictureService;
import com.onlineshop.product_service.services.ProductService;
import com.onlineshop.product_service.utilities.HateoasUtilities;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @Operation(summary = "Creates a new product in the database and returns the new products as HATEOAS-Json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Created a new product",
                    content = {@Content(mediaType = "application/hal+json")})
    })
    @PostMapping(value = "/hateoas", produces = { MediaTypes.HAL_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    public EntityModel<Product> postProductHateoas(@RequestBody Product product) {
        log.info("POST: /v1/products/hateoas has been called");

        validateProduct(product);

        if (product.getProductPicture() == null)
            product.setProductPicture(this.productPictureService.createProductPicture(new ProductPicture()));

        return HateoasUtilities.buildProductEntity(this.productService.createProduct(product));
    }

    @Operation(summary = "Creates a new product in the database and returns the new products as Json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Created a new product",
                    content = {@Content(mediaType = "application/json")})
    })
    @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    public Product postProduct(@RequestBody Product product) {
        log.info("POST: /v1/products has been called");

        validateProduct(product);

        if (product.getProductPicture() == null)
            product.setProductPicture(this.productPictureService.createProductPicture(new ProductPicture()));

        return this.productService.createProduct(product);
    }

    @Operation(summary = "Fetches the product with the given id from the database and returns it as HATEOAS-Json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Fetched the product",
                    content = {@Content(mediaType = "application/hal+json")})
    })
    @GetMapping(value = "/hateoas/{productId}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Product> getProductHateoas(@PathVariable Long productId) {
        log.info(String.format("GET: /v1/products/hateoas/%d has been called", productId));

        return HateoasUtilities.buildProductEntity(this.productService.readProductById(productId));
    }

    @Operation(summary = "Fetches the product with the given id from the database and returns it as Json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Fetched the product",
                    content = {@Content(mediaType = "application/json")})
    })
    @GetMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Product getProduct(@PathVariable Long productId) {
        log.info(String.format("GET: /v1/products/%d has been called", productId));

        return this.productService.readProductById(productId);
    }

    @Operation(summary = "Fetches all products from the database and returns them as HATEOAS-Json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Fetched all products",
                    content = {@Content(mediaType = "application/hal+json")})
    })
    @GetMapping(value = "/hateoas", produces = { MediaTypes.HAL_JSON_VALUE })
    public CollectionModel<EntityModel<Product>> getAllProductsHateoas() {
        log.info("GET: /v1/products/hateoas has been called");

        return HateoasUtilities.buildProductCollection(this.productService.readAllProducts());
    }

    @Operation(summary = "Fetches all products from the database and returns them as Json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Fetched all products",
                    content = {@Content(mediaType = "application/hal+json")})
    })
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<Product> getAllProducts() {
        log.info("GET: /v1/products has been called");

        return this.productService.readAllProducts();
    }

    @Operation(summary = "Updates the product with the given id in the database and return it as HATEOAS-Json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Updated the product",
                    content = {@Content(mediaType = "application/hal+json")})
    })
    @PutMapping(value = "/hateoas/{productId}", produces = MediaTypes.HAL_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<Product> putProductHateoas(@PathVariable Long productId,
                                           @RequestBody Product product) {
        log.info(String.format("PUT: /v1/products/hateoas/%d has been called", productId));

        return HateoasUtilities.buildProductEntity(this.productService.updateProduct(productId, product));
    }

    @Operation(summary = "Updates the product with the given id in the database and return it as Json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Updated the product",
                    content = {@Content(mediaType = "application/json")})
    })
    @PutMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Product putProduct(@PathVariable Long productId,
                                           @RequestBody Product product) {
        log.info(String.format("PUT: /v1/products/%d has been called", productId));

        return this.productService.updateProduct(productId, product);
    }

    @Operation(summary = "Updates the productpicture within the product with the given id in the database and return it as HATEOAS-Json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Updated the productpicture",
                    content = {@Content(mediaType = "application/hal+json")})
    })
    @PutMapping(value = "/hateoas/{productId}/productpicture", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Product> putProductPictureOfProductByIdHateoas(@PathVariable Long productId,
                                                               @RequestBody MultipartFile file) throws IOException {
        log.info(String.format("PUT: /v1/products/hateoas/%d/productpicture has been called", productId));

        return HateoasUtilities.buildProductEntity(this.productService.updateProduct(productId,
                putProductPictureOfProductByIdHelper(productId, file)));
    }

    @Operation(summary = "Updates the productpicture within the product with the given id in the database and return it as Json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Updated the productpicture",
                    content = {@Content(mediaType = "application/json")})
    })
    @PutMapping(value = "/{productId}/productpicture", produces = MediaType.APPLICATION_JSON_VALUE)
    public Product putProductPictureOfProductById(@PathVariable Long productId,
                                                               @RequestBody MultipartFile file) throws IOException {
        log.info(String.format("PUT: /v1/products/%d/productpicture has been called", productId));

        return this.productService.updateProduct(productId, putProductPictureOfProductByIdHelper(productId, file));
    }

    private Product putProductPictureOfProductByIdHelper(Long productId,
                                                         MultipartFile file) throws IOException {
        Product product = this.productService.readProductById(productId);

        ProductPicture productPicture = new ProductPicture();
        productPicture.setName(file.getOriginalFilename());
        productPicture.setData(file.getInputStream().readAllBytes());

        productPicture = this.productPictureService.updateProductPicture(product.getProductPicture().getId(), productPicture);

        product.setProductPicture(productPicture);

        return product;
    }

    @Operation(summary = "Deletes the product with the given id in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Deleted the product")
    })
    @DeleteMapping(path = "/{productId}")
    public void deleteProductById(@PathVariable Long productId) {
        log.info(String.format("DELETE: /v1/products/%d has been called", productId));

        Long productPictureId = this.productService.readProductById(productId).getProductPicture().getId();

        this.productService.deleteProductById(productId);
        this.productPictureService.deleteProductPictureById(productPictureId);
    }

    @Operation(summary = "Orders a product, reduces the remaining amount of available products and returns the product info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully ordered product")
    })
    @PutMapping(path = "/{productId}/{amount}")
    public Product putOrderProduct(@PathVariable Long productId, @PathVariable int amount) throws CloneNotSupportedException {
        log.info(String.format("PUT: /v1/products/%d/%d has been called", productId, amount));

        return this.productService.orderProduct(productId, amount);
    }

    @Operation(summary = "Put request to update the available amount of a product in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Adds the given amount to the product")
    })
    @PutMapping(path = "/{productId}/quantity/{amount}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Product putChangeProductQuantity(@PathVariable Long productId, @PathVariable int amount) {
        log.info(String.format("PUT: /v1/products/%d/quantity/%d has been called", productId, amount));

        return this.productService.changeQuantity(productId, amount);
    }

    private void validateProduct(Product product) {
        this.productService.validateProduct(product);
    }

}
