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

  //  NEW 0 ,
  //  IN_PROGRESS 1,
  //  BLOCKED 2,
  //  FINISHED 3
  private Integer status;

  //  URGENT 0,
  //  HIGH 1,
  //  NORMAL 2
  private Integer priority;

  private ZonedDateTime beginDate;

  private ZonedDateTime endDate;

  private Integer targetValue;

  private Integer achievedValue;
}
