package com.mnan2c.fms.controller;

import com.mnan2c.fms.common.BaseCrudController;
import com.mnan2c.fms.controller.dto.EbbinghausDto;
import com.mnan2c.fms.entity.Ebbinghaus;
import com.mnan2c.fms.repository.EbbinghausRepository;
import com.mnan2c.fms.utils.DateUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ebbinghaus")
public class EbbinghausController extends BaseCrudController<Ebbinghaus, EbbinghausDto> {
  @Inject private EbbinghausRepository ebbinghausRepository;

  @GetMapping("/ebbinghausContent")
  @ApiOperation(
      "根据当前日期获取1/2/7(一周)/14(2周)/30(一个月)/90(三个月)/365(一年)天前的内容，构造成map，根据key排序，如<'一天前','...'>")
  public Map<String, Ebbinghaus> getEbbinghausContent(
      @RequestParam("currentDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate currentDate) {
    Integer createdBy = getCurrentUserId();
    List<LocalDate> dateList =
        Arrays.asList(
            currentDate.minusDays(1),
            currentDate.minusDays(2),
            currentDate.minusWeeks(1),
            currentDate.minusWeeks(2),
            currentDate.minusMonths(1),
            currentDate.minusMonths(3),
            currentDate.minusYears(1),
            currentDate.minusYears(2),
            currentDate.minusYears(3));
    Map<String, Ebbinghaus> result =
        dateList.stream()
            .map(date -> ebbinghausRepository.findFirstByRecordDateAndCreatedBy(date, createdBy))
            .filter(Objects::nonNull)
            .collect(
                Collectors.toMap(
                    ebbinghaus -> {
                      LocalDate dataDate = ebbinghaus.getRecordDate();
                      if (dataDate.equals(dateList.get(0))) {
                        return "一天前";
                      } else if (dataDate.equals(dateList.get(1))) {
                        return "两天前";
                      } else if (dataDate.equals(dateList.get(2))) {
                        return "一周前";
                      } else if (dataDate.equals(dateList.get(3))) {
                        return "两周前";
                      } else if (dataDate.equals(dateList.get(4))) {
                        return "一个月前";
                      } else if (dataDate.equals(dateList.get(5))) {
                        return "三个月前";
                      } else if (dataDate.equals(dateList.get(6))) {
                        return "一年前";
                      } else if (dataDate.equals(dateList.get(7))) {
                        return "两年前";
                      } else if (dataDate.equals(dateList.get(8))) {
                        return "三年前";
                      }
                      return "几天前";
                    },
                    Function.identity()));
    return sortMapByKey(result, 1);
  }

  @GetMapping("/contentByMonth")
  @ApiOperation("获取某个月的每一天的内容列表")
  public Map<Integer, Ebbinghaus> getContentByMonth(
      @RequestParam("monthDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate monthDate) {
    Map<Integer, Ebbinghaus> resultMap = new HashMap<>();
    Integer createdBy = getCurrentUserId();
    LocalDate firstDayOfMonth = LocalDate.of(monthDate.getYear(), monthDate.getMonthValue(), 1);
    int currentMonthValue = monthDate.getMonthValue();
    for (int i = 1;
        i < firstDayOfMonth.getMonth().length(DateUtil.isLeapYear(firstDayOfMonth.getYear())) + 1
            && firstDayOfMonth.getMonthValue() == currentMonthValue;
        i++) {
      Ebbinghaus ebbinghaus =
          ebbinghausRepository.findFirstByRecordDateAndCreatedBy(firstDayOfMonth, createdBy);
      resultMap.put(i, ebbinghaus);
      firstDayOfMonth = firstDayOfMonth.plusDays(1);
    }
    return resultMap;
  }

  @GetMapping("/contentByDay")
  @ApiOperation("根据日期获取某一天的内容")
  public Ebbinghaus getContentByDay(
      @RequestParam("recordDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate recordDate) {
    Integer createdBy = getCurrentUserId();
    return ebbinghausRepository.findFirstByRecordDateAndCreatedBy(recordDate, createdBy);
  }

  @PostMapping("/save")
  public ResponseEntity saveItem(@Valid @RequestBody EbbinghausDto dto) {
    Integer createdBy = getCurrentUserId();
    dto.setCreatedBy(createdBy);
    return super.create(dto);
  }

  @PutMapping("/save")
  public ResponseEntity updateData(@Valid @RequestBody EbbinghausDto dto) {
    return super.update(dto);
  }

  private Map<String, Ebbinghaus> sortMapByKey(Map<String, Ebbinghaus> oriMap, Integer sort) {
    List<Map.Entry<String, Ebbinghaus>> list = new ArrayList<>(oriMap.entrySet());
    list.sort(
        Map.Entry.comparingByKey(
            (a, b) -> {
              if (sort == -1) {
                return compareKey(b, a);
              }
              return compareKey(a, b);
            }));

    Map<String, Ebbinghaus> result = new LinkedHashMap<>();
    for (Map.Entry<String, Ebbinghaus> entry : list) {
      result.put(entry.getKey(), entry.getValue());
    }
    return result;
  }

  private int compareKey(String key1, String key2) {
    List<String> digits = Arrays.asList("一", "两", "三");
    List<String> units = Arrays.asList("天", "周", "月", "年");
    int key1Digitindex = getKeyIndex(digits, key1);
    int key2Digitindex = getKeyIndex(digits, key2);
    int key1Unitindex = getKeyIndex(units, key1);
    int key2Unitindex = getKeyIndex(units, key2);
    if (key1Unitindex != key2Unitindex) {
      return key1Unitindex - key2Unitindex;
    }
    return key1Digitindex - key2Digitindex;
  }

  private int getKeyIndex(List<String> list, String key) {
    for (Character character : key.toCharArray()) {
      if (list.contains(character.toString())) {
        return list.indexOf(character.toString());
      }
    }
    return -1;
  }
}
