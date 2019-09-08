package com.mnan2c.fms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

// 表示该接口不会创建这个接口的实例
// (我们原来定义的StudentPageRepository这些，Spring Data JPA的基础组件都会自动为我们创建一个实例对象，
// 加上这个annotation，spring data jpa的基础组件就不会再为我们创建它的实例)
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable>
    extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

  List<Object[]> listBySQL(String sql);

  <S extends T> Iterable<S> batchSave(Iterable<S> var1);

  <S extends T> Iterable<S> batchUpdate(Iterable<S> var1);

  Page<T> findAll(String query, Pageable pageable);

  List<T> findAll(String query);

  int deleteByIds(List<ID> ids);
}
