package com.mnan2c.fms.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.mnan2c.fms.common.FmsConsts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Configuration
public class DateConfig {
  @Bean
  public Converter<String, LocalDate> localDateConverter() {
    return new Converter<String, LocalDate>() {
      @Override
      public LocalDate convert(String source) {
        return LocalDate.parse(source, DateTimeFormatter.ofPattern(FmsConsts.DATE_FORMAT));
      }
    };
  }

  /** LocalDateTime转换器，用于转换RequestParam和PathVariable参数 */
  @Bean
  public Converter<String, LocalDateTime> localDateTimeConverter() {
    //    return source ->
    //        LocalDateTime.parse(source, DateTimeFormatter.ofPattern(FmsConsts.DATE_TIME_FORMAT));
    return new Converter<String, LocalDateTime>() {
      @Override
      public LocalDateTime convert(String source) {
        return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(FmsConsts.DATE_TIME_FORMAT));
      }
    };
  }

  @Bean
  public Converter<String, LocalTime> localTimeConverter() {
    //    return source -> LocalTime.parse(source,
    // DateTimeFormatter.ofPattern(FmsConsts.TIME_FORMAT));
    return new Converter<String, LocalTime>() {
      @Override
      public LocalTime convert(String source) {
        return LocalTime.parse(source, DateTimeFormatter.ofPattern(FmsConsts.TIME_FORMAT));
      }
    };
  }

  @Bean
  public Converter<String, Date> dateConverter() {
    //    return source -> {
    //      SimpleDateFormat format = new SimpleDateFormat(FmsConsts.DATE_TIME_FORMAT);
    //      try {
    //        return format.parse(source);
    //      } catch (ParseException e) {
    //        throw new RuntimeException(e);
    //      }
    //    };
    return new Converter<String, Date>() {
      @Override
      public Date convert(String source) {
        SimpleDateFormat format = new SimpleDateFormat(FmsConsts.DATE_TIME_FORMAT);
        try {
          return format.parse(source);
        } catch (ParseException e) {
          throw new RuntimeException(e);
        }
      }
    };
  }

  /** Json序列化和反序列化转换器，用于转换Post请求体中的json以及将我们的对象序列化为返回响应的json */
  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

    // LocalDateTime系列序列化和反序列化模块，继承自jsr310，我们在这里修改了日期格式
    JavaTimeModule javaTimeModule = new JavaTimeModule();
    javaTimeModule.addSerializer(
        LocalDateTime.class,
        new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(FmsConsts.DATE_TIME_FORMAT)));
    javaTimeModule.addSerializer(
        LocalDate.class,
        new LocalDateSerializer(DateTimeFormatter.ofPattern(FmsConsts.DATE_FORMAT)));
    javaTimeModule.addSerializer(
        LocalTime.class,
        new LocalTimeSerializer(DateTimeFormatter.ofPattern(FmsConsts.TIME_FORMAT)));
    javaTimeModule.addDeserializer(
        LocalDateTime.class,
        new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(FmsConsts.DATE_TIME_FORMAT)));
    javaTimeModule.addDeserializer(
        LocalDate.class,
        new LocalDateDeserializer(DateTimeFormatter.ofPattern(FmsConsts.DATE_FORMAT)));
    javaTimeModule.addDeserializer(
        LocalTime.class,
        new LocalTimeDeserializer(DateTimeFormatter.ofPattern(FmsConsts.TIME_FORMAT)));

    // Date序列化和反序列化
    javaTimeModule.addSerializer(
        Date.class,
        new JsonSerializer<Date>() {
          @Override
          public void serialize(
              Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
              throws IOException {
            SimpleDateFormat formatter = new SimpleDateFormat(FmsConsts.DATE_TIME_FORMAT);
            String formattedDate = formatter.format(date);
            jsonGenerator.writeString(formattedDate);
          }
        });
    javaTimeModule.addDeserializer(
        Date.class,
        new JsonDeserializer<Date>() {
          @Override
          public Date deserialize(
              JsonParser jsonParser, DeserializationContext deserializationContext)
              throws IOException {
            SimpleDateFormat format = new SimpleDateFormat(FmsConsts.DATE_TIME_FORMAT);
            String date = jsonParser.getText();
            try {
              return format.parse(date);
            } catch (ParseException e) {
              throw new RuntimeException(e);
            }
          }
        });

    objectMapper.registerModule(javaTimeModule);
    return objectMapper;
  }
}
