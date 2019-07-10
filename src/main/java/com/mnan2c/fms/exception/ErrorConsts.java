package com.mnan2c.fms.exception;

import lombok.Getter;

@Getter
public enum ErrorConsts {
  ID_IS_REQUIRED(400001, "id.is.required"),
  ENTITY_NOT_EXISTED(400002, "entity.not.existed"),
  INVALID_USERNAME_OR_PASSWORD(400003, "invalid.username.or.password"),
  INVALID_TOKEN(400004, "invalid.token"),
  USER_ALREADY_EXISTED(400005, "user.already.existed"),
  FAILED_TO_REGISTER(400006, "failed.to.register"),
  TOKEN_EXPIRED(400007, "token.expired"),
  INVALID_PASSWORD(400008, "invalid.password"),

  SYSTEM_ERROR(500001, "system.error");

  private Integer code;
  private String message;

  ErrorConsts(Integer code, String message) {
    this.code = code;
    this.message = message;
  }
}
