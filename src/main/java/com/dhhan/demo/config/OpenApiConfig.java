package com.dhhan.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;

@Configuration
public class OpenApiConfig {

//    @Value("${springdoc.info.title}")
//    private String title;
//
//    @Value("${springdoc.info.version}")
//    private String version;
//
//    @Value("${springdoc.info.description}")
//    private String description;
//
//    @Bean
//    public OpenAPI openAPI() {
//        return new OpenAPI()
//                .info(new Info()
//                        .version(version)
//                        .title(title)
//                        .description(description));
//    }



}
