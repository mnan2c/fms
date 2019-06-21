package com.mnan2c.fms.repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
    implements BaseRepository<T, ID> {

  private static final int BATCH_SIZE = 500;

  private final EntityManager entityManager;

  public BaseRepositoryImpl(
      JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
    super(entityInformation, entityManager);
    this.entityManager = entityManager;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Object[]> listBySQL(String sql) {
    return entityManager.createNativeQuery(sql).getResultList();
  }

  @Override
  @Transactional
  public <S extends T> Iterable<S> batchSave(Iterable<S> var1) {
    Iterator<S> iterator = var1.iterator();
    int index = 0;
    while (iterator.hasNext()) {
      entityManager.persist(iterator.next());
      index++;
      if (index % BATCH_SIZE == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    if (index % BATCH_SIZE != 0) {
      entityManager.flush();
      entityManager.clear();
    }
    return var1;
  }

  @Override
  @Transactional
  public <S extends T> Iterable<S> batchUpdate(Iterable<S> var1) {
    Iterator<S> iterator = var1.iterator();
    int index = 0;
    while (iterator.hasNext()) {
      entityManager.merge(iterator.next());
      index++;
      if (index % BATCH_SIZE == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    if (index % BATCH_SIZE != 0) {
      entityManager.flush();
      entityManager.clear();
    }
    return var1;
  }
}
