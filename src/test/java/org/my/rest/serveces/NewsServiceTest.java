package org.my.rest.serveces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.my.rest.models.News;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NewsServiceTest {

    private NewsService newsService;

    @BeforeEach
    void setUp() {
        newsService= new NewsService();
    }

    @Test
    void getAll(){
        List<News> result = newsService.getAll();
        assertEquals(5, result.size());
    }
    @Test
    void  getById(){
        News result = newsService.getById(2L);
        assertNotNull(result);
        assertEquals(2L, result.getId());
    }

    @Test
    void deleteById(){
        News result = newsService.deleteById(1L);

        assertEquals(1L, result.getId());
        assertNotNull(result);
        assertEquals(4, newsService.getAll().size());
    }
     @Test
    void addNew(){
        News testNews = new News(6L,"The Women", "beauty");
        News result = newsService.addNew(testNews);

        assertEquals(6, newsService.getAll().size());
         assertNotNull(result);
         assertEquals(6L, result.getId());
     }
}
