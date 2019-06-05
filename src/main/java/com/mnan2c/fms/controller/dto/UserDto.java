package com.mnan2c.fms.controller.dto;

import com.mnan2c.fms.common.AbstractDto;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class UserDto extends AbstractDto {

  private String name;
  private String email;
  private String telephone;
  private String address;
  private String nickName;
  private ZonedDateTime birth;
  private String description;
}
