package com.example.bookstore.controller;

import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.service.BookService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    // Injecting dependencies
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookService bookService;

    // Adds a book
    @PostMapping
    public ResponseEntity<Object> addBook(@Valid @RequestBody Book book) {
        bookService.createBook(book);
        return new ResponseEntity<>("Book added successfully", HttpStatus.OK);
    }

    // Deletes a book by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
        return new ResponseEntity<>("Book deleted successfully", HttpStatus.OK);
    }

    // Retrieves a book by ID
    @GetMapping("/{id}")
    @ApiOperation(value = "Get a book by ID")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    // Searches books by title
    @GetMapping("/title/{title}")
    public ResponseEntity<List<Book>> searchBooksByName(@PathVariable String title) {
        List<Book> books = bookService.findByTitleContainingIgnoreCase(title);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // Retrieves books by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Book>> getBooksByCategory(@PathVariable String category) {
        List<Book> books = bookService.getBooksByCategory(category);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // Retrieves books by author
    @GetMapping("/author/{author}")
    public ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable String author) {
        List<Book> books = bookService.getBooksByAuthor(author);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // Retrieves all books
    @GetMapping
    public List<Book> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return books;
    }
}
