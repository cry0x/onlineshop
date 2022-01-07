package org.msia_projekt.product_service.controllers;

import org.msia_projekt.product_service.entities.ArticlePicture;
import org.msia_projekt.product_service.repositories.IArticlePictureRepository;
import org.msia_projekt.product_service.exceptions.ArticlePictureDoesntExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/v1/articlepictures")
public class ArticlePictureController {

    private final IArticlePictureRepository iArticlePictureRepository;
    private final static Logger log = LoggerFactory.getLogger(ArticlePictureController.class);

    @Autowired
    public ArticlePictureController(IArticlePictureRepository iArticlePictureRepository) {
        this.iArticlePictureRepository = iArticlePictureRepository;
    }

    @GetMapping(value = "/{id}")
    public EntityModel<ArticlePicture> getArticlePicture(@PathVariable Long id) {
        log.info(String.format("GET: /v1/articlepictures/%d has been called", id));

        ArticlePicture articlePicture = this.iArticlePictureRepository.findById(id).orElseThrow(() -> new ArticlePictureDoesntExistException(id));

        return EntityModel.of(articlePicture,
                linkTo(methodOn(ArticlePictureController.class).getArticlePicture(articlePicture.getId())).withSelfRel());
    }

    @GetMapping
    public CollectionModel<EntityModel<ArticlePicture>> getAllArticlePictures() {
        log.info("GET: /v1/articlepictures has been called");

        List<EntityModel<ArticlePicture>> articlePictures = this.iArticlePictureRepository.findAll().stream()
                .map(articlePicture -> EntityModel.of(articlePicture,
                        linkTo(methodOn(ArticlePictureController.class).getArticlePicture(articlePicture.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(articlePictures,
                linkTo(methodOn(ArticlePictureController.class).getAllArticlePictures()).withSelfRel());
    }

}
