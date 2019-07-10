package com.mnan2c.fms.common;

import com.mnan2c.fms.exception.ErrorConsts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FmsResult<T> {

  private Integer code;
  private String message;
  private T data;

  public static FmsResult instance(Integer code, String message) {
    return new FmsResult(code, message, null);
  }

  public static FmsResult instance(ErrorConsts consts) {
    return new FmsResult(consts.getCode(), consts.getMessage(), null);
  }

  public static <T> FmsResult<T> success(T data) {
    return new FmsResult(200, "", data);
  }
}
