package spring.task.news_application.model.service;

import org.apache.logging.log4j.LogManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import spring.task.news_application.model.Article;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@CacheConfig(cacheNames = {"newsSave"})
public class SaveDateAboutNews implements SaveDateAboutNewsInterface {

    public static final Logger logger = LogManager.getLogger(SaveDateAboutNews.class);

    @Autowired
    private NewsServiceInterface newsServiceInterface;

    @Cacheable
    public ByteArrayOutputStream writeInWord(List<Article> articleList) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();

        for(Article article : articleList) {
            XWPFRun run = paragraph.createRun();
            run.setText("Название: " + article.getTitle());
            run.addBreak();
            run.setText("Автор: " + article.getAuthor());
            run.addBreak();
            run.setText("Источник: " + article.getSource());
            run.addBreak();
            run.setText("Ссылка на публикацию: " + article.getUrl());
            run.addBreak();
            if(article.getImageToUrl() == null) {
                run.setText("Не было найдено изображения!");
            } else {
                try {
                    run.addPicture(takeImageFromUrl(article.getImageToUrl()), XWPFDocument.PICTURE_TYPE_JPEG, "",
                            Units.toEMU(450), Units.toEMU(300));
                } catch (IOException | InvalidFormatException e) {
                    run.setText("Не было найдено изображения!");
                    logger.error("IOException/InvalidFormatException");
                }
            }
            run.addBreak();
            run.setText("Описание: " + article.getDescription());
            run.addBreak();
            run.setText("Дата публикации: " + article.getPublishedAt());
            run.addBreak();
            run.addBreak();
        }
        try {
            document.write(byteArrayOutputStream);
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
            document.close();
        } catch (IOException e) {
            logger.error("IOException");
        }
        return byteArrayOutputStream;
    }

    public InputStream takeImageFromUrl(String url) throws IOException {
        URL urls = new URL(url);
        URLConnection connection = urls.openConnection();
        InputStream inputStream = urls.openStream();
        connection.setRequestProperty("User-Agent", "Firefox");
        return inputStream;
    }

    public ByteArrayOutputStream saveForCountry(String urlCountry) throws ExecutionException, InterruptedException {
        return writeInWord(newsServiceInterface.searchByCountry(urlCountry));
    }

    public ByteArrayOutputStream saveForCategory(String urlCountry, String urlCategory) throws ExecutionException, InterruptedException {
        return writeInWord(newsServiceInterface.searchByCategory(urlCountry, urlCategory));
    }
}
