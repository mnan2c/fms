package com.mnan2c.fms.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

  @Id @GeneratedValue private Long id;

  @JsonIgnore private Boolean active = true;

  @JsonIgnore @CreatedDate private ZonedDateTime createdDate = ZonedDateTime.now();

  @JsonIgnore @CreatedBy private Long createdBy;
}
