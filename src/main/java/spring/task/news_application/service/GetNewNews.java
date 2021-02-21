package spring.task.news_application.service;

import org.apache.logging.log4j.LogManager;
import spring.task.news_application.model.Article;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class GetNewNews implements GetNewNewsInterface {

    public static final Logger logger = LogManager.getLogger(GetNewNews.class);

    @Override
    public Article getNewNews(JSONObject arrayElements) {

        String name;
        String author;
        String title;
        String description;
        String url;
        String urlToImage;
        String publishedAt;

        Article articleList = new Article();
        try {
            JSONObject jsonObject = arrayElements.getJSONObject("source");
            name = jsonObject.getString("name");
            if(!arrayElements.isNull("author")) {
                author = arrayElements.getString("author");
            } else {
                author = name;
            }
            title = arrayElements.getString("title");
            description = arrayElements.getString("description");
            url = arrayElements.getString("url");
            urlToImage = arrayElements.getString("urlToImage");
            publishedAt = arrayElements.getString("publishedAt");

            articleList.setAuthor(author);
            articleList.setDescription(description);
            articleList.setPublishedAt(publishedAt);
            articleList.setUrl(url);
            articleList.setSource(name);
            articleList.setTitle(title);
            articleList.setImageToUrl(urlToImage);
        } catch (JSONException e) {
            logger.error("JSONException");
        }
        return articleList;
    }
}
