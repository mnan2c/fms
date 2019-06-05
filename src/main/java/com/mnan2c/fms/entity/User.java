package com.mnan2c.fms.entity;

import com.mnan2c.fms.common.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "user")
public class User extends AbstractEntity {

  @Column(name = "name", nullable = false)
  private String name;

  private String password;

  private String email;

  private String telephone;

  private String address;

  private String nickName;

  private ZonedDateTime birth;

  private String description;
}
