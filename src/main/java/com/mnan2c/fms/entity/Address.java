package com.mnan2c.fms.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table
public class Address {

  @Id private Long userId;
  private String province;
  private String city;
  private String street;
}
