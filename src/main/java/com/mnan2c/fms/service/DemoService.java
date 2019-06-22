package com.mnan2c.fms.service;

import com.mnan2c.fms.common.BaseCrudService;
import com.mnan2c.fms.controller.dto.DemoDto;
import com.mnan2c.fms.entity.Demo;
import org.springframework.stereotype.Service;

@Service
public class DemoService extends BaseCrudService<Demo, DemoDto> {}
