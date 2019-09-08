package com.mnan2c.fms.entity;

import com.mnan2c.fms.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;
import java.time.ZonedDateTime;

@Data
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity {

  @Column(name = "name", nullable = false)
  private String name;

  @Pattern(regexp = "[a-zA-Z0-9]{6,32}")
  private String password;

  private String email;

  private String telephone;

  private String address;

  private String nickName;

  private String avatar;

  private ZonedDateTime birth;

  private String description;
}
