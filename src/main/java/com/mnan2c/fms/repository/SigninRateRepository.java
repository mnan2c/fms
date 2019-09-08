package com.mnan2c.fms.repository;

import com.mnan2c.fms.entity.SigninRate;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface SigninRateRepository
    extends JpaSpecificationExecutor<SigninRate>, BaseRepository<SigninRate, Integer> {

  @Transactional
  @Modifying
  @Query(
      value = "update signin_rate set rate=?2 where create_date=?1 and user_id=?3",
      nativeQuery = true)
  void updateSigninRate(LocalDate createDate, BigDecimal rate, Integer userId);

  List<SigninRate> findAllByCreateDateAndUserId(LocalDate createDate, Integer userId);

  @Query(
      value = "select * from signin_rate where create_date>?1 and user_id=?2",
      nativeQuery = true)
  List<SigninRate> findLastNDaysRates(LocalDate createDate, Integer userId);

  @Query(
      value = "select * from signin_rate where user_id=?1 order by create_date desc limit 1",
      nativeQuery = true)
  SigninRate findLastSigninRate(Integer userId);

  @Query(value = "select max(continuous) from signin_rate where user_id=?1", nativeQuery = true)
  Integer findMaxContinuous(Integer userId);
}
