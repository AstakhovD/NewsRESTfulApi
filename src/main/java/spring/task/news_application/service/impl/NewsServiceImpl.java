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

    private ConversionService conversionService;

    @Autowired
    public NewsServiceImpl(@Value("${apiKey}") String key, @Value("${thread}") int thread, ConversionService conversionService) {
        this.uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl("https://newsapi.org/v2/top-headlines").queryParam("apiKey", key);
        this.thread = thread;
        this.conversionService = conversionService;

    }

    private List<Article> asynchronousForNews(String url) {
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(thread);
            CompletionService<List<Article>> listCompletionService = new ExecutorCompletionService<List<Article>>(executorService);
            Future<List<Article>> listFuture;
            listFuture = listCompletionService.submit(() -> conversionService.convert(url, List.class));
            executorService.shutdown();
            return listFuture.get();
        } catch (ExecutionException | InterruptedException  e) {
            logger.error("Ошибки при асинхронном выполнение", e);
        }
        return null;
    }

    @Cacheable
    public List<Article> searchByCategory(String country, String category) {
        String url = uriComponentsBuilder.cloneBuilder().queryParam("country", country).queryParam("category", category).build().toString();
        return asynchronousForNews(url);
    }

    @Cacheable
    public List<Article> searchByCountry(String country) {
        String url = uriComponentsBuilder.cloneBuilder().queryParam("country", country).build().toString();
        return asynchronousForNews(url);
    }

    @Cacheable
    public List<Article> searchByOnlyCategory(String category) {
        String url = uriComponentsBuilder.cloneBuilder().queryParam("category", category).build().toString();
        return asynchronousForNews(url);
    }

    @Cacheable
    public List<Article> searchByLanguage(String language) {
        String url = uriComponentsBuilder.cloneBuilder().queryParam("language", language).build().toString();
        return asynchronousForNews(url);
    }

    @Cacheable
    public List<Article> searchByQ(String q) {
        String url = uriComponentsBuilder.cloneBuilder().queryParam("q", q).build().toString();
        return asynchronousForNews(url);
    }

    @Cacheable
    public List<Article> searchBySource(String sources) {
        String url = uriComponentsBuilder.cloneBuilder().queryParam("sources", sources).build().toString();
        return asynchronousForNews(url);
    }
}
