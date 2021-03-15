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

    ByteArrayOutputStream saveForCategory(String urlCountry, String urlCategory) throws ExecutionException, InterruptedException, IOException;

    ByteArrayOutputStream saveForCountry(String urlCountry) throws ExecutionException, InterruptedException, IOException;

    ByteArrayOutputStream saveForOnlyCategory(String urlCategory) throws ExecutionException, InterruptedException, IOException;

    ByteArrayOutputStream saveForOnlyLanguage(String urlLanguage) throws ExecutionException, InterruptedException, IOException;

    ByteArrayOutputStream saveByKeyWord(String urlQ) throws ExecutionException, InterruptedException, IOException;

    ByteArrayOutputStream saveBySource(String urlSource) throws ExecutionException, InterruptedException, IOException;
}
