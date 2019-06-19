package com.mnan2c.fms.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.Charset;
import java.util.List;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowCredentials(true)
        .allowedHeaders("*")
        .allowedOrigins("*")
        .allowedMethods("*");
  }

  @Bean
  public JwtInterceptor jwtInterceptor() {
    return new JwtInterceptor();
  }

  @Override
  protected void addInterceptors(InterceptorRegistry registry) {
    // 添加拦截器
    registry
        .addInterceptor(jwtInterceptor())
        .addPathPatterns("/**")
        .excludePathPatterns("/webjars/**", "/swagger-resources/**", "/swagger-ui.html/**");
    super.addInterceptors(registry);
  }

  // 解决 No handler found for GET /swagger-ui.html
  @Override
  protected void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
        .addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");
    registry
        .addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }

  @Override
  protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    // 解决中文乱码
    converters.add(responseBodyConverter());
    // 解决 添加解决中文乱码后 上述配置之后，返回json数据直接报错 500：no convertter for return value of type
    // https://www.cnblogs.com/oldboyooxx/p/10824531.html
    // 因为在所有的 HttpMessageConverter 实例集合中，StringHttpMessageConverter 要比其它的 Converter 排得靠前一些。
    // 我们需要将处理 Object 类型的 HttpMessageConverter 放得靠前一些，这可以在 Configuration 类中完成：
    converters.add(0, messageConverter());
  }

  @Bean
  public HttpMessageConverter<String> responseBodyConverter() {
    StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
    return converter;
  }

  @Bean
  public MappingJackson2HttpMessageConverter messageConverter() {
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    converter.setObjectMapper(getObjectMapper());
    return converter;
  }

  @Bean
  public ObjectMapper getObjectMapper() {
    return new ObjectMapper();
  }
}
