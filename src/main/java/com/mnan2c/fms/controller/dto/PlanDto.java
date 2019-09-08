package com.mnan2c.fms.controller.dto;

import com.mnan2c.fms.common.BaseDto;
import com.mnan2c.fms.enums.Interval;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PlanDto extends BaseDto {

  @NotBlank private String name;

  private Interval circle;

  //  NEW 1 ,
  //  IN_PROGRESS 2,
  //  BLOCKED 3,
  //  FINISHED 4
  private Integer status;

  //  Important 1,
  //  Normal 2,
  //  Monor 3
  private Integer priority;

  private ZonedDateTime beginDate;

  private ZonedDateTime endDate;

  private Integer targetValue;

  private Integer achievedValue;

  // 1. 短期；2. 长期
  private Integer type;
}
