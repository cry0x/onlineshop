package org.msia_projekt.product_service.controllers;

import org.msia_projekt.product_service.entities.ArticlePicture;
import org.msia_projekt.product_service.services.ArticlePictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

        return EntityModel.of(articlePicture,
                linkTo(methodOn(ArticlePictureController.class).getArticlePicture(articlePicture.getId())).withSelfRel());
    }

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<ArticlePicture>> getAllArticlePictures() {
        log.info("GET: /v1/articlepictures has been called");

        List<EntityModel<ArticlePicture>> articlePictures = this.articlePictureService.readAllArticlePictures().stream()
                .map(articlePicture -> EntityModel.of(articlePicture,
                        linkTo(methodOn(ArticlePictureController.class).getArticlePicture(articlePicture.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(articlePictures,
                linkTo(methodOn(ArticlePictureController.class).getAllArticlePictures()).withSelfRel());
    }

}
