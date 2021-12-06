package org.msia_projekt.product_service.entities;

import javax.persistence.*;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String picture;
    private double price;
    private int stock;

    public Article() {

    }

    public Article(String name,
                   String description,
                   String picture,
                   double price,
                   int stock) {
        setName(name);
        setDescription(description);
        setPicture(picture);
        setPrice(price);
        setStock(stock);
    }

    public Article(long id,
                   String name,
                   String description,
                   String picture,
                   double price,
                   int stock) {
        this(name, description, picture, price, stock);

        setId(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
