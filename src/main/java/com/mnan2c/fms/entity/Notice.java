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
public class Notice extends BaseEntity {

  private String name;

  // 3. Important; 2. Normal; 1. Minor
  private Integer importance = 2;
}
