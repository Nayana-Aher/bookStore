package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByCategory(Category category);
    List<Book> findAll();
    List<Book> findByTitleContainingIgnoreCase(String title);
    boolean existsByTitleAndAuthor(String title, String author);
    List<Book> findByAuthorContainingIgnoreCase(String authorName);
}


