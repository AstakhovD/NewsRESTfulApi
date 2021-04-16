package spring.task.news_application.service.impl;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Value;
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
import spring.task.news_application.service.NewsService;
import spring.task.news_application.service.SaveDateAboutNews;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Class implementation SaveDateAboutNews. The class is a service.
 * This class is responsible for saving data to a file (.docx) upon user request.
 * Supports caching.
 */

@Service
@CacheConfig(cacheNames = {"newsSave"})
public class SaveDateAboutNewsImpl implements SaveDateAboutNews {

    public static final Logger logger = LogManager.getLogger(SaveDateAboutNewsImpl.class);

    @Autowired
    private NewsService newsServiceInterface;

    private final String docTemplatePath;

    public SaveDateAboutNewsImpl(@Value("${docTemplatePath}") String docTemplatePath) {
        this.docTemplatePath = docTemplatePath;
    }

    @Cacheable
    public ByteArrayOutputStream writeInWord(List<Article> articles) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            XWPFDocument template = createNTemplateParagraphs(articles.size());
            InputStream is = writeToInputStream(template);
            XWPFDocument document = new XWPFDocument(is);
            List<XWPFParagraph> paragraphList = document.getParagraphs();
            for (int i = 0; i < articles.size(); i++) {
                XWPFParagraph par = paragraphList.get(i);
                List<XWPFRun> runList = par.getRuns();
                if (runList != null) {
                    for (XWPFRun run : runList) {
                        String titleRun = run.getText(0);
                        if(titleRun != null) {
                            titleRun = titleRun.replace("title", articles.get(i).getTitle() == null ? "Отсуствует заголовок!" : articles.get(i).getTitle());
                            titleRun = titleRun.replace("author", articles.get(i).getAuthor() == null ? "Отсуствует автор!" : articles.get(i).getAuthor());
                            titleRun = titleRun.replace("source", articles.get(i).getSource() == null ? "Отсуствует источник!" : articles.get(i).getSource());
                            titleRun = titleRun.replace("url", articles.get(i).getUrl() == null ? "Отсуствует ссылка на публикацию!" : articles.get(i).getUrl());
                            titleRun = titleRun.replace("description", articles.get(i).getDescription() == null ? "Отсуствует описание!" : articles.get(i).getDescription());
                            titleRun = titleRun.replace("publishedAt", articles.get(i).getPublishedAt() == null ? "Отсуствует дата публикации!" : articles.get(i).getPublishedAt());
                            run.setText(titleRun, 0);
                        }
                    }
                    XWPFRun imageRun = par.createRun();
                    addImageRun(imageRun, articles.get(i).getImageToUrl());
                }
                    par.setSpacingBetween(1.3);
                    par.setSpacingAfter(1000);
                }
                document.write(byteArrayOutputStream);
                document.close();
                return byteArrayOutputStream;
            } catch(IOException e){
                logger.error("Ошибка при заполнение данных в файл", e);
            }
            return null;
        }

    private XWPFDocument createNTemplateParagraphs(int n) {
        XWPFDocument doc = null;
        try {
            doc = new XWPFDocument(new FileInputStream(docTemplatePath));
            XWPFParagraph templateParagraph = doc.getParagraphs().get(0);
            while(doc.getParagraphs().size() != n) {
                doc.createParagraph();
            }
            int position = 0;
            int s = doc.getParagraphs().size();
            for(XWPFParagraph paragraph : doc.getParagraphs()) {
                doc.setParagraph(templateParagraph, position++);
            }
        } catch (IOException e) {
            logger.error("Ошибка при создание документа", e);
        }
        return doc;
    }

    private InputStream writeToInputStream(XWPFDocument document) {
        InputStream is = null;
        try {
            ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
            document.write(byteOutStream);
            is = new ByteArrayInputStream(byteOutStream.toByteArray());
            is.close();
            document.close();
        } catch (IOException e) {
            logger.error("Ошибка при заполнение данных в файл", e);
        }
        return is;
    }

    private void addImageRun(XWPFRun imageRun, String urlToImage) {
        try {
            imageRun.addBreak();
            URL url = new URL(urlToImage);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Firefox");
            connection.connect();
            BufferedImage img = ImageIO.read(connection.getInputStream());
            ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
            if (img != null) {
                ImageIO.write(img, "jpg", imageStream);
                InputStream is = new ByteArrayInputStream(imageStream.toByteArray());
                imageRun.addPicture(is,
                        XWPFDocument.PICTURE_TYPE_JPEG, "image",
                        Units.toEMU(400), Units.toEMU(250));
            }
        } catch (IOException | InvalidFormatException e) {
            logger.error("Ошибка при попытке добавить изображение в файл", e);
        }
    }

    public ByteArrayOutputStream saveForCountry(String urlCountry) throws ExecutionException, InterruptedException {
        return writeInWord(newsServiceInterface.searchByCountry(urlCountry));
    }

    public ByteArrayOutputStream saveForCategory(String urlCountry, String urlCategory) throws ExecutionException, InterruptedException {
        return writeInWord(newsServiceInterface.searchByCategory(urlCountry, urlCategory));
    }

    public ByteArrayOutputStream saveForOnlyCategory(String urlCategory) throws ExecutionException, InterruptedException {
        return writeInWord(newsServiceInterface.searchByOnlyCategory(urlCategory));
    }

    public ByteArrayOutputStream saveForOnlyLanguage(String urlLanguage) throws ExecutionException, InterruptedException {
        return writeInWord(newsServiceInterface.searchByLanguage(urlLanguage));
    }

    public ByteArrayOutputStream saveByKeyWord(String urlQ) throws ExecutionException, InterruptedException {
        return writeInWord(newsServiceInterface.searchByQ(urlQ));
    }

    public ByteArrayOutputStream saveBySource(String urlSource) throws ExecutionException, InterruptedException {
        return writeInWord(newsServiceInterface.searchBySource(urlSource));
    }
}