package dr.sbs.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class SbsDateUtil {
  public static int ONE_MINUTE = 60;
  public static int ONE_HOUR = 3600;
  public static int ONE_DAY = 24 * 3600;
  public static int ONE_WEEK = 7 * 24 * 3600;
  public static int ONE_MINUTE_MILLISECONDS = 60 * 1000;
  public static int ONE_HOUR_MILLISECONDS = 3600 * 1000;
  public static int ONE_DAY_MILLISECONDS = 24 * 3600 * 1000;
  public static int ONE_WEEK_MILLISECONDS = 7 * 24 * 3600 * 1000;

  private static ZoneOffset zoneOffset = ZoneOffset.ofHours(8);

  public static LocalDate toLocalDate(Date date) {
    return date.toInstant().atZone(zoneOffset).toLocalDate();
  }

  public static LocalDateTime toLocalDateTime(Date date) {
    return date.toInstant().atZone(zoneOffset).toLocalDateTime();
  }

  public static Date toDate(LocalDate localDate) {
    return Date.from(localDate.atStartOfDay(zoneOffset).toInstant());
  }

  public static Date toDate(LocalDateTime localDateTime) {
    return Date.from(localDateTime.atZone(zoneOffset).toInstant());
  }

  // 2020,1,1 => 2020-01-01
  public static String getDateStrByYMD(int year, int month, int day) {
    return year + "-" + SbsStringUtil.fillZeroBy2(month) + "-" + SbsStringUtil.fillZeroBy2(day);
  }

  // 2020,1,1 => 20200101
  public static int getDateNum(int year, int month, int day) {
    return year * 10000 + month * 100 + day;
  }

  // 2020,1 => 2020-01
  public static String getYMStr(int year, int month) {
    return year + "-" + SbsStringUtil.fillZeroBy2(month);
  }

  // 2020,1 => 202001
  public static int getYMNum(int year, int month) {
    return year * 100 + month;
  }
}
