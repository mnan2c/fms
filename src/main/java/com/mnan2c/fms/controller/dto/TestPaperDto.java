package com.mnan2c.fms.controller.dto;

import com.mnan2c.fms.common.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TestPaperDto extends BaseDto {

  private String name;

  /** 1. 语文；2. 数学； 3. 英语；4. 物理；5. 化学； 6. 生物；7. 政治；8.历史；9. 地理 */
  private Integer type;

  private Integer totalPoints = 0;

  private Integer achievedPoints = 0;

  private LocalDateTime startTime;

  private LocalDateTime endTime;

  private String comment;
}
