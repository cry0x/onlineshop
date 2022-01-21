package com.onlineshop.product_service.services;

import com.onlineshop.product_service.exceptions.ProductPictureExistsInProduct;
import com.onlineshop.product_service.repositories.IProductPictureRepository;
import com.onlineshop.product_service.entities.ProductPicture;
import com.onlineshop.product_service.exceptions.ProductPictureDoesntExistException;
import com.onlineshop.product_service.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductPictureService {

    private final IProductPictureRepository iProductPictureRepository;
    private final IProductRepository iProductRepository;

    @Autowired
    public ProductPictureService(IProductPictureRepository iProductPictureRepository,
                                 IProductRepository iProductRepository) {
        this.iProductPictureRepository = iProductPictureRepository;
        this.iProductRepository = iProductRepository;
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
        if (this.iProductRepository.existsProductPictureInProduct(id))
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
