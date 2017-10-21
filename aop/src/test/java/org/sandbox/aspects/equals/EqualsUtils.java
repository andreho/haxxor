package org.sandbox.aspects.equals;

import java.util.Objects;

import static java.lang.Double.doubleToLongBits;
import static java.lang.Float.floatToIntBits;

/**
 * <br/>Created by a.hofmann on 05.07.2017 at 21:00.
 */
public abstract class EqualsUtils {

  public static boolean equal(boolean a, boolean b) {
    return a == b;
  }

  public static boolean equal(byte a, byte b) {
    return a == b;
  }

  public static boolean equal(short a, short b) {
    return a == b;
  }

  public static boolean equal(char a, char b) {
    return a == b;
  }

  public static boolean equal(int a, int b) {
    return a == b;
  }

  public static boolean equal(float a, float b) {
    return floatToIntBits(a) == floatToIntBits(b);
  }

  public static boolean equal(long a, long b) {
    return a == b;
  }

  public static boolean equal(double a, double b) {
    return doubleToLongBits(a) == doubleToLongBits(b);
  }

  public static boolean equal(Object a, Object b) {
    return Objects.equals(a, b);
  }

  private EqualsUtils() {
  }
}
