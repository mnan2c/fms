package com.mnan2c.fms.exception;

import lombok.Getter;

@Getter
public enum ErrorConsts {
  ID_IS_REQUIRED("400001", "id.is.required"),
  ENTITY_NOT_EXISTED("400002", "entity.not.existed");

  private String code;
  private String message;

  ErrorConsts(String code, String message) {
    this.code = code;
    this.message = message;
  }
}
