package com.onlineshop.product_service.services;

import com.onlineshop.product_service.exceptions.ProductPictureExistsInProduct;
import com.onlineshop.product_service.repositories.IProductPictureRepository;
import com.onlineshop.product_service.entities.ProductPicture;
import com.onlineshop.product_service.exceptions.ProductPictureDoesntExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductPictureService {

    private final IProductPictureRepository iProductPictureRepository;
    private final ProductService productService;

    @Autowired
    public ProductPictureService(IProductPictureRepository iProductPictureRepository,
                                 ProductService productService) {
        this.iProductPictureRepository = iProductPictureRepository;
        this.productService = productService;
    }

    public ProductPicture createProductPicture(ProductPicture productPicture) {
        return this.iProductPictureRepository.save(productPicture);
    }

    public ProductPicture readProductPictureById(Long id) {
        return this.iProductPictureRepository.findById(id).orElseThrow(() -> new ProductPictureDoesntExistException(id));
    }

    public List<ProductPicture> readAllProductPictures() {
        return this.iProductPictureRepository.findAll();
    }

    public void deleteProductPictureById(Long id) {
        if (this.productService.existsProductPictureInProduct(id))
            throw new ProductPictureExistsInProduct();

        this.iProductPictureRepository.deleteById(id);
    }

    public ProductPicture updateProductPicture(Long id, ProductPicture updatedProductPicture) {
        if (!this.iProductPictureRepository.existsById(id))
            throw new ProductPictureDoesntExistException(id);

        updatedProductPicture.setId(id);

        return this.iProductPictureRepository.save(updatedProductPicture);
    }

}
