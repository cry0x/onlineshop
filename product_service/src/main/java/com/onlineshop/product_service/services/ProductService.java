package com.onlineshop.product_service.services;

import com.onlineshop.product_service.entities.ProductPicture;
import com.onlineshop.product_service.exceptions.*;
import com.onlineshop.product_service.entities.Product;
import com.onlineshop.product_service.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final IProductRepository iProductRepository;
    private final ProductPictureService productPictureService;
    private final OrderService orderService;

    @Autowired
    public ProductService(IProductRepository iProductRepository,
                          ProductPictureService productPictureService,
                          OrderService orderService) {
        this.iProductRepository = iProductRepository;
        this.productPictureService = productPictureService;
        this.orderService = orderService;
    }

    public Product createProduct(Product product) {
        validateProduct(product);

        return this.iProductRepository.save(product);
    }

    public Product readProductById(Long id) {
        return this.iProductRepository.findById(id).orElseThrow(() -> new ProductDoesntExistsException(id));
    }

    public List<Product> readAllProducts() {
        return this.iProductRepository.findAll();
    }

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

    public Product changeQuantity(Long productId, int amount) {
        Product product = readProductById(productId);
        product.changeQuantity(amount);

        return this.iProductRepository.save(product);
    }

    public void deleteProductById(Long productId) {
        if (!checkProductExistsById(productId))
            throw new ProductDoesntExistsException(productId);
        if (checkProductExistsInOrder(productId))
            throw new ProductExistsInOrderException(productId);

        this.iProductRepository.deleteById(productId);
    }

    private boolean checkProductExistsById(Long productId) {
        return this.iProductRepository.existsById(productId);
    }

    private boolean checkProductExistsInOrder(Long productId) {
        return this.orderService.existsProductInOrder(productId);
    }

    public void validateProduct(Product product) {
        if (product.getName().isEmpty())
            throw new ProductNameEmptyException(product);
        if (product.getQuantity() < 0)
            throw new ProdcutQuantityNegativeException(product.getQuantity());
        if (product.getPrice() < 0)
            throw new ProductPriceNegativeException(product);
    }

}
