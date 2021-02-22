package spring.task.news_application.model.service;

import org.apache.logging.log4j.LogManager;
import spring.task.news_application.model.Article;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.concurrent.*;

@Service
public class NewsService implements NewsServiceInterface {

    private static final Logger logger = LogManager.getLogger(NewsService.class);

    private final UriComponentsBuilder uriComponentsBuilder;

    private final int thread;

    @Autowired
    private ConversionService conversionService;

    public NewsService(@Value("${apiKey}") String key, @Value("${thread}") int thread) {
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

    public List<Article> searchByCategory(String country, String category) throws ExecutionException, InterruptedException {
        String url = uriComponentsBuilder.cloneBuilder().queryParam("country", country).queryParam("category", category).build().toString();
        try {
            return asynchronousForNews(url);
        } catch (ExecutionException | InterruptedException e) {
            logger.error("ExecutionException/InterruptedException");
        }
        return asynchronousForNews(url);
    }

    public List<Article> searchByCountry(String country) throws ExecutionException, InterruptedException {
        String url = uriComponentsBuilder.cloneBuilder().queryParam("country", country).build().toString();
        try {
            return asynchronousForNews(url);
        } catch (ExecutionException | InterruptedException e) {
            logger.error("ExecutionException/InterruptedException");
        }
        return asynchronousForNews(url);
    }
}
