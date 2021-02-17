package spring.task.news_application.config;

import spring.task.news_application.model.JsonParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

@Configuration
public class JsonParserSetup {

    @Bean
    public ConversionService conversionService() {
        DefaultConversionService defaultConversionService = new DefaultConversionService();
        defaultConversionService.addConverter(new JsonParser());
        return defaultConversionService;
    }
}
