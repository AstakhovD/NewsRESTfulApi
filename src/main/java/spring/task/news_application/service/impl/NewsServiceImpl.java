package spring.task.news_application.service.impl;

import org.apache.logging.log4j.LogManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import spring.task.news_application.model.Article;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import spring.task.news_application.service.NewsService;

import java.util.List;
import java.util.concurrent.*;

/**
 * Class implementation NewsService. The class is a service.
 * This class is responsible for retrieving data when requested by the user.
 * Supports caching.
 */

@Service
@CacheConfig(cacheNames = {"cacheServ"})
public class NewsServiceImpl implements NewsService {

    private static final Logger logger = LogManager.getLogger(NewsServiceImpl.class);

    private final UriComponentsBuilder uriComponentsBuilder;

    private final int thread;

    @Autowired
    private ConversionService conversionService;

    public NewsServiceImpl(@Value("${apiKey}") String key, @Value("${thread}") int thread) {
        this.uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl("https://newsapi.org/v2/top-headlines").queryParam("apiKey", key);
        this.thread = thread;
    }

    public List<Article> asynchronousForNews(String url) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(thread);
        CompletionService<List<Article>> listCompletionService = new ExecutorCompletionService<List<Article>>(executorService);
        Future<List<Article>> listFuture;
        listFuture = listCompletionService.submit(()
                -> conversionService.convert(url, List.class));
        return listFuture.get();
    }

    @Cacheable
    public List<Article> searchByCategory(String country, String category) throws ExecutionException, InterruptedException {
        String url = uriComponentsBuilder.cloneBuilder().queryParam("country", country).queryParam("category", category).build().toString();
        try {
            return asynchronousForNews(url);
        } catch (ExecutionException | InterruptedException e) {
            logger.error("ExecutionException/InterruptedException", e);
        }
        return asynchronousForNews(url);
    }

    @Cacheable
    public List<Article> searchByCountry(String country) throws ExecutionException, InterruptedException {
        String url = uriComponentsBuilder.cloneBuilder().queryParam("country", country).build().toString();
        try {
            return asynchronousForNews(url);
        } catch (ExecutionException | InterruptedException e) {
            logger.error("ExecutionException/InterruptedException", e);
        }
        return asynchronousForNews(url);
    }

    @Cacheable
    public List<Article> searchByOnlyCategory(String category) throws ExecutionException, InterruptedException {
        String url = uriComponentsBuilder.cloneBuilder().queryParam("category", category).build().toString();
        try {
            return asynchronousForNews(url);
        } catch (ExecutionException | InterruptedException e) {
            logger.error("ExecutionException/InterruptedException", e);
        }
        return asynchronousForNews(url);
    }

    @Cacheable
    public List<Article> searchByLanguage(String language) throws ExecutionException, InterruptedException {
        String url = uriComponentsBuilder.cloneBuilder().queryParam("language", language).build().toString();
        try {
            return asynchronousForNews(url);
        } catch (ExecutionException | InterruptedException e) {
            logger.error("ExecutionException/InterruptedException", e);
        }
        return asynchronousForNews(url);
    }

    @Cacheable
    public List<Article> searchByQ(String q) throws ExecutionException, InterruptedException {
        String url = uriComponentsBuilder.cloneBuilder().queryParam("q", q).build().toString();
        try {
            return asynchronousForNews(url);
        } catch (ExecutionException | InterruptedException e) {
            logger.error("ExecutionException/InterruptedException", e);
        }
        return asynchronousForNews(url);
    }

    @Cacheable
    public List<Article> searchBySource(String sources) throws ExecutionException, InterruptedException {
        String url = uriComponentsBuilder.cloneBuilder().queryParam("sources", sources).build().toString();
        try {
            return asynchronousForNews(url);
        } catch (ExecutionException | InterruptedException e) {
            logger.error("ExecutionException/InterruptedException", e);
        }
        return asynchronousForNews(url);
    }
}
