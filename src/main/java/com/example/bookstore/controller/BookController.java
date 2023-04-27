package com.example.bookstore.controller;

import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/books")
@Api(value = "BookController", description = "REST API for managing books")
public class BookController {

    // Injecting dependencies
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookService bookService;

    // Adds a book
    @PostMapping
    @ApiOperation(value = "Add a book")
    public ResponseEntity<Object> addBook(
            @ApiParam(value = "Book object to be added", required = true)
            @Valid @RequestBody Book book) {
        bookService.createBook(book);
        return new ResponseEntity<>("Book added successfully", HttpStatus.OK);
    }

    // Deletes a book by ID
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a book by ID")
    public ResponseEntity<String> deleteBook(
            @ApiParam(value = "Book ID to be deleted", required = true)
            @PathVariable Long id) {
        bookService.deleteById(id);
        return new ResponseEntity<>("Book deleted successfully", HttpStatus.OK);
    }

    // Retrieves a book by ID
    @GetMapping("/{id}")
    @ApiOperation(value = "Get a book by ID")
    public ResponseEntity<Book> getBookById(
            @ApiParam(value = "Book ID to retrieve", required = true)
            @PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    // Searches books by title
    @GetMapping("/title/{title}")
    @ApiOperation(value = "Search books by title")
    public ResponseEntity<List<Book>> searchBooksByName(
            @ApiParam(value = "Title to search for", required = true)
            @PathVariable String title) {
        List<Book> books = bookService.findByTitleContainingIgnoreCase(title);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // Retrieves books by category
    @GetMapping("/category/{category}")
    @ApiOperation(value = "Get books by category")
    public ResponseEntity<List<Book>> getBooksByCategory(
            @ApiParam(value = "Category to retrieve books for", required = true)
            @PathVariable String category) {
        List<Book> books = bookService.getBooksByCategory(category);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // Retrieves books by author
    @GetMapping("/author/{author}")
    @ApiOperation(value = "Get books by author")
    public ResponseEntity<List<Book>> getBooksByAuthor(
            @ApiParam(value = "Author to retrieve books for", required = true)
            @PathVariable String author) {
        List<Book> books = bookService.getBooksByAuthor(author);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // Retrieves all books
    @GetMapping
    @ApiOperation(value = "Get all books")
    public List<Book> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return books;
    }
}
