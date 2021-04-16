package spring.task.news_application.dao;

import spring.task.news_application.model.Article;
import org.json.JSONObject;

public interface GetNewNews {
    Article getNewNews(JSONObject arrayElements);
}