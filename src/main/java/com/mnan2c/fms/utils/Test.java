package com.mnan2c.fms.utils;

import java.time.LocalDate;

public class Test {

  public static void main(String[] args) {
    LocalDate now = LocalDate.now();
    System.out.println(now);
    now = now.plusDays(1);
    System.out.println(now);
  }
}
