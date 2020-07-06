package org.my.rest.controllers;

import org.my.rest.models.News;
import org.my.rest.serveces.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping(value = "/news", produces = {"application/json", "application/xml"})
    public List<News> getAll(@RequestParam(required = false) String name,
                             @RequestParam(required = false) String topic) {

        if (name == null && topic != null) {
            return newsService.getAll().stream()
                    .filter(news -> news.getTopic().equalsIgnoreCase(topic))
                    .collect(Collectors.toList());

        } else if (name != null && topic == null) {
            return newsService.getAll().stream()
                    .filter(news -> news.getName().equalsIgnoreCase(name))
                    .collect(Collectors.toList());


        } else if (name != null && topic != null) {
            return newsService.getAll().stream()
                    .filter(news -> news.getName().equalsIgnoreCase(name))
                    .filter(news -> news.getTopic().equalsIgnoreCase(topic))
                    .collect(Collectors.toList());

        } else {
            return newsService.getAll();
        }
    }

    @GetMapping(value = "/news/{id}", produces = {"application/json", "application/xml"})
    public News getById(@PathVariable Long id) {
        return newsService.getById(id);
    }

    @DeleteMapping("/news/{id}")
    public void deleteById(@PathVariable Long id){
        newsService.deleteById(id);
    }

    @PostMapping(value = "/news/{id}", consumes ={"application/json", "application/xml"} ,
                produces = {"application/json", "application/xml"})
    public News createOne(@RequestBody News news){
        return newsService.addNew(news);
    }


    @PutMapping(value = "/news/{id}", consumes ={"application/json", "application/xml"} ,
            produces = {"application/json", "application/xml"})
    public News modifyOne(@PathVariable Long id, @RequestBody News news){
        News news1 = newsService.getById(id);
        news1.setName(news.getName());
        news1.setTopic(news.getTopic());

        return newsService.overrideNews(news1);
    }
}

