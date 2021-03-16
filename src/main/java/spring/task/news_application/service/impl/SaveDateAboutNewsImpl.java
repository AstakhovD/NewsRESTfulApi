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

import java.io.*;
import java.net.URL;
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
    public ByteArrayOutputStream writeInWord(List<Article> articleList) throws IOException {

        File file = new File(docTemplatePath);

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             FileInputStream fileInputStream = new FileInputStream(file);
             XWPFDocument document = new XWPFDocument(fileInputStream)) {

            XWPFParagraph paragraph = document.createParagraph();

            for (Article article : articleList) {
                XWPFRun run = paragraph.createRun();

                replaceText(document, "N_TITL", article.getTitle());
                run.setFontFamily("Times New Roman");
                run.setFontSize(14);
                run.addBreak();

                replaceText(document, "N_AUT", article.getAuthor());
                run.setFontFamily("Times New Roman");
                run.setFontSize(14);
                run.addBreak();

                replaceText(document, "N_SOU", article.getSource());
                run.setFontFamily("Times New Roman");
                run.setFontSize(14);
                run.addBreak();

                replaceText(document, "N_Ur", article.getUrl());
                run.setFontFamily("Times New Roman");
                run.setFontSize(14);
                run.addBreak();

                if (article.getImageToUrl().isEmpty()) {
                    run.setText("Не было найдено изображения!");
                } else {
                    replaceImg(document, "N_Img", article.getImageToUrl());
                    run.setTextPosition(20);
                    run.addBreak();

                    if (article.getDescription() == null) {
                        run.setText("Отсуствует описание!");
                    } else {
                        try {
                            replaceText(document, "N_Desc", article.getDescription());
                        } catch (Exception e) {
                            run.setText("Отсуствует описание!");
                            logger.error("Exception", e);
                        }
                    }
                    run.setFontFamily("Times New Roman");
                    run.setFontSize(14);
                    run.addBreak();

                    replaceText(document, "N_Publ", article.getPublishedAt());
                    run.setFontFamily("Times New Roman");
                    run.setFontSize(14);
                    run.addBreak();
                    run.addBreak();
                }
                try {
                    document.write(byteArrayOutputStream);
                } catch (IOException e) {
                    logger.error("IOException", e);
                } finally {
                    try {
                        byteArrayOutputStream.flush();
                        byteArrayOutputStream.close();
                    } catch (IOException e) {
                        logger.error("IOException", e);
                    }
                }
            }
            return byteArrayOutputStream;
        }
    }

    private void replaceText(XWPFDocument doc, String findText, String replaceText) {
        for (XWPFParagraph p : doc.getParagraphs()) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    String text = r.getText(0);
                    if (text != null && text.contains(findText)) {
                        text = text.replace(findText, replaceText);
                        r.setText(text, 0);
                    }
                }
            }
        }
    }

    private void replaceImg(XWPFDocument doc, String findText, String imageURL) {
        for (XWPFParagraph p : doc.getParagraphs()) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    String text = r.getText(0);
                    if (text != null && text.contains(findText)) {
                        r.setText("", 0);
                        r.addBreak();
                        try {
                            URL url = new URL(imageURL);
                            InputStream in = new BufferedInputStream(url.openStream());
                            r.addPicture(in, XWPFDocument.PICTURE_TYPE_PNG, imageURL, Units.toEMU(174), Units.toEMU(174));
                            in.close();
                        } catch (InvalidFormatException | IOException e) {
                            logger.error("Failed to form Url", e);
                        }
                    }
                }
            }
        }
    }

    public ByteArrayOutputStream saveForCountry(String urlCountry) throws ExecutionException, InterruptedException, IOException {
        return writeInWord(newsServiceInterface.searchByCountry(urlCountry));
    }

    public ByteArrayOutputStream saveForCategory(String urlCountry, String urlCategory) throws ExecutionException, InterruptedException, IOException {
        return writeInWord(newsServiceInterface.searchByCategory(urlCountry, urlCategory));
    }

    public ByteArrayOutputStream saveForOnlyCategory(String urlCategory) throws ExecutionException, InterruptedException, IOException {
        return writeInWord(newsServiceInterface.searchByOnlyCategory(urlCategory));
    }

    public ByteArrayOutputStream saveForOnlyLanguage(String urlLanguage) throws ExecutionException, InterruptedException, IOException {
        return writeInWord(newsServiceInterface.searchByLanguage(urlLanguage));
    }

    public ByteArrayOutputStream saveByKeyWord(String urlQ) throws ExecutionException, InterruptedException, IOException {
        return writeInWord(newsServiceInterface.searchByQ(urlQ));
    }

    public ByteArrayOutputStream saveBySource(String urlSource) throws ExecutionException, InterruptedException, IOException {
        return writeInWord(newsServiceInterface.searchBySource(urlSource));
    }
}