package com.mnan2c.fms.controller.dto;

import com.mnan2c.fms.common.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class KnowledgeDetailDto extends BaseDto {

  @Column(length = 5000)
  private String detail;

  private Integer classifyId;
}
