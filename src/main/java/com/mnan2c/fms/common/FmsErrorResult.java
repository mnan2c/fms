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
public class FmsErrorResult {

  private Integer code;
  private String message;

  public static FmsErrorResult instance(Integer code, String message) {
    return new FmsErrorResult(code, message);
  }

  public static FmsErrorResult instance(ErrorConsts consts) {
    return new FmsErrorResult(consts.getCode(), consts.getMessage());
  }
}
