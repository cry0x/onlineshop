package com.onlineshop.product_service.services;

import com.onlineshop.product_service.entities.ProductPicture;
import com.onlineshop.product_service.exceptions.ProductDoesntExistsException;
import com.onlineshop.product_service.entities.Product;
import com.onlineshop.product_service.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final IProductRepository iProductRepository;
    private final ProductPictureService productPictureService;

    @Autowired
    public ProductService(IProductRepository iProductRepository, ProductPictureService productPictureService) {
        this.iProductRepository = iProductRepository;
        this.productPictureService = productPictureService;
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

    public Product updateProduct(Long productId, Product updatedProduct) {
        checkProductExistsById(productId);

        Product unchangedProduct = readProductById(productId);

        updatedProduct.setId(productId);
        updatedProduct.setName(unchangedProduct.getName());

        ProductPicture updatedProductPicture = this.productPictureService.updateProductPicture(unchangedProduct.getProductPicture().getId(), updatedProduct.getProductPicture());
        updatedProduct.setProductPicture(updatedProductPicture);

        return this.iProductRepository.save(updatedProduct);
    }

    public void deleteProductById(Long productId) {
        checkProductExistsById(productId);

        this.iProductRepository.deleteById(productId);
    }

    private final void checkProductExistsById(Long productId) {
        if (!this.iProductRepository.existsById(productId))
            throw new ProductDoesntExistsException(productId);
    }

}
