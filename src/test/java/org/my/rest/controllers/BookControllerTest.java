package org.my.rest.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.my.rest.models.Book;
import org.my.rest.serveces.BookService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookService service;

    @InjectMocks
    private BookController controller;

    private Map<Long, Book> map;

    @BeforeEach
    void setUp() {
        this.map = new HashMap<>();
        map.put(1L, new Book(1L, "Skazki", "soft"));
        map.put(2L, new Book(2L, "Horor", "hard"));
        map.put(3L, new Book(3L, "Roman", "soft"));

    }

    @Test
    void getAll() {
        when(service.getAll()).thenReturn(new ArrayList<>(map.values()));
        List<Book> result = controller.getAll(null, null);
        verify(service).getAll();
        assertEquals(3, result.size());
    }

    @Test
    void getAllCaseTitleNotNull() {
        when(service.getAll()).thenReturn(new ArrayList<>(map.values()));
        List<Book> result = controller.getAll("Horor", null);

        verify(service).getAll();
        assertEquals(1, result.size());
        assertEquals("Horor", result.get(0).getTitle());
    }

    @Test
    void getAllCaseCoverNotNull() {
        when(service.getAll()).thenReturn(new ArrayList<>(map.values()));
        List<Book> result = controller.getAll(null, "hard");
        verify(service).getAll();
        assertEquals("hard", result.get(0).getCover());
        assertEquals(1, result.size());
    }

    @Test
    void getAllCaseInvalidCover() {
        when(service.getAll()).thenReturn(new ArrayList<>(map.values()));
        List<Book> result = controller.getAll(null, "booo");
        verify(service).getAll();
        assertEquals(0, result.size());
    }

    @Test
    void getAllCaseInvalidTitle() {
        when(service.getAll()).thenReturn(new ArrayList<>(map.values()));
        List<Book> result = controller.getAll("fooo", null);
        verify(service).getAll();
        assertEquals(0, result.size());
    }

    @Test
    void getAllCaseBothNotNull() {
        when(service.getAll()).thenReturn(new ArrayList<>(map.values()));
        List<Book> result = controller.getAll("Horor", "hard");
        verify(service).getAll();
        assertEquals(1, result.size());
        assertEquals("hard", result.get(0).getCover());
        assertEquals("Horor", result.get(0).getTitle());
    }

    @Test
    void getOne() {
        Long testId = 2L;
        when(service.getById(testId)).thenReturn(map.get(testId));

        Book result = controller.getOne(testId);
        verify(service).getById(testId);
        assertEquals(new Long(testId), result.getId());
    }

    @Test
    void create() {
        Book testBook = new Book(null, "Comedy", "hard");
        when(service.addNew(testBook)).thenReturn(testBook);

        Book result = controller.create(testBook);
        verify(service).addNew(testBook);
        assertNotNull(result);
    }

    @Test
    void deletOne() {
        Long testId = 1L;
        controller.deletOne(testId);
        verify(service).deleteOne(testId);
    }

    @Test
    void modifyOne() {
        Book modifiedBook = new Book(10L, "Roman", "soft");
        Book legacyBook = new Book(10L, "Vasya", "hard");
        when(service.getById(modifiedBook.getId())).thenReturn(legacyBook);
        when(service.overrideBook(modifiedBook)).thenReturn(modifiedBook);

        Book result = controller.modifyOne(modifiedBook.getId(), modifiedBook);
        verify(service).getById(modifiedBook.getId());
        verify(service).overrideBook(modifiedBook);
        assertEquals(modifiedBook, result);
    }
}