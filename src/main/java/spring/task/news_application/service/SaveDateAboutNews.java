package spring.task.news_application.service;

import spring.task.news_application.model.Article;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface SaveDateAboutNews {

    ByteArrayOutputStream writeInWord(List<Article> articleList) throws IOException;

    InputStream takeImageFromUrl(String url) throws IOException;

    ByteArrayOutputStream saveForCategory(String urlCountry, String urlCategory) throws ExecutionException, InterruptedException;

    ByteArrayOutputStream saveForCountry(String urlCountry) throws ExecutionException, InterruptedException;

    ByteArrayOutputStream saveForOnlyCategory(String urlCategory) throws ExecutionException, InterruptedException;

    ByteArrayOutputStream saveForOnlyLanguage(String urlLanguage) throws ExecutionException, InterruptedException;

    ByteArrayOutputStream saveByKeyWord(String urlQ) throws ExecutionException, InterruptedException;

    ByteArrayOutputStream saveBySource(String urlSource) throws ExecutionException, InterruptedException;
}
