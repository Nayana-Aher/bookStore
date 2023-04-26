package com.example.bookstore.service;

import com.example.bookstore.exception.BookNotFoundException;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Category;
import com.example.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createBook(Book book) {

        Book savedBook = bookRepository.save(book);
        if (savedBook == null) {
            System.out.println("Failed to save book: " + book);
        } else {
            System.out.println("Saved employee: " + savedBook);
        }
        return savedBook;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByCategory(Category category) {
        return bookRepository.findByCategory(category);
    }

    public List<Book> getBooksByAuthor(String authorName) {

        return bookRepository.findByAuthorContainingIgnoreCase(authorName);
    }

    public Book getBookById(Long id) throws BookNotFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with id " +id));
    }

    public void deleteBook(Long id) throws BookNotFoundException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with id " +id));
        bookRepository.delete(book);
    }

    public List<Book> searchBooks(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public boolean existsById(Long id) {
        return bookRepository.findById(id).isPresent();
    }

}




