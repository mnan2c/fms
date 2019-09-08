package com.mnan2c.fms.repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mnan2c.fms.enums.QueryTypeEnum;
import com.mnan2c.fms.exception.BusinessException;
import com.mnan2c.fms.exception.ErrorConsts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

@Slf4j
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
    implements BaseRepository<T, ID> {

  private static final int BATCH_SIZE = 500;

  private final EntityManager entityManager;

  private final JpaEntityInformation<T, ?> entityInformation;

  public BaseRepositoryImpl(
      JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
    super(entityInformation, entityManager);
    this.entityInformation = entityInformation;
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

  /**
   * 根据ids删除实体。
   *
   * @param ids
   * @return 返回成功删除的数量，如果传入的ids为空，返回0
   */
  @Override
  @Transactional
  public int deleteByIds(List<ID> ids) {
    if (CollectionUtils.isEmpty(ids)) return 0;

    StringBuilder builder =
        new StringBuilder(String.format("delete from %s where", entityInformation.getEntityName()));
    Iterator<ID> iterator = ids.iterator();
    int i = 0;
    while (iterator.hasNext()) {
      iterator.next();
      builder.append(String.format(" id= ?%d", ++i));
      if (iterator.hasNext()) {
        builder.append(" or");
      }
    }
    Query query = entityManager.createQuery(builder.toString());
    iterator = ids.iterator();
    i = 0;
    while (iterator.hasNext()) {
      query.setParameter(++i, iterator.next());
    }

    int deletedCount = query.executeUpdate();
    entityManager.flush();
    entityManager.clear();
    return deletedCount;
  }

  @Override
  public List<T> findAll(String query) {
    if (StringUtils.isBlank(query)) {
      return findAll();
    }
    JSONObject jsonObject = JSON.parseObject(query);
    Specification<T> spec = getSpecInstance(jsonObject);
    return findAll(spec);
  }

  @Override
  public Page<T> findAll(String query, Pageable pageable) {

    if (StringUtils.isBlank(query)) {
      return findAll(pageable);
    }
    JSONObject jsonObject = JSON.parseObject(query);
    Specification<T> spec = getSpecInstance(jsonObject);
    Page<T> page = findAll(spec, pageable);
    return page;
  }

  private Predicate getPredicate(String[] arr, Object value, Root<T> root, CriteriaBuilder cb) {
    if (arr.length == 1) {
      return cb.equal(root.get(arr[0]).as(value.getClass()), value);
    }
    if (QueryTypeEnum.like.name().equals(arr[1])) {
      return cb.like(root.get(arr[0]).as(String.class), String.format("%%%s%%", value));
    }
    if (QueryTypeEnum.ne.name().equals(arr[1])) {
      return cb.notEqual(root.get(arr[0]).as(value.getClass()), value);
    }
    if (QueryTypeEnum.lt.name().equals(arr[1])) {
      return getLessThanPredicate(arr, value, root, cb);
    }
    if (QueryTypeEnum.lte.name().equals(arr[1])) {
      return getLessThanOrEqualToPredicate(arr, value, root, cb);
    }
    if (QueryTypeEnum.gt.name().equals(arr[1])) {
      return getGreaterThanPredicate(arr, value, root, cb);
    }
    if (QueryTypeEnum.gte.name().equals(arr[1])) {
      return getGreaterThanOrEqualToPredicate(arr, value, root, cb);
    }
    log.warn("unsupported operation: [{}]", arr[1]);
    throw BusinessException.instance(ErrorConsts.UNSUPPORTED_OPERATION.getCode(), arr[1]);
  }

  private Predicate getLessThanPredicate(
      String[] arr, Object value, Root<T> root, CriteriaBuilder cb) {
    if (Integer.class == value.getClass()) {
      return cb.lessThan(root.get(arr[0]).as(Integer.class), (int) value);
    }
    if (Long.class == value.getClass()) {
      return cb.lessThan(root.get(arr[0]).as(Long.class), (long) value);
    }
    if (Double.class == value.getClass()) {
      return cb.lessThan(root.get(arr[0]).as(Double.class), (double) value);
    }
    if (Float.class == value.getClass()) {
      return cb.lessThan(root.get(arr[0]).as(Float.class), (float) value);
    }
    if (Timestamp.class == value.getClass()) {
      return cb.lessThan(root.get(arr[0]).as(Timestamp.class), (Timestamp) value);
    }
    if (Date.class == value.getClass()) {
      return cb.lessThan(root.get(arr[0]).as(Date.class), (Date) value);
    }
    return cb.lessThan(root.get(arr[0]).as(String.class), String.valueOf(value));
  }

  private Predicate getLessThanOrEqualToPredicate(
      String[] arr, Object value, Root<T> root, CriteriaBuilder cb) {
    if (Integer.class == value.getClass()) {
      return cb.lessThanOrEqualTo(root.get(arr[0]).as(Integer.class), (int) value);
    }
    if (Long.class == value.getClass()) {
      return cb.lessThanOrEqualTo(root.get(arr[0]).as(Long.class), (long) value);
    }
    if (Double.class == value.getClass()) {
      return cb.lessThanOrEqualTo(root.get(arr[0]).as(Double.class), (double) value);
    }
    if (Float.class == value.getClass()) {
      return cb.lessThanOrEqualTo(root.get(arr[0]).as(Float.class), (float) value);
    }
    if (Timestamp.class == value.getClass()) {
      return cb.lessThanOrEqualTo(root.get(arr[0]).as(Timestamp.class), (Timestamp) value);
    }
    if (Date.class == value.getClass()) {
      return cb.lessThanOrEqualTo(root.get(arr[0]).as(Date.class), (Date) value);
    }
    if (LocalDate.class == value.getClass()) {
      return cb.lessThanOrEqualTo(root.get(arr[0]).as(LocalDate.class), (LocalDate) value);
    }
    if (LocalDateTime.class == value.getClass()) {
      return cb.lessThanOrEqualTo(root.get(arr[0]).as(LocalDateTime.class), (LocalDateTime) value);
    }
    if (LocalTime.class == value.getClass()) {
      return cb.lessThanOrEqualTo(root.get(arr[0]).as(LocalTime.class), (LocalTime) value);
    }
    return cb.lessThanOrEqualTo(root.get(arr[0]).as(String.class), String.valueOf(value));
  }

  private Predicate getGreaterThanPredicate(
      String[] arr, Object value, Root<T> root, CriteriaBuilder cb) {
    if (Integer.class == value.getClass()) {
      return cb.greaterThan(root.get(arr[0]).as(Integer.class), (int) value);
    }
    if (Long.class == value.getClass()) {
      return cb.greaterThan(root.get(arr[0]).as(Long.class), (long) value);
    }
    if (Double.class == value.getClass()) {
      return cb.greaterThan(root.get(arr[0]).as(Double.class), (double) value);
    }
    if (Float.class == value.getClass()) {
      return cb.greaterThan(root.get(arr[0]).as(Float.class), (float) value);
    }
    if (Timestamp.class == value.getClass()) {
      return cb.greaterThan(root.get(arr[0]).as(Timestamp.class), (Timestamp) value);
    }
    if (Date.class == value.getClass()) {
      return cb.greaterThan(root.get(arr[0]).as(Date.class), (Date) value);
    }
    if (LocalDate.class == value.getClass()) {
      return cb.greaterThan(root.get(arr[0]).as(LocalDate.class), (LocalDate) value);
    }
    if (LocalDateTime.class == value.getClass()) {
      return cb.greaterThan(root.get(arr[0]).as(LocalDateTime.class), (LocalDateTime) value);
    }
    if (LocalTime.class == value.getClass()) {
      return cb.greaterThan(root.get(arr[0]).as(LocalTime.class), (LocalTime) value);
    }
    return cb.greaterThan(root.get(arr[0]).as(String.class), String.valueOf(value));
  }

  private Predicate getGreaterThanOrEqualToPredicate(
      String[] arr, Object value, Root<T> root, CriteriaBuilder cb) {
    if (Integer.class == value.getClass()) {
      return cb.greaterThanOrEqualTo(root.get(arr[0]).as(Integer.class), (int) value);
    }
    if (Long.class == value.getClass()) {
      return cb.greaterThanOrEqualTo(root.get(arr[0]).as(Long.class), (long) value);
    }
    if (Double.class == value.getClass()) {
      return cb.greaterThanOrEqualTo(root.get(arr[0]).as(Double.class), (double) value);
    }
    if (Float.class == value.getClass()) {
      return cb.greaterThanOrEqualTo(root.get(arr[0]).as(Float.class), (float) value);
    }
    if (Timestamp.class == value.getClass()) {
      return cb.greaterThanOrEqualTo(root.get(arr[0]).as(Timestamp.class), (Timestamp) value);
    }
    if (Date.class == value.getClass()) {
      return cb.greaterThanOrEqualTo(root.get(arr[0]).as(Date.class), (Date) value);
    }
    if (LocalDate.class == value.getClass()) {
      return cb.greaterThanOrEqualTo(root.get(arr[0]).as(LocalDate.class), (LocalDate) value);
    }
    if (LocalDateTime.class == value.getClass()) {
      return cb.greaterThanOrEqualTo(
          root.get(arr[0]).as(LocalDateTime.class), (LocalDateTime) value);
    }
    if (LocalTime.class == value.getClass()) {
      return cb.greaterThanOrEqualTo(root.get(arr[0]).as(LocalTime.class), (LocalTime) value);
    }
    return cb.lessThanOrEqualTo(root.get(arr[0]).as(String.class), String.valueOf(value));
  }

  private Specification<T> getSpecInstance(JSONObject jsonObject) {
    return (Specification<T>)
        (root, query1, cb) -> {
          List<Predicate> list = new ArrayList<>();
          for (Entry<String, Object> entry : jsonObject.entrySet()) {
            Object value = entry.getValue();
            if (value == null || StringUtils.isBlank(value.toString())) {
              continue;
            }
            String key = entry.getKey();
            String[] arr = key.split(":");
            Predicate predicate = getPredicate(arr, value, root, cb);
            list.add(predicate);
          }
          Predicate[] p = new Predicate[list.size()];
          return cb.and(list.toArray(p));
        };
  }
}
