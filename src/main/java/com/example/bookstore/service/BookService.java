package com.example.bookstore.service;

import com.example.bookstore.exception.BookAlreadyExistsException;
import com.example.bookstore.exception.BookNotFoundException;
import com.example.bookstore.exception.CategoryNotFoundException;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Category;
import com.example.bookstore.repository.BookRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void createBook(Book book) {
        boolean exists = bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor());
        if (exists) {
            throw new BookAlreadyExistsException("Book with title '" + book.getTitle() +
                    "' and author '" + book.getAuthor() + "' already exists.");
        }
        try {
            bookRepository.save(book);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Book> getAllBooks() {
        List<Book> books= bookRepository.findAll();
        if (books.isEmpty()) {
            throw new BookNotFoundException("No book found");
        }
        return books;
    }

    public List<Book> getBooksByCategory(String category) {
       if (!Category.isValid(category)) {
            throw new CategoryNotFoundException("Invalid category: " + category);
       }
       List<Book> books = bookRepository.findByCategory(Category.valueOf(category));
        if (books.isEmpty()) {
            throw new BookNotFoundException("No books found for category: " + category);
        }
        return books;
    }

    public List<Book> getBooksByAuthor(String authorName) {
        List<Book> books = bookRepository.findByAuthorContainingIgnoreCase(authorName);
        if (books.isEmpty()) {
            throw new BookNotFoundException("No books found for Author: " + authorName);
        }
        return bookRepository.findByAuthorContainingIgnoreCase(authorName);
    }

    public Book getBookById(Long id) throws BookNotFoundException {
        return bookRepository.findById(id).orElseThrow(() ->
                new BookNotFoundException("Book not found with id " +id));
    }

    public List<Book> findByTitleContainingIgnoreCase(String keyword) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(keyword);
        if (books.isEmpty()) {
            throw new BookNotFoundException("No book found with name: " + keyword);
        }
        return books;
    }

    public void deleteById(Long id) {
        if (!bookRepository.findById(id).isPresent()) {
            throw new BookNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }
}




