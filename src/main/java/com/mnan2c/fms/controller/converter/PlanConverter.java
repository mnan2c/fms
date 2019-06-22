package com.mnan2c.fms.controller.converter;

import com.mnan2c.fms.common.BaseConverter;
import com.mnan2c.fms.controller.dto.PlanDto;
import com.mnan2c.fms.entity.Plan;
import org.springframework.stereotype.Component;

@Component
public class PlanConverter extends BaseConverter<Plan, PlanDto> {}
