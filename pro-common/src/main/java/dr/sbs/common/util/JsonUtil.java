package dr.sbs.common.util;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class JsonUtil {
  // Date格式化字符串
  private static final String DATE_FORMAT = DatePattern.NORM_DATE_PATTERN; // yyyy-MM-dd
  // DateTime格式化字符串
  private static final String DATETIME_FORMAT =
      DatePattern.NORM_DATETIME_PATTERN; // yyyy-MM-dd HH:mm:ss
  // Time格式化字符串
  private static final String TIME_FORMAT = DatePattern.NORM_TIME_PATTERN; // HH:mm:ss

  private static ObjectMapper objectMapper;

  static {
    Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
    builder
        .serializerByType(Long.class, ToStringSerializer.instance)
        .serializerByType(BigInteger.class, ToStringSerializer.instance)
        .modulesToInstall(new JavaTimeModule())
        .serializerByType(
            LocalDateTime.class,
            new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
        .serializerByType(
            LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)))
        .serializerByType(
            LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(TIME_FORMAT)))
        .deserializerByType(
            LocalDateTime.class,
            new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
        .deserializerByType(
            LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_FORMAT)))
        .deserializerByType(
            LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(TIME_FORMAT)));

    objectMapper = builder.build();
  }

  /** Transform Object to Json string */
  public static String objectToJson(Object data) {
    try {
      return objectMapper.writeValueAsString(data);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  /** Transform Json string to Object */
  public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
    try {
      return objectMapper.readValue(jsonData, beanType);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /** Transform Json string to List[Object] */
  public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
    JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, beanType);
    try {
      return objectMapper.readValue(jsonData, javaType);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }
}
