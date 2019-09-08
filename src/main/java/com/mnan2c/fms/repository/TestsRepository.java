package com.mnan2c.fms.repository;

import com.mnan2c.fms.entity.Tests;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TestsRepository
    extends JpaSpecificationExecutor<Tests>, BaseRepository<Tests, Integer> {

  @Transactional(timeout = 10)
  @Modifying
  @Query("update Tests set answer = ?1 where id = ?2")
  int modifyById(String answer, Integer id);

  @Transactional(timeout = 10)
  @Modifying
  @Query("update Tests set isCorrect = ?1 where id = ?2")
  int goOverTheTest(Boolean isCorrect, Integer id);
}
