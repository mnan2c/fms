package com.mnan2c.fms.service;

import com.mnan2c.fms.common.BaseCrudService;
import com.mnan2c.fms.controller.converter.PlanConverter;
import com.mnan2c.fms.controller.dto.PlanDto;
import com.mnan2c.fms.entity.Plan;
import com.mnan2c.fms.repository.PlanRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class PlanService extends BaseCrudService<Plan, PlanDto> {
  @Inject private PlanRepository planRepository;
  @Inject private PlanConverter planConverter;

  public Page<PlanDto> findPlansByCreatedByAndStatusIn(
      Integer createdBy, Integer type, List<Integer> status, Pageable pageable) {
    Page<Plan> pageData =
        planRepository.findPlansByCreatedByAndTypeAndStatusIn(createdBy, type, status, pageable);
    List<PlanDto> dtos = planConverter.entitiesToDtos(pageData.getContent());
    return new PageImpl<>(dtos, pageable, pageData.getTotalElements());
  }
}
