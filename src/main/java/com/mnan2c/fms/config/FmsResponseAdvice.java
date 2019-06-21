package com.mnan2c.fms.config;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

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
    return new ResponseEntity<>(o, HttpStatus.OK);
  }

  @Override
  public boolean supports(MethodParameter methodParameter, Class clas) {
    // 获取当前处理请求的controller的方法
    String methodName = methodParameter.getMethod().getName();
    // 不拦截/不需要处理返回值 的方法
    String method = "loginCheck"; // 如登录
    // 不拦截
    return !method.equals(methodName);
  }
}
