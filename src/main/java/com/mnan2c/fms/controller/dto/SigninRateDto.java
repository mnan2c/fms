package com.mnan2c.fms.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@ToString(callSuper = true)
public class SigninRateDto {

  private Integer id;

  private Integer userId;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate createDate;

  private BigDecimal rate;

  private Integer continuous;
}
