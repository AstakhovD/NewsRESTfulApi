package spring.task.news_application.model.service;

import spring.task.news_application.model.Article;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface NewsServiceInterface {

    List<Article> searchByCountry(String country) throws ExecutionException, InterruptedException;

    List<Article> searchByCategory(String country, String category) throws ExecutionException, InterruptedException;
}
