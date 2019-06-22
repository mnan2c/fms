package com.mnan2c.fms.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

  private Integer code;

  private String message;

  private BusinessException(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public static BusinessException instance(Integer code, String message) {
    return new BusinessException(code, message);
  }

  public static BusinessException instance(ErrorConsts consts) {
    Integer code = consts.getCode();
    String message = consts.getMessage();
    return new BusinessException(code, message);
  }
}
