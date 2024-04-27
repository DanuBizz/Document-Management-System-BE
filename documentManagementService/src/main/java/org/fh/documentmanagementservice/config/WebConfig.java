package org.fh.documentmanagementservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for Web settings.
 * This class configures the Cross-Origin Resource Sharing (CORS) settings for the application.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures the CORS mappings for the application.
     * It accepts a CorsRegistry object and configures the allowed origins, methods, headers, and credentials.
     * @param registry The CorsRegistry object to configure.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}