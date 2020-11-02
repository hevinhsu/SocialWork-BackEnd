package com.socialWork.config;


import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
public class SwaggerConfig {
	
	@Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
//                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.socialWork.controller"))
                .paths(PathSelectors.any())//paths(PathSelectors.regex("/.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Social Work Controller API 說明文件")
                .description("Project GitHub:https://github.com/hevinhsu/socialWork")
                .termsOfServiceUrl("https://github.com/hevinhsu/socialWork")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("Dev")
                .build();
    }
    
    private ApiKey apiKey() { 
        return new ApiKey("JWT", "Authorization", "header"); 
    }
    
    private SecurityContext securityContext() { 
        return SecurityContext.builder().securityReferences(defaultAuth()).build(); 
    } 
    
    private List<SecurityReference> defaultAuth() { 
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything"); 
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1]; 
        authorizationScopes[0] = authorizationScope; 
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes)); 
    }
    
}
