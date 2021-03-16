package spring.task.news_application.controller;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import spring.task.news_application.service.SaveDateAboutNews;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * The class is a controller.
 * The class is responsible for processing requests related to saving data to a file (.docx).
 */

@RestController
public class NewsControllerSaveDate {

    @Autowired
    private SaveDateAboutNews saveDateAboutNewsInterface;

    @GetMapping(value = "/news/country/{country}/word")
    public ResponseEntity<?> saveInWordCountry(@PathVariable String country) throws ExecutionException, InterruptedException, IOException, InvalidFormatException {
        File file = new File("saveCountry");
        byte[] document = saveDateAboutNewsInterface.saveForCountry(country).toByteArray();
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentLength(document.length).body(document);
    }

    @GetMapping(value = "/news/category/{category}/{country}/word")
    public ResponseEntity<?> saveInWordCategory(@PathVariable String country, @PathVariable String category) throws ExecutionException, InterruptedException, IOException, InvalidFormatException {
        File file = new File("saveCategoryAndCountry");
        byte[] document = saveDateAboutNewsInterface.saveForCategory(country, category).toByteArray();
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentLength(document.length).body(document);
    }

    @GetMapping(value = "/news/category/{category}/word")
    public ResponseEntity<?> saveInWordOnlyCategory(@PathVariable String category) throws ExecutionException, InterruptedException, IOException {
        File file = new File("saveCategory");
        byte[] document = saveDateAboutNewsInterface.saveForOnlyCategory(category).toByteArray();
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentLength(document.length).body(document);
    }

    @GetMapping(value = "/news/language/{language}/word")
    public ResponseEntity<?> saveInWordOnlyLanguage(@PathVariable String language) throws ExecutionException, InterruptedException, IOException {
        File file = new File("saveLanguage");
        byte[] document = saveDateAboutNewsInterface.saveForOnlyLanguage(language).toByteArray();
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentLength(document.length).body(document);
    }

    @GetMapping(value = "/news/q/{q}/word")
    public ResponseEntity<?> saveByKeyWord(@PathVariable String q) throws ExecutionException, InterruptedException, IOException {
        File file = new File("saveByKeyWord");
        byte[] document = saveDateAboutNewsInterface.saveByKeyWord(q).toByteArray();
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentLength(document.length).body(document);
    }

    @GetMapping(value = "/news/sources/{sources}/word")
    public ResponseEntity<?> saveBySources(@PathVariable String sources) throws ExecutionException, InterruptedException, IOException {
        File file = new File(("saveBySources"));
        byte[] document = saveDateAboutNewsInterface.saveBySource(sources).toByteArray();
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentLength(document.length).body(document);
    }
}
