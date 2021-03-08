package spring.task.news_application.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewsControllerGuideForUsers {

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

    @RequestMapping(value = "/validDate/language", method = RequestMethod.GET)
    public String validDateLanguage() {
        return "Доступные языки для поиска - ar, de, en, es, fr, he, it, nl, no, pt, ru, uk, se, ud, zh";
    }

    @RequestMapping(value = "/validDate/country", method = RequestMethod.GET)
    public String validDateCountry() {
        return "Доступные страны для поиска - ar, au, br, ca, cn, de, es, fr, gb, hk, ie, in, is, nl, no, pk, ru, sa, sv, us, ua, za";
    }

    @RequestMapping(value = "/validDate/categories", method = RequestMethod.GET)
    public String validDateCategories() {
        return "Доступные категории для поиска - business, entertainment, gaming, general, health, politics, science, sport, technology";
    }
}
