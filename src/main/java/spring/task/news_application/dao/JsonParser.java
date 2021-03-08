package spring.task.news_application.dao;

import org.apache.logging.log4j.LogManager;
import spring.task.news_application.model.Article;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.client.RestTemplate;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class JsonParser implements Converter<String, List<Article>> {

    public static final Logger logger = LogManager.getLogger(JsonParser.class);

    @Override
    public List<Article> convert(String url) {
        List<Article> articleList = new ArrayList<>();
        GetNewNews getNewNewsInterface = new GetNewNewsImpl();

        try {
            RestTemplate restTemplate = new RestTemplate();
            String resultForSearch = restTemplate.getForObject(url, String.class);
            JSONObject jsonObject = new JSONObject(resultForSearch);
            JSONArray newsObjects = jsonObject.getJSONArray("articles");

            for(int i = 0; i < newsObjects.length(); i++) {
                JSONObject arrayElements = newsObjects.getJSONObject(i);
                articleList.add(getNewNewsInterface.getNewNews(arrayElements));
            }
        } catch (JSONException e) {
            logger.error("JSONException", e);
        }
        return articleList;
    }
}

