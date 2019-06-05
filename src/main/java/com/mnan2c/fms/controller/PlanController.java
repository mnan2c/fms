package com.mnan2c.fms.controller;

import com.mnan2c.fms.common.AbstractCrudController;
import com.mnan2c.fms.controller.dto.PlanDto;
import com.mnan2c.fms.entity.Plan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plans")
public class PlanController extends AbstractCrudController<Plan, PlanDto> {}
