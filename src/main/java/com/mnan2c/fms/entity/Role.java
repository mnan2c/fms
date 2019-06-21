package com.mnan2c.fms.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  private String nameZh;
}
