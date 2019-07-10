package com.mnan2c.fms.utils.pojo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class PostmanItem {

  private String name;
  private JSONArray event = new JSONArray();
  private JSONObject protocolProfileBehavior;
  private JSONObject request = new JSONObject();
  private List<Object> response;

  public static PostmanItem getInstance(
      String path, String method, JSONObject requestParamAndBody) {
    // 1. name
    String name = path;
    // 2. event
    // 如果是登录或注册，把token添加到环境变量中
    String eventStr =
        Arrays.asList("/api/user/login", "/api/user/register").contains(path)
            ? "[{\"listen\": \"test\",\"script\": {\"id\": \"33cf03e3-6587-4c63-80c0-2b1f73aa1f39\","
                + "\"exec\": [\"var jsonData = JSON.parse(responseBody);\\r\",\"postman.setEnvironmentVariable(\\\"accessToken\\\", "
                + "jsonData.data.token);\"],\"type\": \"text/javascript\"}}]"
            : "[{\"listen\": \"test\",\"script\": {\"id\": \"33cf03e3-6587-4c63-80c0-2b1f73aa1f39\","
                + "\"exec\": [\"\"],\"type\": \"text/javascript\"}}]";
    JSONArray event = (JSONArray) JSONArray.parse(eventStr);
    // 3. protocolProfileBehavior
    JSONObject protocolProfileBehavior = null;
    if (StringUtils.equalsIgnoreCase("GET", method)) {
      protocolProfileBehavior = (JSONObject) JSONObject.parse("{\"disableBodyPruning\": true}");
    }
    // 4. request
    JSONArray requestParam = requestParamAndBody.getJSONArray("requestParam");
    String queryStr = JSON.toJSONString(requestParam);
    JSONObject requestBody = requestParamAndBody.getJSONObject("requestBody");
    String requestBodyStr = JSON.toJSONString(requestBody);
    String convertedStr = "";
    if (StringUtils.isNotBlank(requestBodyStr) && requestBodyStr.length() > 2) {
      convertedStr =
          requestBodyStr
              .replaceAll("\"", "\\\\\"")
              .replaceAll("\\{", "\\{\\\\n\\\\t")
              .replaceAll(",", ",\\\\n\\\\t")
              .replaceAll("\\}", "\\\\n\\}");
    }
    String requestStr =
        "{\"method\": \""
            + method
            + "\",\"header\": [{\"key\": \"Content-Type\",\"value\": \"application/json\",\"type\": "
            + "\"text\"},{\"key\": \"Authorization\",\"type\": \"text\",\"value\": \"{{accessToken}}\"}],"
            + "\"body\": {\"mode\": \"raw\",\"raw\": \""
            + convertedStr
            + "\"},"
            + "\"url\": {\"raw\": \"{{apiRootUrl}}"
            + path
            + "\",\"host\": [\"{{apiRootUrl}}\"],"
            + "\"path\": [\""
            + path
            + "\"],\"query\":"
            + queryStr
            + "}}";
    JSONObject request = (JSONObject) JSONObject.parse(requestStr);
    // 5. response
    List<Object> response = new ArrayList<>();
    return new PostmanItem(name, event, protocolProfileBehavior, request, response);
  }
}
