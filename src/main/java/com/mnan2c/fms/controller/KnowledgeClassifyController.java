package com.mnan2c.fms.controller;

import com.mnan2c.fms.common.BaseCrudController;
import com.mnan2c.fms.controller.dto.KnowledgeClassifyDto;
import com.mnan2c.fms.entity.KnowledgeClassify;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/knowledgeclassify")
public class KnowledgeClassifyController
    extends BaseCrudController<KnowledgeClassify, KnowledgeClassifyDto> {}
