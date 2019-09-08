package com.mnan2c.fms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mnan2c.fms.common.BaseCrudController;
import com.mnan2c.fms.controller.dto.TestsDto;
import com.mnan2c.fms.entity.Tests;
import com.mnan2c.fms.repository.TestsRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/tests")
public class TestsController extends BaseCrudController<Tests, TestsDto> {
  @Inject private TestsRepository testsRepository;

  @PostMapping("/submitAnswer")
  public void submitAnswer(@RequestParam(value = "answer", required = false) String answer) {
    JSONObject obj = JSON.parseObject(answer);
    obj.entrySet().stream()
        .forEach(
            entry -> {
              Integer testId = Integer.valueOf(entry.getKey());
              String userAnswer;
              if (entry.getValue() instanceof JSONArray) {
                userAnswer = JSONArray.toJSONString(entry.getValue());
              } else {
                userAnswer = (String) entry.getValue();
              }
              testsRepository.modifyById(userAnswer, testId);
            });
  }

  @PostMapping("/goOver")
  public void goOver(@RequestParam(value = "result", required = false) String result) {
    JSONObject obj = JSON.parseObject(result);
    obj.entrySet().stream()
        .forEach(
            entry -> {
              Integer testId = Integer.valueOf(entry.getKey());
              Boolean isCorrect = (Boolean) entry.getValue();
              testsRepository.goOverTheTest(isCorrect, testId);
            });
  }
}
