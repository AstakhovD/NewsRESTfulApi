package spring.task.news_application.model.service;

import spring.task.news_application.model.Article;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface SaveDateAboutNewsInterface {

    ByteArrayOutputStream writeInWord(List<Article> articleList) throws IOException;

    InputStream takeImageFromUrl(String url) throws IOException;

    ByteArrayOutputStream saveForCategory(String urlCountry, String urlCategory) throws IOException, ExecutionException, InterruptedException;

    ByteArrayOutputStream saveForCountry(String urlCountry) throws IOException, ExecutionException, InterruptedException;
}
