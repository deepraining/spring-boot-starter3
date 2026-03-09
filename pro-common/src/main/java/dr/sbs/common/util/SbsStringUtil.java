package dr.sbs.common.util;

public class SbsStringUtil {
  // 填充数字0
  public static String fillZeroBy2(int num) {
    return (num < 10 ? "0" : "") + num;
  }

  public static String fillZeroBy2(String num) {
    if (num == null || num.equals("")) return "00";
    return (num.length() == 1 ? "0" : "") + num;
  }
}
