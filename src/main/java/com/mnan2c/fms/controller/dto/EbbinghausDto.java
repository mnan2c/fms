package com.mnan2c.fms.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mnan2c.fms.common.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class EbbinghausDto extends BaseDto {

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate recordDate;

  private String description;
}
