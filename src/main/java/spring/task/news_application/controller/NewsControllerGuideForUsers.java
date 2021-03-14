package spring.task.news_application.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * The class is a controller.
 * The class is a guide for the user. Contains examples for all possible queries.
 */

@RestController
public class NewsControllerGuideForUsers {

    @Value("${validCountry}")
    private String validCountry;
    @Value("${validCategories}")
    private String validCategories;
    @Value("${validLanguage}")
    private String validLanguage;

    @RequestMapping(value = "/news/language", method = RequestMethod.GET)
    public String validDateLanguage() {
        return validLanguage;
    }

    @RequestMapping(value = "/news/country", method = RequestMethod.GET)
    public String validDateCountry() {
        return validCountry;
    }

    @RequestMapping(value = "/news/category", method = RequestMethod.GET)
    public String validDateCategories() {
        return validCategories;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String guide() {
        return "Помощь по поиску новостей - /guideForUsersSearch" +
                "\nПомощь по сохранению данных - /guideForUsersSave";
    }

    @RequestMapping(value = "/guideForUsersSearch", method = RequestMethod.GET)
    public String guideForUsersSearch() {
        return "Для поиска новостей по категориям и стране - /news/category/{category}/{country} - пример: /news/category/sport/ua " +
                "\nДля поиска новостей по странам - /news/country/{country} - пример: /news/country/ua" +
                "\nДля поиска новостей по ключевому слову - /news/q/{q} - пример: /news/q/bitcoin" +
                "\nДля поиска новостей по категории - /news/category/{category} - пример: /news/category/technology" +
                "\nДля поиска новостей по указанному источнику - /news/sources/{sources} - пример: /news/sources/MSNBC" +
                "\nДля поиска новостей по указанному языку - /news/language/{language} - пример: /news/language/uk";

    }

    @RequestMapping(value = "/guideForUsersSave", method = RequestMethod.GET)
    public String guideForUsersSave() {
        return "Для сохранения данных в Word файл о новостях с пометкой категории + страна - /news/category/{category}/{country}/word" +
                "\nДля сохранения данных в Word файл о новостях с пометкой страна - /news/country/{country}/word" +
                "\nДля сохранения данных в Word файл о новостях с пометкой категории - /news/category/{category}/word" +
                "\nДля сохранения данных в Word файл о новостях с ключевым словом - /news/q/{q}/word" +
                "\nДля сохранения данных в Word файл о новостях из указаного источника - /news/sources/{sources}/word" +
                "\nДля сохранения данных в Word файл о новостях с указанным языком - /news/language/{language}/word";
    }

}
