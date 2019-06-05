package com.mnan2c.fms.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Data
public class UserRole {

  @Id @GeneratedValue private Long id;
  private Long userId;
  private Long roleId;
}
