package com.mnan2c.fms.repository;

import com.mnan2c.fms.entity.Ebbinghaus;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;

public interface EbbinghausRepository
    extends JpaSpecificationExecutor<Ebbinghaus>, BaseRepository<Ebbinghaus, Integer> {

  Ebbinghaus findFirstByRecordDateAndCreatedBy(LocalDate recordDate, Integer createdBy);
}
