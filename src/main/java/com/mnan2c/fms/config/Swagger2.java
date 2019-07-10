package com.mnan2c.fms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 extends WebMvcConfigurationSupport {

  @Bean
  public Docket createRestApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.mnan2c.fms.controller"))
        .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Swagger2 构建FMS RestFul API")
        .contact(
            new Contact(
                "Morgan Zhang", "http://localhost:8888/swagger-ui.html", "1530849052@qq.com"))
        .version("1.0")
        .description("API 文档")
        .build();
  }

  //  @Override
  //  protected void addResourceHandlers(ResourceHandlerRegistry registry) {
  //    registry.addResourceHandler("/statics/**").addResourceLocations("classpath:/statics/");
  //    registry
  //        .addResourceHandler("swagger-ui.html")
  //        .addResourceLocations("classpath:/META-INF/resources/");
  //    registry
  //        .addResourceHandler("/webjars/**")
  //        .addResourceLocations("classpath:/META-INF/resources/webjars/");
  //  }
}
