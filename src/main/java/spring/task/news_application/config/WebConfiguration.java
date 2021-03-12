package spring.task.news_application.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This class is one of the Configuration's classes, enable MVC and enable caching.
 * Also implements interface WebMvcConfigurer and implemented one method that helps REST Controller return JSON or XML.
 */

@Configuration
@EnableWebMvc
@EnableCaching
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * Implementing of method from interface, that set default content type
     * as JSON with favorParameter.
     *
     * @param configurer object of ContentNegotiationConfigurer class
     */

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(true).
                favorParameter(false).
                ignoreAcceptHeader(true).
                defaultContentType(MediaType.APPLICATION_JSON).
                mediaType("xml", MediaType.APPLICATION_XML).
                mediaType("json", MediaType.APPLICATION_JSON);
    }
}


