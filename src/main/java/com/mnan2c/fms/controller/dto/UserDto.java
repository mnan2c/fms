package com.mnan2c.fms.controller.dto;

import com.mnan2c.fms.common.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserDto extends BaseDto {

  @NotBlank private String name;
  private String password;
  private String email;
  private String telephone;
  private String address;
  private String nickName;
  private ZonedDateTime birth;
  private String description;
  private String avatar;
}
