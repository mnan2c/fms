package com.mnan2c.fms.controller.dto;

import com.mnan2c.fms.common.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TestsDto extends BaseDto {

  private Integer testPaperId;

  /** 1. 单选 2. 多选 3. 填空题 4. 简答题 */
  private Integer type;

  private String question;

  private String selections;

  private Integer sort;

  private String answer;

  private String userAnswer;

  private Boolean isCorrect;

  // 分值
  private Integer point;
}
