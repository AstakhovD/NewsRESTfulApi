package spring.task.news_application.dao;

import org.apache.logging.log4j.LogManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import spring.task.news_application.model.Article;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * Class implementation GetNewNews. The class is a service.
 * This class is responsible for receiving new data.
 * Support caching.
 */

@Service
@CacheConfig(cacheNames = {"dateNews"})
public class GetNewNewsImpl implements GetNewNews {

    public static final Logger logger = LogManager.getLogger(GetNewNewsImpl.class);

    @Override
    @Cacheable
    public Article getNewNews(JSONObject arrayElements) {

        Article articleList = new Article();
        try {
            JSONObject jsonObject = arrayElements.getJSONObject("source");
            articleList.setSource(jsonObject.getString("name"));
            if(!arrayElements.isNull("author")) {
                articleList.setAuthor(arrayElements.getString("author"));
            } else {
                articleList.setAuthor(articleList.getSource());
            }
            articleList.setTitle(arrayElements.getString("title"));
            articleList.setDescription(arrayElements.getString("description"));
            articleList.setUrl(arrayElements.getString("url"));
            articleList.setImageToUrl(arrayElements.getString("urlToImage"));
            articleList.setPublishedAt(arrayElements.getString("publishedAt"));

            articleList.setAuthor(articleList.getAuthor());
            articleList.setDescription(articleList.getDescription());
            articleList.setPublishedAt(articleList.getPublishedAt());
            articleList.setUrl(articleList.getUrl());
            articleList.setSource(articleList.getSource());
            articleList.setTitle(articleList.getTitle());
            articleList.setImageToUrl(articleList.getImageToUrl());
        } catch (JSONException e) {
            logger.error("Ошибка при конвертации", e);
        }
        return articleList;
    }
}