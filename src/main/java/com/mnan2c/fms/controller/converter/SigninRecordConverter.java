package com.mnan2c.fms.controller.converter;

import com.mnan2c.fms.common.BaseConverter;
import com.mnan2c.fms.controller.dto.SigninRecordDto;
import com.mnan2c.fms.entity.SigninRecord;
import org.springframework.stereotype.Component;

@Component
public class SigninRecordConverter extends BaseConverter<SigninRecord, SigninRecordDto> {}
