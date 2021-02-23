package spring.task.news_application.controller;

import spring.task.news_application.model.Article;
import spring.task.news_application.model.service.NewsServiceInterface;
import spring.task.news_application.model.service.SaveDateAboutNewsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class NewsController {

    @Autowired
    private NewsServiceInterface newsServiceInterface;

    @Autowired
    private SaveDateAboutNewsInterface saveDateAboutNewsInterface;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String guideForUsers() {
        return  "Для поиска по категориям /news/category/{category}/{country} - пример: /news/category/sport/ua " +
                "\nДля поиска по странам /news/country/{country} - пример: /news/country/ua" +
                "\nДля сохранение данных в Word файл о новостях с пометкой категории + страна -  /news/category/{category}/{country}/word" +
                "\nДля сохранение данных в Word файл о новостях с пометкой страна - /news/country/{country}/word";
    }

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

    @GetMapping(value = "/news/country/{country}/word")
    public ResponseEntity<byte[]> saveInWordCountry(@PathVariable String country) throws IOException, ExecutionException, InterruptedException {
        File file = new File("saveCountry.docx");
        byte[] document = saveDateAboutNewsInterface.saveForCountry(country).toByteArray();
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentLength(document.length).body(document);
    }

    @GetMapping(value = "/news/category/{category}/{country}/word")
    public ResponseEntity<byte[]> saveInWordCategory(@PathVariable String country, @PathVariable String category) throws IOException, ExecutionException, InterruptedException {
        File file = new File("saveCategoryAndCountry.docx");
        byte[] document = saveDateAboutNewsInterface.saveForCategory(country, category).toByteArray();
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentLength(document.length).body(document);
    }
}