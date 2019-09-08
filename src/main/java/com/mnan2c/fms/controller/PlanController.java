package com.mnan2c.fms.controller;

import com.mnan2c.fms.common.BaseCrudController;
import com.mnan2c.fms.controller.dto.PlanDto;
import com.mnan2c.fms.entity.Plan;
import com.mnan2c.fms.repository.PlanRepository;
import com.mnan2c.fms.service.PlanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api/plans")
public class PlanController extends BaseCrudController<Plan, PlanDto> {
  @Inject private PlanService planService;
  @Inject private PlanRepository planRepository;

  // 排序：根据状态、优先级升序，创建时间倒序。
  // 创建的时候自动给CreatedDate赋值
  // 删除：物理删除
  @GetMapping("/page")
  public ResponseEntity getAllByStatus(
      @RequestParam("type") Integer type,
      @RequestParam(value = "page", defaultValue = "1") int page,
      @RequestParam(value = "status", required = false) List<Integer> status,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    Sort sort =
        Sort.by(Sort.Direction.ASC, "status", "priority")
            .and(Sort.by(Sort.Direction.DESC, "createdDate"));
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<PlanDto> dtos =
        planService.findPlansByCreatedByAndStatusIn(getCurrentUserId(), type, status, pageable);
    return ResponseEntity.ok(dtos);
  }

  @PutMapping("/complete/{id}")
  public int completePlan(@PathVariable Integer id) {
    return planRepository.completePlan(id);
  }
}
