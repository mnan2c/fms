package com.mnan2c.fms.service;

import com.mnan2c.fms.common.BaseCrudService;
import com.mnan2c.fms.controller.dto.NoticeDto;
import com.mnan2c.fms.entity.Notice;
import org.springframework.stereotype.Service;

@Service
public class NoticeService extends BaseCrudService<Notice, NoticeDto> {}
