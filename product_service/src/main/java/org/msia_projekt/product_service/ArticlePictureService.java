package org.msia_projekt.product_service;

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

    public void deleteArticlePicture(ArticlePicture articlePicture) {
        this.articlePictureRepository.delete(articlePicture);
    }

    public void deleteArticlePictureById(Long id) {
        this.articlePictureRepository.deleteById(id);
    }

}
