package com.mnan2c.fms.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

@Slf4j
public abstract class AbstractCrudController<E extends AbstractEntity, D extends AbstractDto> {

  @Inject private AbstractCrudService<E, D> crudService;

  @PostMapping
  public ResponseEntity create(@Valid @RequestBody D dto) {
    log.debug("Request to create entity: [{}]", dto);
    dto = crudService.create(dto);
    return ResponseEntity.ok(dto);
  }

  @PutMapping
  public ResponseEntity update(@Valid @RequestBody D dto) {
    log.debug("Request to udpate entity: [{}]", dto);
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
}
