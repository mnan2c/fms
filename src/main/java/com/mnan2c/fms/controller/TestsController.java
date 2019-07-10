package com.mnan2c.fms.controller;

import com.mnan2c.fms.common.BaseCrudController;
import com.mnan2c.fms.controller.dto.TestsDto;
import com.mnan2c.fms.entity.Tests;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tests")
public class TestsController extends BaseCrudController<Tests, TestsDto> {}
