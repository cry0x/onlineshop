package com.onlineshop.product_service.services;

import com.onlineshop.product_service.clients.IOrderServiceClient;
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
    private final IOrderServiceClient iOrderServiceClient;

    @Autowired
    public ProductService(IProductRepository iProductRepository,
                          ProductPictureService productPictureService,
                          IOrderServiceClient iOrderServiceClient) {
        this.iProductRepository = iProductRepository;
        this.productPictureService = productPictureService;
        this.iOrderServiceClient = iOrderServiceClient;
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
        //wird der check hier benötigt?
        checkProductExistsById(productId);

        Product unchangedProduct = readProductById(productId);

        if (this.iOrderServiceClient.getIsProductInOrders(unchangedProduct.getId())) {
            updatedProduct.setName(unchangedProduct.getName());
            updatedProduct = this.iProductRepository.save(updatedProduct);
            unchangedProduct.setNewProductVersion(updatedProduct);
            this.iProductRepository.save(unchangedProduct);
        } else {
            updatedProduct.setId(productId);
            updatedProduct.setName(unchangedProduct.getName());

            if (updatedProduct.getProductPicture() != null) {
                ProductPicture updatedProductPicture = this.productPictureService.updateProductPicture(unchangedProduct.getProductPicture().getId(), updatedProduct.getProductPicture());
                updatedProduct.setProductPicture(updatedProductPicture);
            }
            updatedProduct = this.iProductRepository.save(updatedProduct);
        }

        return updatedProduct;
    }

    public void deleteProductById(Long productId) {
        checkProductExistsById(productId);

        this.iProductRepository.deleteById(productId);
    }

    private void checkProductExistsById(Long productId) {
        if (!this.iProductRepository.existsById(productId))
            throw new ProductDoesntExistsException(productId);
    }

}
