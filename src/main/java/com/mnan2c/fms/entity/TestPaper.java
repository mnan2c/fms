package com.mnan2c.fms.entity;

import com.mnan2c.fms.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TestPaper extends BaseEntity {

  private String name;

  /** 1. 语文；2. 数学； 3. 英语；4. 物理；5. 化学； 6. 生物；7. 历史；8.地理；9. 政治 */
  private Integer type;

  private Integer totalPoints = 0;

  private Integer achievedPoints = 0;

  private LocalDateTime startTime;

  private LocalDateTime endTime;

  private String comment;
}
