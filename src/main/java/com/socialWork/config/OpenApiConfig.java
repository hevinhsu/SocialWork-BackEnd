package com.socialWork.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Test API", version = "version:test",
                description = "Testing OpenApi On SpringDoc",
                license = @License(name = "Apache Licence")
                ))
@Configuration
public class OpenApiConfig {



}
