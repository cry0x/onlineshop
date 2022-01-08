package org.msia_projekt.product_service.services;

import org.msia_projekt.product_service.repositories.IArticlePictureRepository;
import org.msia_projekt.product_service.entities.ArticlePicture;
import org.msia_projekt.product_service.exceptions.ArticlePictureDoesntExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticlePictureService {

    private final IArticlePictureRepository iArticlePictureRepository;

    @Autowired
    public ArticlePictureService(IArticlePictureRepository iArticlePictureRepository) {
        this.iArticlePictureRepository = iArticlePictureRepository;
    }

    public ArticlePicture createArticlePicture(ArticlePicture articlePicture) {
        return this.iArticlePictureRepository.save(articlePicture);
    }

    public ArticlePicture readArticlePictureById(Long id) {
        return this.iArticlePictureRepository.findById(id).orElseThrow(() -> new ArticlePictureDoesntExistException(id));
    }

    public List<ArticlePicture> readAllArticlePictures() {
        return this.iArticlePictureRepository.findAll();
    }

    public void deleteArticlePictureById(Long id) {
        this.iArticlePictureRepository.deleteById(id);
    }

    public ArticlePicture updateArticlePicture(Long id, ArticlePicture updatedArticlePicture) {
        if (!this.iArticlePictureRepository.existsById(id))
            throw new ArticlePictureDoesntExistException(id);

        updatedArticlePicture.setId(id);

        return this.iArticlePictureRepository.save(updatedArticlePicture);
    }

}
