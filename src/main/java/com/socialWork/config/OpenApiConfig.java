package com.socialWork.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Wayne's Talk API", version = "v1.0.0",
                description = "Testing OpenApi On SpringDoc",
                license = @License(name = "MIT Licence")


                ))
@Configuration
public class OpenApiConfig {



}
