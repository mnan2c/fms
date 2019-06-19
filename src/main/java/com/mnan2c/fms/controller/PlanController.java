package com.mnan2c.fms.controller;

import com.mnan2c.fms.common.AbstractCrudController;
import com.mnan2c.fms.controller.dto.PlanDto;
import com.mnan2c.fms.entity.Plan;
import com.mnan2c.fms.service.PlanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/plans")
public class PlanController extends AbstractCrudController<Plan, PlanDto> {
  @Inject private PlanService planService;

  // 排序：根据状态升序，创建时间倒序，优先级升序。
  // 创建的时候自动给CreatedDate赋值
  // 删除：物理删除
  @Override
  @GetMapping
  public ResponseEntity getAll(
      @RequestParam(value = "page", defaultValue = "1") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    Sort sort =
        new Sort(Sort.Direction.ASC, "status", "priority")
            .and(new Sort(Sort.Direction.DESC, "createdDate"));
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<PlanDto> dtos = planService.findAll(pageable);
    return ResponseEntity.ok(dtos);
  }
}
