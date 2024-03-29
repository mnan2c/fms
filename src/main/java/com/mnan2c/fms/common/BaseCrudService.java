package com.mnan2c.fms.common;

import com.mnan2c.fms.exception.BusinessException;
import com.mnan2c.fms.exception.ErrorConsts;
import com.mnan2c.fms.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public abstract class BaseCrudService<E extends BaseEntity, DTO extends BaseDto>
    implements CrudService<E, DTO> {

  @Inject private BaseRepository<E, Integer> repository;

  @Inject private BaseConverter<E, DTO> mapper;

  @Override
  public DTO create(DTO dto) {
    E entity = mapper.dtoToEntity(dto);
    entity = repository.save(entity);
    return mapper.entityToDto(entity);
  }

  @Override
  @Transactional
  public DTO update(DTO dto) throws BusinessException {
    if (dto.getId() == null) {
      throw BusinessException.instance(ErrorConsts.ID_IS_REQUIRED);
    }
    if (findOne(dto.getId()) == null) {
      throw BusinessException.instance(ErrorConsts.ENTITY_NOT_EXISTED);
    }
    E entity = mapper.dtoToEntity(dto);
    entity = repository.save(entity);
    return mapper.entityToDto(entity);
  }

  @Override
  public DTO findOne(Integer id) {
    return mapper.entityToDto(repository.getOne(id));
  }

  @Override
  public List<DTO> findByIds(List<Integer> ids) {
    if (CollectionUtils.isEmpty(ids)) {
      return null;
    }
    List<E> entities = repository.findAllById(ids);
    return mapper.entitiesToDtos(entities);
  }

  @Override
  public Page<DTO> findAll(Map<String, Object> query, Pageable pageable) {
    // TODO
    //    ExampleMatcher exampleMatcher = ExampleMatcher.matching();
    //    Example<DTO> example = Example.of(productCategory，exampleMatcher);
    //    repository.findAll(example, pageable);
    return null;
  }

  @Override
  public Page<DTO> findAll(Pageable pageable) {
    Page<E> page = repository.findAll(pageable);
    if (page.getContent() == null) {
      return null;
    }
    return new PageImpl<>(
        mapper.entitiesToDtos(page.getContent()), //
        pageable, //
        page.getTotalElements() //
        );
  }

  @Override
  public void delete(Integer id) {
    repository.deleteById(id);
  }

  @Override
  public E deltaUpdate(E entity) throws BusinessException {
    // TODO
    return null;
  }

  @Override
  public Page<DTO> findAll(String query, Pageable pageable) {
    Page<E> page = repository.findAll(query, pageable);
    return new PageImpl<>(
        mapper.entitiesToDtos(page.getContent()), //
        pageable, //
        page.getTotalElements() //
        );
  }

  @Override
  public List<DTO> findAll(String query) {
    List<E> list = repository.findAll(query);
    return mapper.entitiesToDtos(list);
  }

  @Override
  public int deleteByIds(List<Integer> ids) {
    return repository.deleteByIds(ids);
  }
}
