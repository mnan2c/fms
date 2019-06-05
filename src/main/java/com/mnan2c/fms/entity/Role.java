package com.mnan2c.fms.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table
public class Role {

  @Id @GeneratedValue private Long id;

  private String name;

  private String nameZh;
}
