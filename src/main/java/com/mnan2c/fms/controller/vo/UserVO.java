package com.mnan2c.fms.controller.vo;

import com.mnan2c.fms.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserVO extends User {
  private String province;
  private String city;
  private String street;
}
