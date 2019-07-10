package com.mnan2c.fms.controller.vo;

import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
  private String name;
  private String avatar;
  private String token;
  private String roleName;
}
