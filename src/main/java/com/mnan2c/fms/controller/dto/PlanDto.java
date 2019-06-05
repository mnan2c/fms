package com.mnan2c.fms.controller.dto;

import com.mnan2c.fms.common.AbstractDto;
import com.mnan2c.fms.enumeration.PlanType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Data
public class PlanDto extends AbstractDto {

  @NotBlank private String name;

  @NotNull private PlanType type;

  private ZonedDateTime beginDate;

  @NotNull private ZonedDateTime endDate;

  private double progress;

  private String description;

  private String link;
}
