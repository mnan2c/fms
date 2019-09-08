package com.mnan2c.fms.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class DateUtil {

  public static final String DATE_FORMAT = "yyyy-MM-dd";
  public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
  public static final String TIME_FORMAT = "HH:mm:ss";

  public static LocalDate getDateYMD() {
    return LocalDate.now(ZoneId.of("UTC+8"));
  }

  public static LocalDate getDateYMD(ZoneId zoneId) {
    return LocalDate.now(zoneId);
  }

  /** 把过去若干天的日期构建成列表 */
  public static List<LocalDate> buildLastNDayDateList(Integer count) {
    List<LocalDate> dates = new ArrayList<>();
    for (int i = count - 1; i >= 0; i--) {
      dates.add(LocalDate.now(ZoneId.of("UTC+8")).minusDays(i));
    }
    return dates;
  }

  /** 判断年份是否为闰年 判断闰年的条件， 能被4整除同时不能被100整除，或者能被400整除 */
  public static boolean isLeapYear(int year) {
    boolean isLeapYear = false;
    if (year % 4 == 0 && year % 100 != 0) {
      isLeapYear = true;
    } else if (year % 400 == 0) {
      isLeapYear = true;
    }
    return isLeapYear;
  }
}
