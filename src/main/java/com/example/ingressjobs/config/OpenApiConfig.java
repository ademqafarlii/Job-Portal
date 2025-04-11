package com.example.ingressjobs.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("Job Portal")
                        .version("1.0.0")
                        .description("A platform for job seekers and employers to find and post job opportunities.")

        );
    }
}
