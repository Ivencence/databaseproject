package util;

public class Validator {

  public static boolean isEmpty(String s) {
    return s == null || s.trim().isEmpty();
  }

  public static boolean isValidDouble(String s) {
    if (isEmpty(s)) return false;
    try { Double.parseDouble(s); return true; }
    catch (NumberFormatException e) { return false; }
  }

  public static boolean isValidInt(String s) {
    if (isEmpty(s)) return false;
    try { Integer.parseInt(s); return true; }
    catch (NumberFormatException e) { return false; }
  }
}