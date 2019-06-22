package com.mnan2c.fms.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.stream.Collectors;

public class FmsUtils {

  // transfer camelCase to under score
  // 例如：formatHashMapKeyToUnderScore 转为：format_hash_map_key_to_under_score
  public static String camelCaseToUnderScore(String original) {
    if (StringUtils.isBlank(original)) return null;
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < original.length(); i++) {
      Character character = original.charAt(i);
      if (Character.isUpperCase(character)) sb.append("_").append(Character.toLowerCase(character));
      else sb.append(character);
    }
    return sb.toString();
  }

  // format hashMap key to under score
  // 把HashMap的key转为下划线命名法。例如formatHashMapKeyToUnderScore 转为：format_hash_map_key_to_under_score
  public static Map<String, Object> formatHashMapKeyToUnderScore(Map<String, Object> map) {
    return map.entrySet().stream()
        .collect(
            Collectors.toMap(
                entry -> camelCaseToUnderScore(entry.getKey()), //
                entry -> entry.getValue()) //
            );
  }
}
