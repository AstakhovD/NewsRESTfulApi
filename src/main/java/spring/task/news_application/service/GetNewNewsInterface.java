package spring.task.news_application.service;

import spring.task.news_application.model.Article;
import org.json.JSONObject;

public interface GetNewNewsInterface {
    Article getNewNews(JSONObject arrayElements);
}
