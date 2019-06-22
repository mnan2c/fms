package com.mnan2c.fms.controller.converter;

import com.mnan2c.fms.common.BaseConverter;
import com.mnan2c.fms.controller.dto.DemoDto;
import com.mnan2c.fms.entity.Demo;
import org.springframework.stereotype.Component;

@Component
public class DemoConverter extends BaseConverter<Demo, DemoDto> {}
