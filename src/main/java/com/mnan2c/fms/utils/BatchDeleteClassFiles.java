package com.mnan2c.fms.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class BatchDeleteClassFiles {

  private static final String baseUrl =
      "G:\\workspace-for-prod\\fms\\src\\main\\java\\com\\mnan2c\\fms\\";

  public static void main(String[] args) {
    // 删除converter,dto,conroller,service,repository,entity
    String controllerPath = baseUrl + "controller\\";
    String servicePath = baseUrl + "service\\";
    String repositoryPath = baseUrl + "repository\\";
    String entityPath = baseUrl + "entity\\";
    String converterPath = baseUrl + "controller\\converter\\";
    String dtoPath = baseUrl + "controller\\dto\\";
    String voPath = baseUrl + "controller\\vo\\";
    String scannedEntityName = scanner("要删除的类对应的实体");
    Arrays.asList(controllerPath, servicePath, repositoryPath, entityPath, converterPath, dtoPath)
        .stream()
        .forEach(
            path -> {
              String[] fileNames = new File(path).list();
              for (String fileName : fileNames) {
                if (Arrays.asList(
                        scannedEntityName,
                        scannedEntityName + "Dto",
                        scannedEntityName + "VO",
                        scannedEntityName + "Controller",
                        scannedEntityName + "Service",
                        scannedEntityName + "Repository",
                        scannedEntityName + "Converter")
                    .contains(fileName.split("\\.")[0])) {
                  new File(path + fileName).delete();
                  System.out.println("删除文件：" + path + fileName);
                }
              }
            });
  }

  /** 读取控制台内容 */
  public static String scanner(String tips) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("请输入" + tips + "：");
    if (scanner.hasNext()) {
      String ipt = scanner.next();
      if (StringUtils.isAllUpperCase(Character.toString(ipt.charAt(0)))
          && StringUtils.isNotEmpty(ipt)) {
        return ipt;
      }
    }
    return scanner("要删除的类对应的实体");
  }
}
