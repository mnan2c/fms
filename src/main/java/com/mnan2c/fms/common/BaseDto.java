package com.mnan2c.fms.common;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseDto {

  private Integer id;
  private Integer createdBy;
  private LocalDateTime createdDate;
}
