package com.mnan2c.fms.service;

import com.mnan2c.fms.entity.SigninRate;
import com.mnan2c.fms.repository.SigninRateRepository;
import com.mnan2c.fms.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class SigninRateService {

  @Inject private SigninRateRepository signinRateRepository;

  @Transactional
  public void upsertSigninRate(LocalDate createDate, BigDecimal rate, Integer userId) {
    List<SigninRate> rates = signinRateRepository.findAllByCreateDateAndUserId(createDate, userId);
    SigninRate signinRate;
    log.warn("rates: [{}]", rates);
    if (CollectionUtils.isEmpty(rates)) {
      signinRate = new SigninRate();
      signinRate.setCreateDate(createDate);
      signinRate.setContinuous(getContinuous(userId) + 1);
      signinRate.setUserId(userId);
    } else {
      signinRate = rates.get(0);
    }
    signinRate.setRate(rate);
    log.warn("signinRate: [{}]", signinRate);
    signinRateRepository.save(signinRate);
  }

  private Integer getContinuous(Integer userId) {
    SigninRate signinRate = signinRateRepository.findLastSigninRate(userId);
    if (signinRate == null
        || !DateUtil.getDateYMD().equals(signinRate.getCreateDate())
            && !DateUtil.getDateYMD().minusDays(1).equals(signinRate.getCreateDate())
        || signinRate.getContinuous() == null) {
      return 0;
    }
    return signinRate.getContinuous();
  }
}
