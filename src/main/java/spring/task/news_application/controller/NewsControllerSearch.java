package spring.task.news_application.controller;

import spring.task.news_application.model.Article;
import spring.task.news_application.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class NewsControllerSearch {

    @Autowired
    private NewsService newsServiceInterface;

    @GetMapping(value = "/news/category/{category}/{country}")
    public ResponseEntity categorizedDate(@PathVariable String country, @PathVariable String category) throws ExecutionException, InterruptedException {
        List<Article> articleList = newsServiceInterface.searchByCategory(country,category);
        return new ResponseEntity(articleList, HttpStatus.OK);
    }

    @GetMapping(value = "/news/country/{country}")
    public ResponseEntity sendSourcedUpdate(@PathVariable String country) throws ExecutionException, InterruptedException {
        List<Article> articleList = newsServiceInterface.searchByCountry(country);
        return new ResponseEntity(articleList,HttpStatus.OK);
    }

    @GetMapping(value = "/news/category/{category}")
    public ResponseEntity categoryOnly(@PathVariable String category) throws ExecutionException, InterruptedException {
        List<Article> articleList = newsServiceInterface.searchByOnlyCategory(category);
        return new ResponseEntity(articleList, HttpStatus.OK);
    }

    @GetMapping(value = "/news/language/{language}")
    public ResponseEntity languageOnly(@PathVariable String language) throws ExecutionException, InterruptedException {
        List<Article> articleList = newsServiceInterface.searchByLanguage(language);
        return new ResponseEntity(articleList, HttpStatus.OK);
    }

    @GetMapping(value = "/news/q/{q}")
    public ResponseEntity searchByQ(@PathVariable String q) throws ExecutionException, InterruptedException {
        List<Article> articleList = newsServiceInterface.searchByQ(q);
        return new ResponseEntity(articleList, HttpStatus.OK);
    }

    @GetMapping(value = "/news/sources/{sources}")
    public ResponseEntity searchBySource(@PathVariable String sources) throws ExecutionException, InterruptedException {
        List<Article> articleList = newsServiceInterface.searchBySource(sources);
        return new ResponseEntity(articleList, HttpStatus.OK);
    }
}

