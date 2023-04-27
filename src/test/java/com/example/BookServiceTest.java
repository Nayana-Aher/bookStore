package com.example;

import com.example.bookstore.exception.BookAlreadyExistsException;
import com.example.bookstore.exception.BookNotFoundException;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.Category;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static com.example.bookstore.model.Category.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock private BookRepository bookRepository;

    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository);
    }

    @Test void testCreateBook() {
        Book book = new Book("Book Title", "Author Name", Technical, 10.50);
        when(bookRepository.existsByTitleAndAuthor(anyString(), anyString())).thenReturn(false);
        when(bookRepository.save(book)).thenReturn(book);

        bookService.createBook(book);

        verify(bookRepository).existsByTitleAndAuthor(anyString(), anyString());
        verify(bookRepository).save(book);
    }

    @Test void testCreateBookAlreadyExists() {
        Book book = new Book("Book Title", "Author Name", Technical, 10.50);
        when(bookRepository.existsByTitleAndAuthor(anyString(), anyString())).thenReturn(true);

        assertThrows(BookAlreadyExistsException.class, () -> bookService.createBook(book));

        verify(bookRepository).existsByTitleAndAuthor(anyString(), anyString());
        verify(bookRepository, never()).save(book);
    }

    @Test void testCreateBookDataIntegrityViolation() {
        Book book = new Book("Book Title", "Author Name", Technical, 10.50);
        when(bookRepository.existsByTitleAndAuthor(anyString(), anyString())).thenReturn(false);
        when(bookRepository.save(book)).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class, () -> bookService.createBook(book));

        verify(bookRepository).existsByTitleAndAuthor(anyString(), anyString());
        verify(bookRepository).save(book);
    }

    @Test void testGetAllBooks() {
        Book book1 = new Book("Book 1", "Author 1", Technical, 10.50);
        Book book2 = new Book("Book 2", "Author 2", Category.Literature, 20.00);
        List<Book> bookList = Arrays.asList(book1, book2);
        when(bookRepository.findAll()).thenReturn(bookList);

        List<Book> result = bookService.getAllBooks();

        assertEquals(bookList, result);
        verify(bookRepository).findAll();
    }

    @Test void testGetAllBooksNotFound() {
        when(bookRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(BookNotFoundException.class, () -> bookService.getAllBooks());

        verify(bookRepository).findAll();
    }

}
