package com.mnan2c.fms.controller.dto;

import com.mnan2c.fms.common.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class NoticeDto extends BaseDto {

  @NotBlank private String name;

  // 3. Important; 2. Normal; 1. Minor
  @NotNull private Integer importance = 2;
}
