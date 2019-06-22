package com.mnan2c.fms.controller;

import com.mnan2c.fms.common.BaseCrudController;
import com.mnan2c.fms.controller.dto.NoticeDto;
import com.mnan2c.fms.entity.Notice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notice")
public class NoticeController extends BaseCrudController<Notice, NoticeDto> {}
