package com.mnan2c.fms.entity;

import com.mnan2c.fms.common.AbstractEntity;
import com.mnan2c.fms.enumeration.PlanType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "plan")
public class Plan extends AbstractEntity {

  private String name;

  @Enumerated(EnumType.STRING)
  private PlanType type;

  private ZonedDateTime beginDate;

  private ZonedDateTime endDate;

  private BigDecimal progress;

  private String description;

  private String link;
}
