package com.mnan2c.fms.utils;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 *
 *
 * <pre>
 * 用于批量创建类文件，规则如下：
 * 1. 所有文件基于entity创建，entity可以通过CodeGenerator创建；
 * 2. 基于entity创建dto：复制entity到新的文件，文件重命名加Dto，然后替换文件中的字符串（类名加Dto），
 *     基类替换为dto的基类；
 * 3. 基于entity与模板类创建controller/service/repository/converter:
 *   （1）查询entity列表，查看需要创建哪些类；
 *   （2）如果要创建的类已存在，则忽略；
 *   （3）将模板类复制到新的文件，根据entity的名称对新文件重命名，类重命名，controller里的mappling地址替换；
 * </pre>
 */
public class BatchCreateClassFilesUtil {
  private static final String baseUrl =
      "G:\\workspace-for-prod\\fms\\src\\main\\java\\com\\mnan2c\\fms\\";

  public static void main(String[] args) throws IOException {
    String entityPath = baseUrl + "\\entity\\";
    File entityDir = new File(entityPath);
    String[] entities = entityDir.list();
    // such as Demo.java
    // 这里添加了过滤条件，预防entity下面如果有目录的情况，如果目录名也带.java，我无话可说
    List<String> entityNames =
        Arrays.asList(entities).stream()
            .filter(name -> name.contains(".java"))
            .collect(Collectors.toList());
    String controllerPath = baseUrl + "controller\\";
    String servicePath = baseUrl + "service\\";
    String repositoryPath = baseUrl + "repository\\";
    String converterPath = baseUrl + "controller\\converter\\";
    // 根据entity创建dto（后续可以手动修改）
    int dtoCount = createDtoFilesFromEntityFile();
    int controllerCount =
        createClassFileFromEntity(
            controllerPath, "DemoController.java", entityNames, "Controller.java");
    int serviceCount =
        createClassFileFromEntity(servicePath, "DemoService.java", entityNames, "Service.java");
    int repositoryCount =
        createClassFileFromEntity(
            repositoryPath, "DemoRepository.java", entityNames, "Repository.java");
    int converterCount =
        createClassFileFromEntity(
            converterPath, "DemoConverter.java", entityNames, "Converter.java");
    System.out.println(
        String.format(
            "共创建%s个controller, %s个service，%s个repository，%s个converter，%s个dto！",
            controllerCount, serviceCount, repositoryCount, converterCount, dtoCount));
  }

  /**
   * 复制样板文件到新建的文件中; 根据entity创建converter、controller、repository、service
   *
   * @param path 文件的目录路径
   * @param fileName 样本文件名
   * @param entities 实体类的文件名列表
   * @param suffix 后缀，如Controller.java,Service.java,Repository.java,Converter.java
   * @throws IOException
   */
  public static int createClassFileFromEntity(
      String path, String fileName, List<String> entities, String suffix) throws IOException {
    Integer successCount = 0;
    // 样本文件，复制，重命名，字符串替换
    String srcPath = path + fileName;
    for (String destEntityName : entities) {
      String destPath = path + destEntityName.split("\\.")[0] + suffix;
      // 如果文件已存在，则不创建
      if (!new File(destPath).exists()) {
        File newFile = copyFileContent(srcPath, destPath);
        replacTextContent(newFile, "Demo", destEntityName.split("\\.")[0]);
        replacTextContent(newFile, "/demo", "/" + destEntityName.split("\\.")[0].toLowerCase());
        System.out.println(String.format("成功创建%s", destPath));
        successCount++;
      }
    }
    return successCount;
  }

  // 如果把entity 复制到dto里，需要修改文件名、类名、父类名
  public static int createDtoFilesFromEntityFile() {
    final AtomicInteger successCount = new AtomicInteger(0);
    String entityPath = baseUrl + "\\entity\\";
    File entityDir = new File(entityPath);
    // such as Demo.java
    // 这里添加了过滤条件，预防entity下面如果有目录的情况，如果目录名也带.java，我无话可说
    List<String> entityNames =
        Arrays.asList(entityDir.list()).stream()
            .filter(name -> name.contains(".java"))
            .collect(Collectors.toList());

    String dtoPath = baseUrl + "controller\\dto\\";
    File dtoDir = new File(dtoPath);
    List<String> dtoNamesWithoutSuffix =
        Arrays.asList(dtoDir.list()).stream()
            .map(name -> name.split("\\.")[0])
            .collect(Collectors.toList());
    // 样本文件，复制，重命名，字符串替换
    entityNames.stream()
        .filter(entityName -> !dtoNamesWithoutSuffix.contains(entityName.split("\\.")[0] + "Dto"))
        .forEach(
            entityName -> {
              String entityNameWithoutSuffix = entityName.split("\\.")[0];
              try {
                File newFile =
                    copyFileContent(
                        entityPath + entityName, dtoPath + entityNameWithoutSuffix + "Dto.java");
                replacTextContent(
                    newFile, entityNameWithoutSuffix, entityNameWithoutSuffix + "Dto");
                // 修改基类
                replacTextContent(newFile, "BaseEntity", "BaseDto");
                // 修改包名
                replacTextContent(
                    newFile, "com.mnan2c.fms.entity", "com.mnan2c.fms.controller.dto");
                // 去除@Entity注解
                replacTextContent(newFile, "@Entity", "");
                replacTextContent(newFile, "@Table", "");
              } catch (IOException e) {
                e.printStackTrace();
              }
              System.out.println(
                  String.format("成功创建%s", dtoPath + entityNameWithoutSuffix + "Dto.java"));
              successCount.getAndAdd(1);
            });
    return successCount.get();
  }

  public static File copyFileContent(String srcFile, String destFile) throws IOException {
    InputStream in = new FileInputStream(new File(srcFile));
    OutputStream out = new FileOutputStream(new File(destFile));
    byte[] buf = new byte[1024];
    int len;
    while ((len = in.read(buf)) > 0) {
      out.write(buf, 0, len);
    }
    in.close();
    out.close();
    return new File(destFile);
  }

  // 替换某个文件里的字符串
  public static void replacTextContent(File file, String srcStr, String replaceStr)
      throws IOException {
    FileReader in = new FileReader(file);
    BufferedReader bufIn = new BufferedReader(in);
    // 内存流, 作为临时流
    CharArrayWriter tempStream = new CharArrayWriter();
    String line;
    while ((line = bufIn.readLine()) != null) {
      // 替换每行中, 符合条件的字符串
      line = line.replaceAll(srcStr, replaceStr);
      // 将该行写入内存
      tempStream.write(line);
      // 添加换行符
      tempStream.append(System.getProperty("line.separator"));
    }
    bufIn.close();
    // 将内存中的流 写入 文件
    FileWriter out = new FileWriter(file);
    tempStream.writeTo(out);
    out.close();
  }
}
