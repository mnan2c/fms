package com.mnan2c.fms.common;

import org.springframework.beans.BeanUtils;
import org.springframework.core.GenericTypeResolver;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class AbstractMapper<E extends AbstractEntity, D extends AbstractDto> {

  protected final Class<E> entityClazz;

  protected final Class<D> dtoClazz;

  @SuppressWarnings("unchecked")
  public AbstractMapper() {
    Class<?>[] genericTypes =
        GenericTypeResolver.resolveTypeArguments(getClass(), AbstractMapper.class);
    this.entityClazz = (Class<E>) genericTypes[0];
    this.dtoClazz = (Class<D>) genericTypes[1];
  }

  public D entityToDto(E entity, String... ignoreProperties) {
    if (entity == null) {
      return null;
    }
    D dto = BeanUtils.instantiateClass(this.dtoClazz);
    BeanUtils.copyProperties(entity, dto, ignoreProperties);
    return dto;
  }

  public E dtoToEntity(D dto, String... ignoreProperties) {
    if (dto == null) {
      return null;
    }
    E entity = BeanUtils.instantiateClass(this.entityClazz);
    BeanUtils.copyProperties(dto, entity, ignoreProperties);
    return entity;
  }

  public List<D> entitiesToDtos(List<E> entities) {
    if (CollectionUtils.isEmpty(entities)) {
      return null;
    }
    return entities.stream().map(entity -> entityToDto(entity)).collect(Collectors.toList());
  }

  public List<E> dtosToEntities(List<D> dtos) {
    if (CollectionUtils.isEmpty(dtos)) {
      return null;
    }
    return dtos.stream().map(dto -> dtoToEntity(dto)).collect(Collectors.toList());
  }
}
