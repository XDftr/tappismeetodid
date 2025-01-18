package cc.cryptek.tappismeetodid.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String resourcePath = new File("src/main/resources/static").getAbsolutePath();

        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + resourcePath + "/images/")
                .setCachePeriod(0);  // Disable caching during development

        // Also keep the classpath resources as fallback
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(3600);
    }
}
