package com.nagarro.bookStore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static springfox.documentation.builders.PathSelectors.regex;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@SuppressWarnings("deprecation")
@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig extends WebMvcConfigurerAdapter {

	@Bean
	public Docket postsApi() {

		return new Docket(DocumentationType.SWAGGER_2).groupName("xadmin").apiInfo(apiInfo()).select()

				.paths(regex("/bookStore.*")).build();

	}

	private ApiInfo apiInfo() {

		return new ApiInfoBuilder().title("BookStore Project Using SpringBoot")

				.description("xadmin Course API Documentation Generateed Using SWAGGER2 for our BookStore Rest API")

				.termsOfServiceUrl("https://swagger.io/support/")

				.license("Xadmin Rest API License")

				.licenseUrl("https://swagger.io/license/").version("1.0").build();

	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}
