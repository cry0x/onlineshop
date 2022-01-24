package com.onlineshop.product_service.services;

import com.onlineshop.product_service.entities.ProductPicture;
import com.onlineshop.product_service.exceptions.*;
import com.onlineshop.product_service.entities.Product;
import com.onlineshop.product_service.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * This class holds all logic to work with products
 */
@Service
@Transactional
public class ProductService {

    private final IProductRepository iProductRepository;
    private final ProductPictureService productPictureService;
    private final OrderService orderService;

    /**
     * Used the autowire the needed beans.
     *
     * @param iProductRepository Bean to store products
     * @param productPictureService Bean to work with product-pictures
     * @param orderService Bean to communicate to the order-service
     */
    @Autowired
    public ProductService(IProductRepository iProductRepository,
                          ProductPictureService productPictureService,
                          OrderService orderService) {
        this.iProductRepository = iProductRepository;
        this.productPictureService = productPictureService;
        this.orderService = orderService;
    }

    /**
     * Creates a product in the database from the given product-data and returns the product with its id.
     *
     * @param product The product to be created.
     * @return The created product with id
     */
    public Product createProduct(Product product) {
        validateProduct(product);

        return this.iProductRepository.save(product);
    }

    /**
     * Reads the product from the database and returns it.
     *
     * @param productTd The id of the product which should be read
     * @return The product with the corresponding id
     * @throws ProductDoesntExistsException
     */
    public Product readProductById(Long productTd) {
        return this.iProductRepository.findById(productTd).orElseThrow(() -> new ProductDoesntExistsException(productTd));
    }

    /**
     * Used to read all products from the database.
     *
     * @return List of all products in the database
     */
    public List<Product> readAllProducts() {
        return this.iProductRepository.findAll();
    }

    /**
     * This method updates a product with the given prodcut information.
     * If the product is referenced in a order a new one will be created and appended to the existing one.
     * If it is in no order the product will simply be updated.
     *
     * @param productId The id of thr product to update
     * @param updatedProduct The prodcut object containing the new prodcut-data
     * @return The updated product
     */
    public Product updateProduct(Long productId,
                                 Product updatedProduct) {
        validateProduct(updatedProduct);

        Product unchangedProduct = readProductById(productId);

        if (!checkProductExistsInOrder(productId)) {
            updatedProduct.setId(productId);
            updatedProduct.setName(unchangedProduct.getName());

            if (updatedProduct.getProductPicture() != null) {
                ProductPicture updatedProductPicture = this.productPictureService.updateProductPicture(unchangedProduct.getProductPicture().getId(),
                    updatedProduct.getProductPicture());
                updatedProduct.setProductPicture(updatedProductPicture);
            } else {
                updatedProduct.setProductPicture(unchangedProduct.getProductPicture());
            }
            updatedProduct = this.iProductRepository.save(updatedProduct);
        } else {
            ProductPicture updatedProductPicture = new ProductPicture();

            if (updatedProduct.getProductPicture() != null) {
                updatedProductPicture = updatedProduct.getProductPicture();
            }

            updatedProductPicture = this.productPictureService.createProductPicture(updatedProductPicture);
            updatedProduct.setProductPicture(updatedProductPicture);
            updatedProduct = this.iProductRepository.save(updatedProduct);
            unchangedProduct.setNewProductVersion(updatedProduct);

            this.iProductRepository.save(unchangedProduct);
        }

        return updatedProduct;
    }

    /**
     * Function to change the available quantity of a product.
     *
     * @param productId The id of the product to change the quantity on
     * @param amount The amount of quantity to be changed
     * @return The product with the updated quantity
     */
    public Product changeQuantity(Long productId, int amount) {
        Product product = readProductById(productId);
        product.changeQuantity(amount);

        return this.iProductRepository.save(product);
    }

    /**
     * Deletes a product by the given id.
     *
     * @param productId The id of the product to be deleted
     */
    public void deleteProductById(Long productId) {
        if (!checkProductExistsById(productId))
            throw new ProductDoesntExistsException(productId);
        if (checkProductExistsInOrder(productId))
            throw new ProductExistsInOrderException(productId);

        this.iProductRepository.deleteById(productId);
    }

    /**
     * This function is used to order an product.
     * It will check if the product has the needed quantity to be ordered.
     * If it can be ordered the wanted product is returned with the given quantity and also the product quantity in
     * the database is updated.
     *
     * @param productId The product which should be ordered
     * @param amount The amount to be ordered
     * @return The ordered product
     * @throws CloneNotSupportedException
     */
    public Product orderProduct(Long productId, int amount) throws CloneNotSupportedException {
        Product dbProduct = readProductById(productId);

        Product orderedProduct = (Product) dbProduct.clone();
        orderedProduct.setQuantity(amount);

        dbProduct.changeQuantity(-amount);

        this.iProductRepository.save(dbProduct);

        return orderedProduct;
    }

    /**
     * Checks if the product matches certain requirements.
     * Name not empty, quantity and price not below 0.
     *
     * @param product The product which should be checked
     */
    public void validateProduct(Product product) {
        if (product.getName().isEmpty())
            throw new ProductNameEmptyException(product);
        if (product.getQuantity() < 0)
            throw new ProdcutQuantityNegativeException(product.getQuantity());
        if (product.getPrice() < 0)
            throw new ProductPriceNegativeException(product);
    }

    private boolean checkProductExistsById(Long productId) {
        return this.iProductRepository.existsById(productId);
    }

    private boolean checkProductExistsInOrder(Long productId) {
        return this.orderService.existsProductInOrder(productId);
    }

}
