package com.mnan2c.fms.repository;

import com.mnan2c.fms.entity.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PlanRepository extends BaseRepository<Plan, Integer> {

  @Transactional
  @Modifying
  @Query(value = "update Plan set status=3 where id=?1")
  int completePlan(Integer id);

  Page<Plan> findPlansByCreatedBy(Integer createdBy, Pageable pageable);
}
