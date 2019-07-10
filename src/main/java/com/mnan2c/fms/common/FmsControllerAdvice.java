package com.mnan2c.fms.common;

import com.mnan2c.fms.exception.BusinessException;
import com.mnan2c.fms.exception.ErrorConsts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// ControllerAdvice只能用于处理Controller层的异常，无法处理Filter中出现的异常
@Slf4j
@RestControllerAdvice
public class FmsControllerAdvice {

  @ExceptionHandler(BusinessException.class)
  public FmsErrorResult businessExceptionHandler(BusinessException ex) {
    log.warn(
        String.format("Exception found with code: %s, message: %s", ex.getCode(), ex.getMessage()));
    return FmsErrorResult.instance(ex.getCode(), ex.getMessage());
  }

  @ExceptionHandler(RuntimeException.class)
  public FmsErrorResult runtimeExceptionHandler(
      HttpServletRequest request, final Exception e, HttpServletResponse response) {
    log.warn(String.format("Exception found with: %s", e.getMessage()));
    RuntimeException exception = (RuntimeException) e;
    return FmsErrorResult.instance(400, exception.getMessage());
  }

  @ExceptionHandler({Exception.class, Throwable.class})
  public FmsErrorResult exceptionHandler(HttpServletRequest request, Exception exception) {
    log.warn(String.format("Exception found with : [%s]", exception.getMessage()));
    return FmsErrorResult.instance(ErrorConsts.SYSTEM_ERROR);
  }
}
