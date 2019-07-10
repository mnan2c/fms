package com.mnan2c.fms.common;

import com.mnan2c.fms.controller.dto.UserDto;
import com.mnan2c.fms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Slf4j
public abstract class BaseCrudController<E extends BaseEntity, D extends BaseDto> {

  @Inject private BaseCrudService<E, D> crudService;

  @Inject private UserService userService;

  @Inject protected HttpSession httpSession;

  @PostMapping
  public ResponseEntity create(@Valid @RequestBody D dto) {
    log.debug("Request to create entity: [{}]", dto);
    dto.setCreatedBy(getCurrentUserId());
    dto = crudService.create(dto);
    return ResponseEntity.ok(dto);
  }

  @PutMapping
  public ResponseEntity update(@Valid @RequestBody D dto) {
    log.debug("Request to udpate entity: [{}]", dto);
    dto.setCreatedBy(getCurrentUserId());
    dto = crudService.update(dto);
    return ResponseEntity.ok(dto);
  }

  @GetMapping("/{id}")
  public ResponseEntity getOne(@PathVariable("id") Integer id) {
    log.debug("Request to get entity by id: [{}]", id);
    D dto = crudService.findOne(id);
    return ResponseEntity.ok(dto);
  }

  @GetMapping
  public ResponseEntity getAll(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    log.debug("Request to get all entities");
    Pageable pageable = PageRequest.of(page, size);
    Page<D> dtos = crudService.findAll(pageable);
    return ResponseEntity.ok(dtos);
  }

  @GetMapping(params = {"ids"})
  public ResponseEntity getByIds(@RequestParam("ids") List<Integer> ids) {
    log.debug("Request to get entities by ids: [{}]", ids);
    List<D> dtos = crudService.findByIds(ids);
    return ResponseEntity.ok(dtos);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteById(@PathVariable("id") Integer id) {
    log.debug("request to delete entity by id: [{}]", id);
    crudService.delete(id);
    return ResponseEntity.ok(1);
  }

  /** 获取当前登录用户ID */
  protected Integer getCurrentUserId() {
    return (Integer) httpSession.getAttribute("userId");
  }

  protected UserDto getCurrentUser() {
    Integer userId = (Integer) httpSession.getAttribute("userId");
    UserDto user = userService.findOne(userId);
    return user;
  }
}
