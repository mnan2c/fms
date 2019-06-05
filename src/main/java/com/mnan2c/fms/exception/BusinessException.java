package com.mnan2c.fms.exception;

public class BusinessException extends RuntimeException {

  private String code;
  private String message;

  private BusinessException(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public static BusinessException instance(String code, String message) {
    return new BusinessException(code, message);
  }

  public static BusinessException instance(ErrorConsts consts) {
    String code = consts.getCode();
    String message = consts.getMessage();
    return new BusinessException(code, message);
  }
}
