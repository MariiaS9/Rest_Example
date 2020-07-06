package org.my.rest.serveces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.my.rest.models.Book;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookService();
    }

    @Test
    void getAll() {
        List<Book> result = bookService.getAll();
        assertEquals(3, result.size());
    }

    @Test
    void getById() {
        Book result = bookService.getById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void deleteOne() {
        Book result = bookService.deleteOne(2L);
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals(2, bookService.getAll().size());
    }

    @Test
    void addNew() {
        Book testBook= new Book(null,"History","hard");
        Book result = bookService.addNew(testBook);
        assertNotNull(result);
        assertEquals(4L, result.getId());
        assertEquals(4, bookService.getAll().size());
    }
}