package com.mnan2c.fms.common;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class AbstractDto {

  private Long id;
  private Boolean active;
  private Long createdBy;
  private ZonedDateTime createdDate;
}
