package com.onlineshop.product_service.services;

import com.onlineshop.product_service.entities.ProductPicture;
import com.onlineshop.product_service.exceptions.ProductDoesntExistsException;
import com.onlineshop.product_service.entities.Product;
import com.onlineshop.product_service.exceptions.ProductExistsInOrderException;
import com.onlineshop.product_service.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
        Product unchangedProduct = readProductById(productId);

        if (checkProductExistsInOrder(productId)) {
            updatedProduct.setName(unchangedProduct.getName());
            updatedProduct = this.iProductRepository.save(updatedProduct);
            unchangedProduct.setNewProductVersion(updatedProduct);
            this.iProductRepository.save(unchangedProduct);
        } else {
            updatedProduct.setId(productId);
            updatedProduct.setName(unchangedProduct.getName());

            if (updatedProduct.getProductPicture() != null) {
                ProductPicture updatedProductPicture = this.productPictureService.updateProductPicture(unchangedProduct.getProductPicture().getId(),
                        updatedProduct.getProductPicture());
                updatedProduct.setProductPicture(updatedProductPicture);
            }
            updatedProduct = this.iProductRepository.save(updatedProduct);
        }

        return updatedProduct;
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

}
