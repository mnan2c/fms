package com.mnan2c.fms.common;

import com.mnan2c.fms.exception.ErrorConsts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FmsResult implements Serializable {

  private Integer statusCodeValue;
  private String message;
  private Object body;

  public static FmsResult instance(Integer code, String message) {
    return new FmsResult(code, message, null);
  }

  public static FmsResult instance(ErrorConsts consts) {
    return new FmsResult(consts.getCode(), consts.getMessage(), null);
  }

  public static FmsResult successResult(Object data) {
    return new FmsResult(200, "", data);
  }
}
