package org.msia_projekt.product_service.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.msia_projekt.product_service.DefaultBase64ProductPicture;

import javax.persistence.*;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    @Column(columnDefinition="TEXT")
    private String picture = DefaultBase64ProductPicture.getDefaultBase64ProductPicture();
    private double price;
    private int stock;

    public Article() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        return new EqualsBuilder()
                .append(getPrice(), article.getPrice())
                .append(getStock(), article.getStock())
                .append(getId(), article.getId())
                .append(getName(), article.getName())
                .append(getDescription(), article.getDescription())
                .append(getPicture(), article.getPicture())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(getName())
                .append(getDescription())
                .append(getPicture())
                .append(getPrice())
                .append(getStock())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", picture='" + picture + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
