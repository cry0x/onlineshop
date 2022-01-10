package org.msia_projekt.product_service.controllers;

import org.msia_projekt.product_service.entities.ArticlePicture;
import org.msia_projekt.product_service.services.ArticlePictureService;
import org.msia_projekt.product_service.utilities.HateoasUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/articlepictures")
public class ArticlePictureController {

    private final ArticlePictureService articlePictureService;
    private final static Logger log = LoggerFactory.getLogger(ArticlePictureController.class);

    @Autowired
    public ArticlePictureController(ArticlePictureService articlePictureService) {
        this.articlePictureService = articlePictureService;
    }

    @GetMapping(value = "/{articlePictureId}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<ArticlePicture> getArticlePicture(@PathVariable Long articlePictureId) {
        log.info(String.format("GET: /v1/articlepictures/%d has been called", articlePictureId));

        ArticlePicture articlePicture = this.articlePictureService.readArticlePictureById(articlePictureId);

        return HateoasUtilities.buildArticlePictureEntity(articlePicture);
    }

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<ArticlePicture>> getAllArticlePictures() {
        log.info("GET: /v1/articlepictures has been called");

        return HateoasUtilities.buildArticlePictureCollection(this.articlePictureService.readAllArticlePictures());
    }

}
