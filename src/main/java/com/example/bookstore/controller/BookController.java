package com.example.bookstore.controller;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.Category;
import com.example.bookstore.controller.exception.BookNotFoundException;
import com.example.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    // Add new book
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book savedBook = bookRepository.save(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    // Delete a book by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book not found with ID " + id);
        }
        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Get a book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID " + id));
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    // Get books by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Book>> getBooksByCategory(@PathVariable String category) {
        List<Book> books = bookRepository.findByCategory(Category.valueOf(category));
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // Get books by author
    @GetMapping("/author/{author}")
    public ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable String author) {
        List<Book> books = bookRepository.findByAuthor(author);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // Get all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // Search book by name
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooksByName(@RequestParam String name) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(name);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}
