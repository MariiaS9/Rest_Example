package org.my.rest.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.my.rest.models.News;
import org.my.rest.serveces.NewsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NewsControllerTest {

    @Mock
    private NewsService newsService;

    @InjectMocks
    private NewsController newsController;

    private Map<Long, News> newsMap;


    @BeforeEach
    void setUp() {
        this.newsMap = new HashMap<>();
        newsMap.put(1L, new News(1L, "The Times", "news"));
        newsMap.put(2L, new News(2L, "Crime news", "criminal"));
        newsMap.put(3L, new News(3L, "The Makeup", "beauty"));
        newsMap.put(4L, new News(4L, "The Boston Globe", "news"));
        newsMap.put(5L, new News(5L, "Crime", "criminal"));

    }

    @Test
    void getAllCaseTopicNotNull() {
        Mockito.when(newsService.getAll()).thenReturn(new ArrayList<>(newsMap.values()));

        List<News> result = newsController.getAll(null, "beauty");
        verify(newsService).getAll();
        assertEquals(1, result.size());
        assertEquals("beauty", result.get(0).getTopic());
    }

    @Test
    void getAllCaseNameNotNull() {
        Mockito.when(newsService.getAll()).thenReturn(new ArrayList<>(newsMap.values()));

        List<News> result = newsController.getAll("The Times", null);
        verify(newsService).getAll();
        assertEquals(1, result.size());
        assertEquals("The Times", result.get(0).getName());
    }

    @Test
    void getAll() {
        Mockito.when(newsService.getAll()).thenReturn(new ArrayList<>(newsMap.values()));

        List<News> result = newsController.getAll(null, null);
        verify(newsService).getAll();
        assertEquals(5, result.size());
    }

    @Test
    void getAllCaseBothNotNull() {
        Mockito.when(newsService.getAll()).thenReturn(new ArrayList<>(newsMap.values()));

        List<News> result = newsController.getAll("The Times", "news");
        verify(newsService).getAll();
        assertEquals(1, result.size());
        assertEquals("The Times", result.get(0).getName());
        assertEquals("news", result.get(0).getTopic());
    }

    @Test
    void getAllCaseInvalidName() {
        Mockito.when(newsService.getAll()).thenReturn(new ArrayList<>(newsMap.values()));

        List<News> result = newsController.getAll("OOO", null);
        verify(newsService).getAll();
        assertEquals(0, result.size());
    }

    @Test
    void getAllCaseInvalidTopic() {
        Mockito.when(newsService.getAll()).thenReturn(new ArrayList<>(newsMap.values()));

        List<News> result = newsController.getAll(null, "prrrr");
        verify(newsService).getAll();
        assertEquals(0, result.size());
    }

    @Test
    void getById() {
        Long testId = 2L;
        when(newsService.getById(testId)).thenReturn(newsMap.get(testId));

        News result = newsController.getById(testId);
        verify(newsService).getById(testId);
        assertEquals(2L, result.getId());
    }

    @Test
    void deleteById() {
        Long testId = 4L;
        newsController.deleteById(testId);
        verify(newsService).deleteById(testId);
    }

    @Test
    void createOne() {
        News testNews = new News(null, " TRK", "beauty");
        when(newsService.addNew(testNews)).thenReturn(testNews);

        News result = newsController.createOne(testNews);
        verify(newsService).addNew(testNews);
        assertNotNull(result);
    }

    @Test
    void modifyOne(){
        News modifiedNews = new News(10L, "Crime", "criminal");
        News legacyNews = new News(10L, "POP", "beauty");
        when(newsService.getById(modifiedNews.getId())).thenReturn(legacyNews);
        when(newsService.overrideNews(modifiedNews)).thenReturn(modifiedNews);

        News result = newsController.modifyOne(modifiedNews.getId(), modifiedNews);
        verify(newsService).getById(modifiedNews.getId());
        verify(newsService).overrideNews(modifiedNews);
        assertEquals(modifiedNews, result);
    }
}
