package com.mnan2c.fms.controller.converter;

import com.mnan2c.fms.common.BaseConverter;
import com.mnan2c.fms.controller.dto.TestsDto;
import com.mnan2c.fms.entity.Tests;
import org.springframework.stereotype.Component;

@Component
public class TestsConverter extends BaseConverter<Tests, TestsDto> {}
