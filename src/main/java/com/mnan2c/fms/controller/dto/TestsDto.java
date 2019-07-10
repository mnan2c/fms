package com.mnan2c.fms.controller.dto;

import com.mnan2c.fms.common.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TestsDto extends BaseDto {

  @Column(length = 8000, name = "name_to_edit")
  private String nameToEdit;

  @Column(length = 8000, name = "name_to_display")
  private String nameToDisplay;

  /** 1. 单选 2. 多选 3. 填空题 4. 简答题 */
  private Integer type;

  private Integer testPaperId;

  private Integer sort;

  private String answer;

  private String userAnswer;

  private Boolean isCorrect;

  private Integer points;
}
