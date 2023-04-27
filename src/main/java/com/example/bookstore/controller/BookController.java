package com.example.bookstore.controller;

import com.example.bookstore.exception.BookAlreadyExistsException;
import com.example.bookstore.exception.BookNotFoundException;
import com.example.bookstore.exception.CategoryNotFoundException;
import com.example.bookstore.exception.ErrorResponse;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Category;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.service.BookService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;

    private final BookService bookService;

    public BookController(BookRepository bookRepository, BookService bookService) {
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Object> addBook( @Valid @RequestBody Book book)  {
        boolean exists = bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor());
        if (exists) {
            throw new BookAlreadyExistsException("Book with title '" + book.getTitle() + "' and author '" + book.getAuthor() + "' already exists.");
        }
        try {
            bookRepository.save(book);
            return new ResponseEntity<>("Book added successfully", HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        if (!bookService.existsById(id)) {
            throw new BookNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
        return new ResponseEntity<>("Book deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            throw new BookNotFoundException("Book not found with id: " + id);
        }else{
            return new ResponseEntity<>(book, HttpStatus.OK);
        }
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Book>> searchBooksByName(@PathVariable String title) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        if (books.isEmpty()) {
            throw new BookNotFoundException("No books found with name: " + title);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Book>> getBooksByCategory(@PathVariable String category) {
        String queryCategory=category.toString();
        if (!Category.isValid(queryCategory)) {
            throw new CategoryNotFoundException("Invalid category: " + category);
        }
        List<Book> books = bookService.getBooksByCategory(Category.valueOf(category));
        if (books.isEmpty()) {
            throw new BookNotFoundException("No books found for category: " + category);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable String author) {
        List<Book> books = bookRepository.findByAuthorContainingIgnoreCase(author);
        if (books.isEmpty()) {
            throw new BookNotFoundException("No books found for Author: " + author);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        List<Book> books =bookService.getAllBooks();
        if (books.isEmpty()) {
            throw new BookNotFoundException("No book found");
        }
        return books;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(HttpServletRequest request, DataIntegrityViolationException ex) {
        String errorMessage = "InValid request";
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),errorMessage , LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

