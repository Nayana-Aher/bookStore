package com.example.bookstore.service;

import com.example.bookstore.controller.exception.BookNotFoundException;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Category;
import com.example.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByCategory(Category category) {
        return bookRepository.findByCategory(category);
    }

    public List<Book> getBooksByAuthor(String authorName) {
        return bookRepository.findByAuthor(authorName);
    }

    public Book getBookById(Long id) throws BookNotFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    public Book updateBook(Long id, Book book) throws BookNotFoundException {
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setCategory(book.getCategory());
        existingBook.setPrice(book.getPrice());
        return bookRepository.save(existingBook);
    }

    public void deleteBook(Long id) throws BookNotFoundException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        bookRepository.delete(book);
    }

    public List<Book> searchBooks(String keyword) {

        return bookRepository.findByTitleContainingIgnoreCase(keyword);

    }
}




