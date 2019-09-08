package com.mnan2c.fms.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.List;

// 添加拦截器并继承WebMvcConfigurationSupport后会覆盖@EnableAutoConfiguration关于WebMvcAutoConfiguration的配置
// 从而导致所有的Date返回都变成时间戳
@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("GFT", "POST", "PUT", "OPTIONS", "DELETE")
        .maxAge(3600)
        .allowCredentials(true);
  }

  @Bean
  public ExceptionHandlerFilter exceptionHandlerFilter() {
    return new ExceptionHandlerFilter();
  }

  @Bean
  public FilterRegistrationBean registerExceptionFilter() {
    FilterRegistrationBean bean = new FilterRegistrationBean();
    bean.setFilter(exceptionHandlerFilter());
    bean.addUrlPatterns("/*");
    bean.setOrder(1);
    return bean;
  }

  //  @Bean
  //  public JwtInterceptor jwtInterceptor() {
  //    return new JwtInterceptor();
  //  }
  //
  //  @Override
  //  protected void addInterceptors(InterceptorRegistry registry) {
  //    registry
  //        .addInterceptor(jwtInterceptor())
  //        .addPathPatterns("/**")
  //        .excludePathPatterns(
  //            "/webjars/**",
  //            "/**.html",
  //            "/swagger-resources/**",
  //            "/v2/api-docs",
  //            "/swagger/**",
  //            "/swagger-ui.html/**");
  //  }

  //  @Override
  //  public void addResourceHandlers(ResourceHandlerRegistry registry) {
  //    registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
  //  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    // 解决中文乱码
    converters.add(responseBodyConverter());
    // 解决 添加解决中文乱码后 上述配置之后，返回json数据直接报错 500：no convertter for return value of type
    // https://www.cnblogs.com/oldboyooxx/p/10824531.html
    // 因为在所有的 HttpMessageConverter 实例集合中，StringHttpMessageConverter 要比其它的 Converter 排得靠前一些。
    // 我们需要将处理 Object 类型的 HttpMessageConverter 放得靠前一些，这可以在 Configuration 类中完成：
    // 将我们定义的时间格式转换器添加到转换器列表中,
    // 这样jackson格式化时候但凡遇到Date类型就会转换成我们定义的格式
    converters.add(0, jackson2HttpMessageConverter());
  }

  @Bean
  public HttpMessageConverter<String> responseBodyConverter() {
    StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
    return converter;
  }

  @Bean
  public ObjectMapper getObjectMapper() {
    return new ObjectMapper();
  }

  // 定义序列化、反序列化 转换器
  @Bean
  public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    ObjectMapper objectMapper = getObjectMapper();

    // 默认，如果反序列化时，JSON字符串里有字段，而POJO中没有定义，会抛异常，可以设置这个来忽略未定义的字段
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    // 默认如果是字符串("")，反序列化会失败，可以开启这个设置，字符串("")会被反序列化成(null)
    objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

    objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    objectMapper.registerModule(new Jdk8Module());
    objectMapper.registerModule(new JavaTimeModule());

    converter.setObjectMapper(objectMapper);
    return converter;
  }

  // TODO 这里没有解决Pageable的问题
  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    // 注册Spring data jpa pageable的参数分解器
    argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
  }
}
