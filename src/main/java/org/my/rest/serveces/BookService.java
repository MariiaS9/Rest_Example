package org.my.rest.serveces;


import org.my.rest.models.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookService {

    private final Map<Long, Book> map;

    public BookService() {
        this.map = new HashMap<>();
        map.put(1L, new Book(1L, "Skazki", "soft"));
        map.put(2L, new Book(2L, "Horor", "hard"));
        map.put(3L, new Book(3L, "Roman", "soft"));
    }

    public  List<Book> getAll() {
        return new ArrayList<>(map.values());
    }

    public Book getById(Long id) {
        return map.get(id);
    }

    public Book deleteOne(Long id) {
        return map.remove(id);
    }

    public Book addNew(Book book) {
        long maxId = map.keySet()
                .stream()
                .mapToLong(v -> v)
                .max().getAsLong();
        maxId++;

        book.setId(maxId);
        map.put(book.getId(), book);
        return book;
    }

    public Book overrideBook(Book book){
        map.put(book.getId(), book);
        return map.get(book.getId());
    }
}
