package org.my.rest.serveces;

import org.my.rest.models.News;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewsService {

    private final Map<Long, News> newsMap;


    public NewsService() {
        this.newsMap = new HashMap<>();

        newsMap.put(1L, new News(1L, "The Times", "news"));
        newsMap.put(2L, new News(2L, "Crime news", "criminal"));
        newsMap.put(3L, new News(3L, "The Makeup", "beauty"));
        newsMap.put(4L, new News(4L, "The Boston Globe", "news"));
        newsMap.put(5L, new News(5L, "Crime", "criminal"));

    }

    public List<News> getAll() {
        return new ArrayList<>(newsMap.values());
    }


    public News getById(Long id) {
        return newsMap.get(id);
    }


    public News deleteById(Long id) {
        return newsMap.remove(id);
    }

    public News addNew(News news) {
        Long maxId = newsMap.keySet()
                .stream()
                .mapToLong(value -> value)
                .max()
                .getAsLong();

        maxId++;

        news.setId(maxId);
        newsMap.put(news.getId(), news);

        return news;
    }

    public News overrideNews(News news) {
        newsMap.put(news.getId(), news);
        return newsMap.get(news.getId());
    }

}



