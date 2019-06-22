package com.mnan2c.fms.common;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class BaseDto {

  private Integer id;
  private Integer createdBy;
  private ZonedDateTime createdDate;
}
