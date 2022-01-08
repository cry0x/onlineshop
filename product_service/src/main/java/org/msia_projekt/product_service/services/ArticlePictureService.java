package org.msia_projekt.product_service.services;

import org.msia_projekt.product_service.repositories.IArticlePictureRepository;
import org.msia_projekt.product_service.entities.ArticlePicture;
import org.msia_projekt.product_service.exceptions.ArticlePictureDoesntExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticlePictureService {

    private final IArticlePictureRepository articlePictureRepository;

    @Autowired
    public ArticlePictureService(IArticlePictureRepository articlePictureRepository) {
        this.articlePictureRepository = articlePictureRepository;
    }

    public ArticlePicture createArticlePicture(ArticlePicture articlePicture) {
        return this.articlePictureRepository.save(articlePicture);
    }

    public ArticlePicture readArticlePictureById(Long id) {
        return this.articlePictureRepository.findById(id).orElseThrow(() -> new ArticlePictureDoesntExistException(id));
    }

    public List<ArticlePicture> readAllArticlePictures() {
        return this.articlePictureRepository.findAll();
    }

    public void deleteArticlePictureById(Long id) {
        this.articlePictureRepository.deleteById(id);
    }

    public ArticlePicture updateArticlePicture(Long id, ArticlePicture updatedArticlePicture) {
        if (!this.articlePictureRepository.existsById(id))
            throw new ArticlePictureDoesntExistException(id);

        updatedArticlePicture.setId(id);

        return this.articlePictureRepository.save(updatedArticlePicture);
    }

}
