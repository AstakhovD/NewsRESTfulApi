package spring.task.news_application.service;

import spring.task.news_application.model.Article;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface NewsService {

    List<Article> searchByCountry(String country) throws ExecutionException, InterruptedException;

    List<Article> searchByCategory(String country, String category) throws ExecutionException, InterruptedException;

    List<Article> searchByOnlyCategory(String category) throws ExecutionException, InterruptedException;

    List<Article> searchByLanguage(String language) throws ExecutionException, InterruptedException;

    List<Article> searchByQ(String q) throws ExecutionException, InterruptedException;

    List<Article> searchBySource(String sources) throws ExecutionException, InterruptedException;
}
