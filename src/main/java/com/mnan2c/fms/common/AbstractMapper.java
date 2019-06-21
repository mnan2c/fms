package com.mnan2c.fms.common;

import org.springframework.beans.BeanUtils;
import org.springframework.core.GenericTypeResolver;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AbstractMapper<E extends AbstractEntity, D extends AbstractDto> {

  protected final Class<E> entityClazz;

  protected final Class<D> dtoClazz;

  private static final List<String> standard_ignore_properties = Arrays.asList("createdDate");

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
    List<String> allIgnore = new ArrayList<>();
    allIgnore.addAll(Arrays.asList(ignoreProperties));
    allIgnore.addAll(standard_ignore_properties);
    BeanUtils.copyProperties(dto, entity, allIgnore.toArray(new String[allIgnore.size()]));
    return entity;
  }

  public List<D> entitiesToDtos(List<E> entities) {
    if (CollectionUtils.isEmpty(entities)) {
      return new ArrayList<>();
    }
    return entities.stream().map(entity -> entityToDto(entity)).collect(Collectors.toList());
  }

  public List<E> dtosToEntities(List<D> dtos) {
    if (CollectionUtils.isEmpty(dtos)) {
      return new ArrayList<>();
    }
    return dtos.stream().map(dto -> dtoToEntity(dto)).collect(Collectors.toList());
  }
}
