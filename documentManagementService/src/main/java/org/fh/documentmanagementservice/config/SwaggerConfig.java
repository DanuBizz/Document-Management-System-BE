package org.fh.documentmanagementservice.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Swagger.
 * This class configures the Swagger UI for the API documentation.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configures the public API group for Swagger.
     * It returns a GroupedOpenApi object that includes all the API endpoints.
     * @return The GroupedOpenApi object for the public API group.
     */
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .build();
    }
}