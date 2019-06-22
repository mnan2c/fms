package com.mnan2c.fms.controller.converter;

import com.mnan2c.fms.common.BaseConverter;
import com.mnan2c.fms.controller.dto.NoticeDto;
import com.mnan2c.fms.entity.Notice;
import org.springframework.stereotype.Component;

@Component
public class NoticeConverter extends BaseConverter<Notice, NoticeDto> {}
