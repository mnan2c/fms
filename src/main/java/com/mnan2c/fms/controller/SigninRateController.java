package com.mnan2c.fms.controller;

import com.mnan2c.fms.entity.SigninRate;
import com.mnan2c.fms.repository.SigninRateRepository;
import com.mnan2c.fms.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/signinrate")
public class SigninRateController {
  @Inject private SigninRateRepository signinRateRepository;
  @Inject protected HttpSession httpSession;

  @GetMapping
  public List<Map<LocalDate, BigDecimal>> getAll(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    Integer userId = (Integer) httpSession.getAttribute("userId");
    List<SigninRate> result =
        signinRateRepository.findLastNDaysRates(DateUtil.getDateYMD().minusDays(size), userId);
    if (CollectionUtils.isEmpty(result)) {
      return new ArrayList<>();
    }
    Map<LocalDate, BigDecimal> map =
        result.stream()
            .collect(Collectors.toMap(item -> item.getCreateDate(), item -> item.getRate()));
    return DateUtil.buildLastNDayDateList(size).stream()
        .map(
            localDate -> {
              Map<LocalDate, BigDecimal> tmp = new HashMap<>();
              if (map.get(localDate) != null) {
                tmp.put(localDate, map.get(localDate));
              } else {
                tmp.put(localDate, new BigDecimal(0));
              }
              return tmp;
            })
        .collect(Collectors.toList());
  }

  @GetMapping("/last")
  public Map<String, Object> findLastSigninRate() {
    Integer userId = (Integer) httpSession.getAttribute("userId");
    Integer maxContinuous = signinRateRepository.findMaxContinuous(userId);
    SigninRate lastRate = signinRateRepository.findLastSigninRate(userId);
    Map<String, Object> result = new HashMap<>();
    result.put("maxContinuous", maxContinuous);
    result.put("lastRate", lastRate);
    return result;
  }
}
