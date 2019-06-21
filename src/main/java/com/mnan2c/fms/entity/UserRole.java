package com.mnan2c.fms.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class UserRole {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Integer userId;
  private Integer roleId;
}
