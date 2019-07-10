package com.mnan2c.fms.controller.converter;

import com.mnan2c.fms.common.BaseConverter;
import com.mnan2c.fms.controller.dto.TestPaperDto;
import com.mnan2c.fms.entity.TestPaper;
import org.springframework.stereotype.Component;

@Component
public class TestPaperConverter extends BaseConverter<TestPaper, TestPaperDto> {}
