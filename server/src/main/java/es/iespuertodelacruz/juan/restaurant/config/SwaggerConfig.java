package es.iespuertodelacruz.juan.restaurant.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.plugin.core.SimplePluginRegistry;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
    public Docket institutoApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("es.iespuertodelacruz.juan.restaurant.controller"))
                .paths(PathSelectors.any())  //(regex("/api.*"))
                .build()
        		.apiInfo(getApiInfo());
    }

	
	private ApiInfo getApiInfo() {
		return new ApiInfo(
				"Restaurant API REST",
				"API REST para un gestionar un restaurante",
				"1.0",
				"Términos de servicio",
				new Contact("Juan Daniel Padilla",
                		"https://github.com/zclut",
                		"juandanielpadilla0@gmail.com"),
				"Apache License Version 2.0",
				"https://www.apache.org/licesen.html",
				Collections.emptyList()
				);
	}
}
