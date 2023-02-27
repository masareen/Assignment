package com.example.demo;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@SpringBootApplication(scanBasePackages = "com.example.demo")
@EnableWebMvc
public class Coen6731AudioApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(Coen6731AudioApiApplication.class, args);
	}
	
	@Bean
	public Docket apis() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("com.example.demo"))
				.build()
				.apiInfo(metaData());
		
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
	}
	
	private ApiInfo metaData() {
		System.out.print("hereheee");
	    return new ApiInfoBuilder()
	            .title("Audio API")
	            .description("API for getting audio tracks")
	            .version("1.0")
	            .contact(new Contact("abc", "abc.com", "abc@gmail.com"))
	            .license("Apache License Version 2.0")
	            .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
	            .termsOfServiceUrl("http://abc.com/terms")
	            .build();
	}

}
