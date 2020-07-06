package org.my.rest.controllers;

import io.swagger.annotations.Api;
import org.my.rest.models.Book;
import org.my.rest.serveces.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/books",produces ={"application/json", "application/xml"} )
    public List<Book> getAll(@RequestParam(required = false) String title,
                             @RequestParam(required = false) String cover) {

        if (title != null && cover == null) {
            return bookService.getAll().stream()
                    .filter(book -> book.getTitle().equalsIgnoreCase(title))
                    .collect(Collectors.toList());
        } else if (title == null && cover != null) {
            return bookService.getAll().stream()
                    .filter(book -> book.getCover().equalsIgnoreCase(cover))
                    .collect(Collectors.toList());
        } else if (title != null && cover != null) {
            return bookService.getAll().stream()
                    .filter(book -> book.getTitle().equalsIgnoreCase(title))
                    .filter(book -> book.getCover().equalsIgnoreCase(cover))
                    .collect(Collectors.toList());
        } else {
            return bookService.getAll();
        }
    }

    @GetMapping(value = "/books/{id}", produces = {"application/json", "application/xml"})
    public Book getOne(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @PostMapping(value = "/books",
            consumes ={"application/json", "application/xml"},
            produces ={"application/json", "application/xml"})
    public Book create(@RequestBody Book book) {
        return bookService.addNew(book);
    }

//    @PostMapping(value = "/xml/books", consumes = {"application/xml"}, produces = {"application/xml"})
//    public Book createFromXml(@RequestBody Book book) {
//        return bookService.addNew(book);
//    }

    @DeleteMapping("/books/{id}")
    public void deletOne(@PathVariable Long id) {
        bookService.deleteOne(id);
    }

    @PutMapping(value = "/books/{id}",
            consumes ={"application/json", "application/xml"},
            produces ={"application/json", "application/xml"} )
    public Book modifyOne(@PathVariable Long id, @RequestBody Book book) {
        Book book1 = bookService.getById(id);
        book1.setTitle(book.getTitle());
        book1.setCover(book.getCover());
        return bookService.overrideBook(book1);
    }
}
