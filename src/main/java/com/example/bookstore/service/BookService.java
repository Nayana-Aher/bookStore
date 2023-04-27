
/**
 * This class represents the business logic for Book operations, such as adding, deleting, searching for books, etc.
 * */

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

    /**
     * Constructor for creating a BookService instance with a given BookRepository instance.
     * @param bookRepository An instance of BookRepository for handling data access to the database.
     */
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Adds a book to the database.
     * @param book The book object to be added to the database.
     * @throws BookAlreadyExistsException if a book with the same title and author already exists in the database.
     */
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

    /**
     * Retrieves all books from the database.
     * @return A list of all books.
     * @throws BookNotFoundException if no books are found in the database.
     */
    public List<Book> getAllBooks() {
        List<Book> books= bookRepository.findAll();
        if (books.isEmpty()) {
            throw new BookNotFoundException("No Book Found");
        }
        return books;
    }

    /**
     * Retrieves all books from the database belonging to a specific category.
     * @param category The category of books to be retrieved.
     * @return A list of all books belonging to the specified category.
     * @throws CategoryNotFoundException if the specified category is invalid.
     * @throws BookNotFoundException if no books are found in the database for the specified category.
     */
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

    /**
     * Retrieves all books from the database written by a specific author.
     * @param authorName The name of the author.
     * @return A list of all books written by the specified author.
     * @throws BookNotFoundException if no books are found in the database written by the specified author.
     */
    public List<Book> getBooksByAuthor(String authorName) {
        List<Book> books = bookRepository.findByAuthorContainingIgnoreCase(authorName);
        if (books.isEmpty()) {
            throw new BookNotFoundException("No books found for Author: " + authorName);
        }
        return bookRepository.findByAuthorContainingIgnoreCase(authorName);
    }


    /**
     * Retrieves a book from the database with a specific id.
     * @param id The id of books to be retrieved.
     * @return A book belonging to the specified id.
     * @throws BookNotFoundException if no books are found in the database for the specified id.
     */
    public Book getBookById(Long id) throws BookNotFoundException {
        return bookRepository.findById(id).orElseThrow(() ->
                new BookNotFoundException("Book not found with id " +id));
    }

    /**
     * Takes a keyword as an argument and
     * returns a list of Book entities that contain the keyword in their title, or
     * throws a BookNotFoundException if no entities are found.
     */
    public List<Book> findByTitleContainingIgnoreCase(String keyword) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(keyword);
        if (books.isEmpty()) {
            throw new BookNotFoundException("No book found with name: " + keyword);
        }
        return books;
    }

    /**
     * Deletes a book from the database by its ID.
     * @param id The ID of the book to be deleted.
     * @throws BookNotFoundException if the book with the given ID is not found in the database.
     */
    public void deleteById(Long id) {
        if (!bookRepository.findById(id).isPresent()) {
            throw new BookNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }
}




