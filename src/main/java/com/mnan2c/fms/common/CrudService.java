package com.mnan2c.fms.common;

import com.mnan2c.fms.exception.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface CrudService<E, DTO> {

  DTO create(DTO dto) throws BusinessException;

  DTO update(DTO dto) throws BusinessException;

  Page<DTO> findAll(Map<String, Object> query, Pageable pageable);

  Page<DTO> findAll(Pageable pageable);

  Page<DTO> findAll(String query, Pageable pageable);

  List<DTO> findAll(String query);

  DTO findOne(Integer id);

  void delete(Integer id);

  List<DTO> findByIds(List<Integer> ids);

  E deltaUpdate(E entity) throws BusinessException;

  int deleteByIds(List<Integer> ids);
}
