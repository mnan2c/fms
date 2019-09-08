package com.mnan2c.fms.controller.converter;

import com.mnan2c.fms.common.BaseConverter;
import com.mnan2c.fms.controller.dto.SigninConfigDto;
import com.mnan2c.fms.entity.SigninConfig;
import org.springframework.stereotype.Component;

@Component
public class SigninConfigConverter extends BaseConverter<SigninConfig, SigninConfigDto> {}
