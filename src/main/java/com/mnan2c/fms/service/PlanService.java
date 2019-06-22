package com.mnan2c.fms.service;

import com.mnan2c.fms.common.BaseCrudService;
import com.mnan2c.fms.controller.dto.PlanDto;
import com.mnan2c.fms.entity.Plan;
import org.springframework.stereotype.Service;

@Service
public class PlanService extends BaseCrudService<Plan, PlanDto> {}
