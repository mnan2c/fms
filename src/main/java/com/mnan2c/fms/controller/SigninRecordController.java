package com.mnan2c.fms.controller;

import com.mnan2c.fms.common.BaseCrudController;
import com.mnan2c.fms.controller.dto.SigninRecordDto;
import com.mnan2c.fms.entity.SigninRecord;
import com.mnan2c.fms.exception.BusinessException;
import com.mnan2c.fms.exception.ErrorConsts;
import com.mnan2c.fms.repository.SigninConfigRepository;
import com.mnan2c.fms.repository.SigninRecordRepository;
import com.mnan2c.fms.service.SigninRateService;
import com.mnan2c.fms.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

// 1. 连续打卡

@Slf4j
@RestController
@RequestMapping("/api/signinrecord")
public class SigninRecordController extends BaseCrudController<SigninRecord, SigninRecordDto> {
  @Inject private SigninRecordRepository signinRecordRepository;
  @Inject private SigninConfigRepository signinConfigRepository;
  @Inject private SigninRateService signinRateService;

  @PostMapping("/signin/{signinConfigId}")
  public SigninRecord signin(@PathVariable("signinConfigId") Integer signinConfigId) {
    int existedCount =
        signinRecordRepository.checkIfAlreadySignin(DateUtil.getDateYMD(), signinConfigId);
    if (existedCount > 0) {
      log.warn("already signin before.");
      throw BusinessException.instance(ErrorConsts.ALREADY_SIGNIN);
    }
    // 记录签到
    SigninRecord signinRecord = new SigninRecord();
    signinRecord.setSigninConfigId(signinConfigId);
    signinRecord.setCreatedBy(getCurrentUserId());
    SigninRecord result = signinRecordRepository.save(signinRecord);
    // 获取签到项的总数量、已签到数量，计算签到比例
    long totalSigninConfig = signinConfigRepository.count();
    LocalDate today = DateUtil.getDateYMD();
    long signinCount = signinRecordRepository.countTodaySigninRecords(today);
    BigDecimal rate = new BigDecimal((double) signinCount / (double) totalSigninConfig);
    // 保存签到比例
    signinRateService.upsertSigninRate(today, rate, getCurrentUserId());
    return result;
  }

  @GetMapping("/today")
  public List<SigninRecord> getTodayRecords() {
    return signinRecordRepository.findTodaySigninRecords(DateUtil.getDateYMD());
  }
}
