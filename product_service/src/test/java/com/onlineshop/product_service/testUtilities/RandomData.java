package com.onlineshop.product_service.testUtilities;

import com.onlineshop.product_service.entities.Article;
import com.onlineshop.product_service.entities.ArticlePicture;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomData {

    public static byte[] RandomByteArray(int size) {
        Random random = new Random();
        byte[] bytes = new byte[size];
        random.nextBytes(bytes);

        return bytes;
    }

    public static String RandomString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static Long RandomLong() {
        return new Random().nextLong();
    }

    public static int RandomInt() {
        return new Random().nextInt();
    }

    public static double RandomDouble() {
        return new Random().nextDouble();
    }

    public static ArticlePicture RandomArticlePictureWithoutId() {
        ArticlePicture articlePicture = new ArticlePicture();
        articlePicture.setName(RandomString(15));
        articlePicture.setData(RandomByteArray(50));

        return articlePicture;
    }

    public static ArticlePicture RandomArticlePicture() {
        ArticlePicture articlePicture = new ArticlePicture();
        articlePicture.setId(RandomLong());
        articlePicture.setName(RandomString(15));
        articlePicture.setData(RandomByteArray(50));

        return articlePicture;
    }

    public static List<ArticlePicture> RandomArticlePictureList(int size) {
        List<ArticlePicture> articlePictureList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            articlePictureList.add(RandomArticlePicture());
        }

        return articlePictureList;
    }

    public static Article RandomArticleWithoutId() {
        Article article = new Article();
        article.setName(RandomString(15));
        article.setDescription(RandomString(50));
        article.setQuantity(RandomInt());
        article.setPrice(RandomDouble());
        article.setArticlePicture(RandomArticlePictureWithoutId());

        return article;
    }

    public static Article RandomArticle() {
        Article article = new Article();
        article.setId(RandomLong());
        article.setName(RandomString(15));
        article.setDescription(RandomString(50));
        article.setQuantity(RandomInt());
        article.setPrice(RandomDouble());
        article.setArticlePicture(RandomArticlePicture());

        return article;
    }

    public static List<Article> RandomArticleList(int size) {
        List<Article> articleList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            articleList.add(RandomArticle());
        }

        return articleList;
    }


}
