package com.mnan2c.fms.service;

import com.mnan2c.fms.common.BaseCrudService;
import com.mnan2c.fms.controller.dto.TestsDto;
import com.mnan2c.fms.entity.Tests;
import org.springframework.stereotype.Service;

@Service
public class TestsService extends BaseCrudService<Tests, TestsDto> {}
