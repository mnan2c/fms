package com.mnan2c.fms.entity;

import com.mnan2c.fms.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Tests extends BaseEntity {

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

  // 分值
  private Integer points;
}
