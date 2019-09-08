package com.mnan2c.fms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

@Data
@Entity
@ToString(callSuper = true)
public class SigninRate {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Integer userId;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate createDate = LocalDate.now(ZoneId.of("UTC+8"));

  @Column(precision = 2, length = 10)
  private BigDecimal rate;

  private Integer continuous;
}
