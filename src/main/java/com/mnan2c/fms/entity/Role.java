package com.mnan2c.fms.entity;

import com.mnan2c.fms.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;

@Data
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class Role extends BaseEntity {

  private String name;
  private String nameZh;
}
