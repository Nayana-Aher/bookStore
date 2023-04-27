
// This file defines the BookRepository interface which extends JpaRepository interface.
// JpaRepository provides methods for basic CRUD operations on entities.
// This interface provides additional methods specific to the Book entity.


package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByCategory(Category category);
    List<Book> findAll();
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorContainingIgnoreCase(String authorName);
    boolean existsByTitleAndAuthor(String title, String author);

}


