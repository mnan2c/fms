package com.mnan2c.fms.entity;

import com.mnan2c.fms.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;

@Data
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TestPaper extends BaseEntity {

  private String name;

  private BigDecimal totalPoints;

  private HashMap<String, Object> eachPartPoints;

  private LocalDateTime startTime;

  private LocalDateTime endTime;

  private String comment;
}
