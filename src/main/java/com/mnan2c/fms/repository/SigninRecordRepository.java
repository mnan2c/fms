package com.mnan2c.fms.repository;

import com.mnan2c.fms.entity.SigninRecord;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SigninRecordRepository
    extends JpaSpecificationExecutor<SigninRecord>, BaseRepository<SigninRecord, Integer> {

  @Query(
      nativeQuery = true,
      value =
          "SELECT count(*) FROM signin_record where DATE_FORMAT(created_date,'%Y-%m-%d')=?1 and signin_config_id=?2")
  int checkIfAlreadySignin(LocalDate currentDate, Integer signinConfigId);

  @Query(
      nativeQuery = true,
      value = "SELECT * FROM signin_record where DATE_FORMAT(created_date,'%Y-%m-%d')=?1")
  List<SigninRecord> findTodaySigninRecords(LocalDate currentDate);

  @Query(
      nativeQuery = true,
      value = "SELECT count(*) FROM signin_record where DATE_FORMAT(created_date,'%Y-%m-%d')=?1")
  long countTodaySigninRecords(LocalDate currentDate);
}
