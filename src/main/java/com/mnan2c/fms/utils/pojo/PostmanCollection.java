package com.mnan2c.fms.utils.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostmanCollection {
  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

  private HashMap<String, String> info = new HashMap<>();
  private List<PostmanItem> item = new ArrayList<>();

  public static PostmanCollection getInstance(List<PostmanItem> item) {
    HashMap<String, String> info = new HashMap<>();
    info.put("_postman_id", "5226925b-cced-4c03-bb36-c41989f0291c");
    info.put("name", "fms-api-" + formatter.format(LocalDateTime.now()));
    info.put("schema", "https://schema.getpostman.com/json/collection/v2.1.0/collection.json");
    return new PostmanCollection(info, item);
  }
}
