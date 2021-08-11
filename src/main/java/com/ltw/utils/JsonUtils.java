package com.ltw.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class JsonUtils {

  private static ObjectMapper mapper;

  static {
    Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
    builder
        .serializerByType(LocalDateTime.class,
            new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
        .serializerByType(LocalDate.class,
            new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        .serializerByType(LocalTime.class,
            new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")))
        .failOnUnknownProperties(false)
        .deserializerByType(LocalDateTime.class,
            new LocalDateTimeDeserializer((DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))))
        .deserializerByType(LocalDate.class,
            new LocalDateDeserializer((DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
        .deserializerByType(LocalTime.class,
            new LocalTimeDeserializer((DateTimeFormatter.ofPattern("HH:mm:ss"))))
        .featuresToEnable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
        .timeZone(TimeZone.getTimeZone("GMT+8"));
    mapper = builder.build();
  }

  public static <T> List<T> toList(String json, Class<T> clazz) {
    return (List) toCollection(json, List.class, clazz);
  }

  public static <T> List<T> toUpperList(String json, Class<T> clazz) {
    return (List) toUpperCollection(json, List.class, clazz);
  }

  public static <T> LinkedList<T> toLinkedList(String json, Class<T> clazz) {
    return (LinkedList) toCollection(json, LinkedList.class, clazz);
  }

  public static <T> Set<T> toSet(String json, Class<T> clazz) {
    return (Set) toCollection(json, Set.class, clazz);
  }

  public static <K, V> Map<K, V> toMap(String json, Class<K> keyClazz, Class<V> valueClazz) {

    return (Map) toCollection(json, Map.class, keyClazz, valueClazz);
  }
  public static <K, V> Map<K, V> toUpperMap(String json, Class<K> keyClazz, Class<V> valueClazz) {

    return (Map) toCollection(json, Map.class, keyClazz, valueClazz);
  }

  private static Object toUpperCollection(String json, Class<?> parametrized,
      Class... parameterClasses) {
    try {
      if (!JSONObject.isValid(json)) {
        return null;
      }
      ObjectMapper mapperUpper = mapper.copy();
      mapperUpper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
      return mapperUpper.readValue(json,
              mapperUpper.getTypeFactory().constructParametricType(parametrized, parameterClasses));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
  private static Object toCollection(String json, Class<?> parametrized,
      Class... parameterClasses) {
    try {
      if (!JSONObject.isValid(json)) {
        return null;
      }
      return mapper.readValue(json,
          mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static <T> T toJavaBean(String json, Class<T> clazz) {
    try {
      if (!JSONObject.isValid(json)) {
        return null;
      }
      return mapper.readValue(json, clazz);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static <T> T toUpperJavaBean(String json, Class<T> clazz) {
    try {
      if (!JSONObject.isValid(json)) {
        return null;
      }
      ObjectMapper mapperUpper = mapper.copy();
      mapperUpper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
      return mapperUpper.readValue(json, clazz);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String toJson(Object obj) {
    try {
      return mapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }
  public static String toUpperJson(Object obj) {
    try {
      ObjectMapper mapperUpper = mapper.copy();
      mapperUpper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
      return mapperUpper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static JsonNode toJsonNode(String json) {
    try {
      if (!JSONObject.isValid(json)) {
        return null;
      }
      return mapper.readTree(json);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return mapper.valueToTree(json);
  }
}
