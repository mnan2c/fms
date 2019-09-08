package com.mnan2c.fms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mnan2c.fms.common.BaseEntity;
import com.mnan2c.fms.common.FmsConsts;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

/** 艾宾浩斯记忆曲线模型 */
@Data
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Ebbinghaus extends BaseEntity {

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate recordDate = LocalDate.now(FmsConsts.SYSTEM_DEFAULT_ZONEID);

  @Column(length = 5000)
  private String description;
}
