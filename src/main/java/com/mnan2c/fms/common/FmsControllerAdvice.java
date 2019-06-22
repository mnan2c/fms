package com.mnan2c.fms.common;

import com.mnan2c.fms.exception.BusinessException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FmsControllerAdvice {

  /** 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器 */
  @InitBinder
  public void initBinder(WebDataBinder binder) {}

  @ExceptionHandler(value = BusinessException.class)
  public FmsResult myErrorHandler(BusinessException ex) {
    return FmsResult.instance(ex.getCode(), ex.getMessage());
  }
}
