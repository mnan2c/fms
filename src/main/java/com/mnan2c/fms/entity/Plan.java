package com.mnan2c.fms.entity;

import com.mnan2c.fms.common.BaseEntity;
import com.mnan2c.fms.enums.Interval;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.ZonedDateTime;

@Data
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Plan extends BaseEntity {

  private String name;

  @Enumerated(EnumType.STRING)
  private Interval circle;

  //  NEW 1 ,
  //  IN_PROGRESS 2,
  //  BLOCKED 3,
  //  FINISHED 4
  private Integer status = 0;

  //  Important 1,
  //  Normal 2,
  //  Monor 3
  private Integer priority = 2;

  private ZonedDateTime beginDate;

  private ZonedDateTime endDate;

  private Integer targetValue;

  private Integer achievedValue;
}
