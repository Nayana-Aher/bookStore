package com.example.bookstore.model;

import com.sun.istack.NotNull;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min=2, max=50)
    @Column(nullable = false,name = "title")
    private String title;

    @Size(min=2, max=50)
    @Column(nullable = false,name = "author")
    private String author;

    @NotNull
    @Column(nullable = false,name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Min(0)
    @Column(nullable = false,name = "price")
    private double price;

    public Book() {}

    public Book(String title, String author, Category category, double price) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
