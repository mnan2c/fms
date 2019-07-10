package com.mnan2c.fms.controller;

import com.mnan2c.fms.common.BaseCrudController;
import com.mnan2c.fms.controller.dto.TestPaperDto;
import com.mnan2c.fms.entity.TestPaper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/testpaper")
public class TestPaperController extends BaseCrudController<TestPaper, TestPaperDto> {}
