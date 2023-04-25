package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findById(Long id);
    List<Book> findByCategory(Category category);
    List<Book> findByAuthor(String author);
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    void delete(Book book);
    @Override
    Book save(Book book);
}


