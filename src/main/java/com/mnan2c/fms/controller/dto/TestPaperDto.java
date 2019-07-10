package com.mnan2c.fms.controller.dto;

import com.mnan2c.fms.common.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;

@Data

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TestPaperDto extends BaseDto {

  private String name;

  private BigDecimal totalPoints;

  private HashMap<String, Object> eachPartPoints;

  private LocalDateTime startTime;

  private LocalDateTime endTime;

  private String comment;
}
