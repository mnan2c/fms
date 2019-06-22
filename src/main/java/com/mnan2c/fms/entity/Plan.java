package com.mnan2c.fms.entity;

import com.mnan2c.fms.common.BaseEntity;
import com.mnan2c.fms.enums.Interval;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Data
@Entity
@Table
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Plan extends BaseEntity {

  private String name;

  @Enumerated(EnumType.STRING)
  private Interval circle;

  //  NEW 0 ,
  //  IN_PROGRESS 1,
  //  BLOCKED 2,
  //  FINISHED 3
  private Integer status = 0;

  //  URGENT 0,
  //  HIGH 1,
  //  NORMAL 2
  private Integer priority = 2;

  private ZonedDateTime beginDate;

  private ZonedDateTime endDate;

  private Integer targetValue;

  private Integer achievedValue;
}
