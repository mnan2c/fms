package com.mnan2c.fms.controller.vo;

import com.mnan2c.fms.entity.User;
import lombok.Data;

@Data
public class UserVO extends User {
  private String province;
  private String city;
  private String street;
}
