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

  private Integer testPaperId;

  /** 1. 单选 2. 多选 3. 填空题 4. 简答题 */
  private Integer type;

  @Column(length = 1000)
  private String question;

  private String selections;

  private Integer sort;

  private String answer;

  private String userAnswer;

  private Boolean isCorrect;

  // 分值
  private Integer point;
}
