package com.mnan2c.fms.common;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

// 封装返回体
@RestControllerAdvice
public class FmsResponseAdvice implements ResponseBodyAdvice {

  @Override
  public Object beforeBodyWrite(
      Object o,
      MethodParameter methodParameter,
      MediaType mediaType,
      Class aClass,
      ServerHttpRequest serverHttpRequest,
      ServerHttpResponse serverHttpResponse) {
    return FmsResult.success(o);
  }

  @Override
  public boolean supports(MethodParameter methodParameter, Class clas) {
    Class<?> returnType = methodParameter.getMethod().getReturnType();
    return !FmsErrorResult.class.equals(returnType);
  }
}
