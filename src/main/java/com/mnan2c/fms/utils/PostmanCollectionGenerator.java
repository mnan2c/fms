package com.mnan2c.fms.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mnan2c.fms.utils.pojo.PostmanCollection;
import com.mnan2c.fms.utils.pojo.PostmanItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 根据controller里的接口，生成postman的接口测试文件，方便自动化测试
 *
 * <pre>
 *  1. PostmanCollection构造了postman文件的结构；
 *  2. PostmanItem构造了postman每个接口的结构；
 *  3. 通过反射获取请求地址，请求方法Method，请求参数，请求体；
 *  4. 根据获取的信息构造Json字符串，并写入文件中；
 *  5. 可以支持json格式化，但是因为一些特殊的参数格式，导致格式化的时候会有问题，
 *      所以暂时不支持，待bug修复。
 * </pre>
 */
@Slf4j
public class PostmanCollectionGenerator {
  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
  private static DateTimeFormatter parameterFormatter =
      DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
  private static String FILE_PATH = "D:/";
  private static String COLLECTION_NAME = "fms-api-";
  private static final String baseUrl =
      "G:\\workspace-for-prod\\fms\\src\\main\\java\\com\\mnan2c\\fms\\";
  private static String controllerPackage = "com.mnan2c.fms.controller.";
  private static String dtoPackage = "com.mnan2c.fms.controller.dto.";
  private static List<String> ignoredDtoFields = Arrays.asList("serialVersionUID");

  public static void main(String[] args) {
    PostmanCollection postman = PostmanCollection.getInstance(getAllApis());
    JSONObject jsonObject = (JSONObject) JSON.toJSON(postman);
    String postmanStr = jsonObject.toJSONString();
    String fileName = COLLECTION_NAME + formatter.format(LocalDateTime.now());
    boolean isSuccess = createJsonFile(postmanStr, FILE_PATH, fileName);
    if (isSuccess) {
      log.debug("文件生成完成，路径：[{}{}]", FILE_PATH, fileName);
    } else {
      log.debug("文件生成失败！");
    }
  }

  // 通过反射 获取controller注解上的path
  public static List<PostmanItem> getAllApis() {
    String controllerPath = baseUrl + "controller\\";
    File controllerDir = new File(controllerPath);
    String[] controllers = controllerDir.list();
    // 这里添加了过滤条件，预防controller包下面有目录的情况
    List<String> controllerNames =
        Arrays.asList(controllers).stream()
            .filter(name -> name.contains(".java"))
            .map(name -> name.split("\\.")[0])
            .collect(Collectors.toList());
    List<PostmanItem> postmanItems = new ArrayList<>();
    for (String controllerName : controllerNames) {
      Class clazz;
      try {
        clazz = Class.forName(controllerPackage + controllerName);
      } catch (ClassNotFoundException e) {
        return null;
      }
      // 获取controller requestMapping注解上的path
      RequestMapping requestMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
      if (!ArrayUtils.isEmpty(requestMapping.value())) {
        String path = requestMapping.value()[0];
        // 1. 添加本类里的接口path
        Class dtoClazz = getDtoClassByControllerClass(clazz);
        addApis(postmanItems, path, clazz, dtoClazz);
        Class superClass = clazz.getSuperclass();
        if (superClass == null) continue;
        // 2. 添加父类里的接口path
        addApis(postmanItems, path, superClass, dtoClazz);
      }
    }
    return postmanItems;
  }

  // 把类里的每个接口构建成一个PostmanItem，然后添加到接口列表中
  private static void addApis(
      List<PostmanItem> postmanItems,
      String controllerPath,
      Class controllerClazz,
      Class dtoClazz) {
    // 获取每个方法的path，method：post，get
    Method[] methods = controllerClazz.getDeclaredMethods();
    // 获取具体的Dto的名称
    for (Method method : methods) {
      RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
      PostMapping methodPostMapping = method.getAnnotation(PostMapping.class);
      PutMapping methodPutMapping = method.getAnnotation(PutMapping.class);
      GetMapping methodGetMapping = method.getAnnotation(GetMapping.class);
      DeleteMapping methodDeleteMapping = method.getAnnotation(DeleteMapping.class);
      String requestPath = null;
      String requestMethod = null;
      if (methodRequestMapping != null) {
        requestPath =
            !ArrayUtils.isEmpty(methodRequestMapping.value())
                ? methodRequestMapping.value()[0]
                : "";
        requestMethod = methodRequestMapping.method()[0].name();
      } else if (methodPostMapping != null) {
        requestPath = methodPostMapping.value().length > 0 ? methodPostMapping.value()[0] : "";
        requestMethod = HttpMethod.POST.name();
      } else if (methodPutMapping != null) {
        requestPath = methodPutMapping.value().length > 0 ? methodPutMapping.value()[0] : "";
        requestMethod = HttpMethod.PUT.name();
      } else if (methodGetMapping != null) {
        requestPath = methodGetMapping.value().length > 0 ? methodGetMapping.value()[0] : "";
        requestMethod = HttpMethod.GET.name();
      } else if (methodDeleteMapping != null) {
        requestPath =
            !ArrayUtils.isEmpty(methodDeleteMapping.value()) ? methodDeleteMapping.value()[0] : "";
        requestMethod = HttpMethod.DELETE.name();
      }
      JSONObject requestParamAndBody =
          getRequestParamAndRequestBody(method, requestMethod, dtoClazz);
      if (StringUtils.isNotBlank(requestMethod)) {
        String subPath =
            StringUtils.isNotBlank(requestPath)
                ? requestPath.replaceAll("\\{", "<").replaceAll("\\}", ">")
                : "";
        postmanItems.add(
            PostmanItem.getInstance(controllerPath + subPath, requestMethod, requestParamAndBody));
      }
    }
  }

  public static JSONObject getRequestParamAndRequestBody(
      Method method, String requestMethod, Class dtoClass) {
    JSONArray requestParams = new JSONArray();
    JSONObject requestBody = new JSONObject();
    if (!ArrayUtils.isEmpty(method.getParameters())) {
      for (Parameter parameter : method.getParameters()) {
        if (Arrays.asList("POST", "PUT").contains(requestMethod)) {
          // 如果请求方法是POST或PUT，且参数体带有注解@RequestBody，则构建请求体
          if (parameter.isAnnotationPresent(RequestBody.class) && dtoClass != null) {
            Field[] dtoFields = dtoClass.getDeclaredFields();
            for (Field dField : dtoFields) {
              if (!ignoredDtoFields.contains(dField.getName())) {
                requestBody.put(
                    dField.getName(),
                    getDefaultValueByFieldType(dField.getName(), dField.getType()));
              }
            }
            Field[] superDtoFields =
                dtoClass.getSuperclass() != null
                    ? dtoClass.getSuperclass().getDeclaredFields()
                    : null;
            if (superDtoFields != null) {
              for (Field field : superDtoFields) {
                if (!ignoredDtoFields.contains(field.getName())) {
                  requestBody.put(
                      field.getName(),
                      getDefaultValueByFieldType(field.getName(), field.getType()));
                }
              }
            }
            // 如果请求方法是POST或PUT，且参数体不带注解@RequestBody，则构建请求参数
          } else if (parameter.isAnnotationPresent(RequestParam.class) && dtoClass != null) {
            JSONObject paramObject = new JSONObject();
            paramObject.put("key", parameter.getName());
            paramObject.put(
                "value", getDefaultValueByFieldType(parameter.getName(), parameter.getType()));
            requestParams.add(paramObject);
          }
          // 如果请求方法是GET或DELETE，则构建请求参数
        } else if (parameter.isAnnotationPresent(RequestParam.class)) {
          JSONObject paramObject = new JSONObject();
          paramObject.put("key", parameter.getName());
          paramObject.put(
              "value", getDefaultValueByFieldType(parameter.getName(), parameter.getType()));
          requestParams.add(paramObject);
        }
      }
    }
    JSONObject result = new JSONObject();
    result.put("requestBody", requestBody);
    result.put("requestParam", requestParams);
    return result;
  }

  private static Class getDtoClassByControllerClass(Class controllerClazz) {
    String[] packNames = controllerClazz.getName().split("\\.");
    String dtoName;
    try {
      dtoName =
          String.format(
              "%sDto",
              packNames[packNames.length - 1].substring(
                  0, packNames[packNames.length - 1].length() - 10));
      return Class.forName(dtoPackage + dtoName);
    } catch (Exception e) {
      return null;
    }
  }

  private static Object getDefaultValueByFieldType(String fieldName, Class type) {
    if ("ids".equals(fieldName)) {
      return Arrays.asList("1", "2");
    }
    if (type == Integer.class || type == int.class) {
      return 1;
    } else if (Long.class == type || type == long.class) {
      return 1L;
    } else if (String.class == type) {
      return "string";
    } else if (List.class == type) {
      return new ArrayList<>();
    } else if (Boolean.class == type || boolean.class == type) {
      return true;
    } else if (Date.class == type || LocalDateTime.class == type || ZonedDateTime.class == type) {
      return parameterFormatter.format(LocalDateTime.now());
    }
    return null;
  }

  public static boolean createJsonFile(String jsonString, String filePath, String fileName) {
    boolean isSuccess = true;
    String fullPath = filePath + fileName + ".json";
    try {
      File file = new File(fullPath);
      if (!file.getParentFile().exists()) {
        file.getParentFile().mkdirs();
      }
      if (file.exists()) {
        file.delete();
      }
      file.createNewFile();
      //      jsonString = HVUtil.formatJson(jsonString);
      Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
      write.write(jsonString);
      write.flush();
      write.close();
    } catch (Exception e) {
      log.warn(e.getMessage());
      isSuccess = false;
      e.printStackTrace();
    }
    return isSuccess;
  }
}
