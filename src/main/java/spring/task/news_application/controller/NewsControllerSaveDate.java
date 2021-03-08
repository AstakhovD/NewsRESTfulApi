package spring.task.news_application.controller;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import spring.task.news_application.service.SaveDateAboutNews;

import java.io.File;
import java.util.concurrent.ExecutionException;

@RestController
public class NewsControllerSaveDate {

    @Autowired
    private SaveDateAboutNews saveDateAboutNewsInterface;

    @GetMapping(value = "/news/country/{country}/word")
    public ResponseEntity<byte[]> saveInWordCountry(@PathVariable String country) throws ExecutionException, InterruptedException {
        File file = new File("saveCountry.docx");
        byte[] document = saveDateAboutNewsInterface.saveForCountry(country).toByteArray();
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentLength(document.length).body(document);
    }

    @GetMapping(value = "/news/category/{category}/{country}/word")
    public ResponseEntity<byte[]> saveInWordCategory(@PathVariable String country, @PathVariable String category) throws ExecutionException, InterruptedException {
        File file = new File("saveCategoryAndCountry.docx");
        byte[] document = saveDateAboutNewsInterface.saveForCategory(country, category).toByteArray();
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentLength(document.length).body(document);
    }

    @GetMapping(value = "/news/category/{category}/word")
    public ResponseEntity<byte[]> saveInWordOnlyCategory(@PathVariable String category) throws ExecutionException, InterruptedException {
        File file = new File("saveCategory.docx");
        byte[] document = saveDateAboutNewsInterface.saveForOnlyCategory(category).toByteArray();
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentLength(document.length).body(document);
    }

    @GetMapping(value = "/news/language/{language}/word")
    public ResponseEntity<byte[]> saveInWordOnlyLanguage(@PathVariable String language) throws ExecutionException, InterruptedException {
        File file = new File("saveLanguage.docx");
        byte[] document = saveDateAboutNewsInterface.saveForOnlyLanguage(language).toByteArray();
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentLength(document.length).body(document);
    }

    @GetMapping(value = "/news/q/{q}/word")
    public ResponseEntity<byte[]> saveByKeyWord(@PathVariable String q) throws ExecutionException, InterruptedException {
        File file = new File("saveByKeyWord.docx");
        byte[] document = saveDateAboutNewsInterface.saveByKeyWord(q).toByteArray();
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentLength(document.length).body(document);
    }

    @GetMapping(value = "/news/sources/{sources}/word")
    public ResponseEntity<byte[]> saveBySources(@PathVariable String sources) throws ExecutionException, InterruptedException {
        File file = new File(("saveBySources.docx"));
        byte[] document = saveDateAboutNewsInterface.saveBySource(sources).toByteArray();
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentLength(document.length).body(document);
    }
}
