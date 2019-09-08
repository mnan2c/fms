package com.mnan2c.fms.controller.dto;

import com.mnan2c.fms.common.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SigninConfigDto extends BaseDto {

  private String name;

  /** 1. 学习；2. 运动； 3. 工作； 4. 生活 ； 5. 娱乐 */
  private Integer type;
}
