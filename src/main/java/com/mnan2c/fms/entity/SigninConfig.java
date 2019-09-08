package com.mnan2c.fms.entity;

import com.mnan2c.fms.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;

@Data
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SigninConfig extends BaseEntity {

  private String name;

  /** 1. 学习；2. 运动； 3. 工作； 4. 生活 ； */
  private Integer type;
}
