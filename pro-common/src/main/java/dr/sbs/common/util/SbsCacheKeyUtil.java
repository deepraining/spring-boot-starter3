package dr.sbs.common.util;

/** 缓存键工具 */
public class SbsCacheKeyUtil {
  // 运行环境（灰度环境与产品环境的缓存要分开）
  public static String ENV = "";
  // 主版本号（针对所有的键，次版本号可以在每个键上另外加）
  public static final String VERSION = "v1";
  // 通用
  public static final String COMMON_DB = "sbsCommon";
  // 管理项目用
  public static final String ADMIN_DB = "sbsAdmin";
  // 前端项目用
  public static final String FRONT_DB = "sbsFront";

  public static final Integer ONE_MINUTE = 60;
  public static final Integer ONE_HOUR = 3600;
  public static final Integer ONE_DAY = 24 * 3600;
  public static final Integer ONE_WEEK = 7 * 24 * 3600;

  public static String getCommonDbKey(Object... keys) {
    StringBuilder res = new StringBuilder(COMMON_DB);
    res.append(":").append(ENV).append(":").append(VERSION);
    for (Object key : keys) {
      res.append(":").append(key);
    }
    return res.toString();
  }

  public static String getAdminDbKey(Object... keys) {
    StringBuilder res = new StringBuilder(ADMIN_DB);
    res.append(":").append(ENV).append(":").append(VERSION);
    for (Object key : keys) {
      res.append(":").append(key);
    }
    return res.toString();
  }

  public static String getFrontDbKey(Object... keys) {
    StringBuilder res = new StringBuilder(FRONT_DB);
    res.append(":").append(ENV).append(":").append(VERSION);
    for (Object key : keys) {
      res.append(":").append(key);
    }
    return res.toString();
  }
}
