package com.mnan2c.fms.controller;

import com.mnan2c.fms.common.BaseCrudController;
import com.mnan2c.fms.controller.dto.KnowledgeDetailDto;
import com.mnan2c.fms.entity.KnowledgeDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/knowledgedetail")
public class KnowledgeDetailController
    extends BaseCrudController<KnowledgeDetail, KnowledgeDetailDto> {

  @Override
  public ResponseEntity create(@Valid @RequestBody KnowledgeDetailDto dto) {
    Integer userId = getCurrentUserId();
    dto.setCreatedBy(userId);
    return super.create(dto);
  }

  @Override
  public ResponseEntity update(@Valid @RequestBody KnowledgeDetailDto dto) {
    Integer userId = getCurrentUserId();
    dto.setCreatedBy(userId);
    return super.update(dto);
  }
}
