package org.sandbox.aspects.equals;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 05.07.2017 at 21:00.
 */
public abstract class HashCodeUtils {

  public static int hashCode(boolean value) {
    return value? 1 : 0;
  }

  public static int hashCode(byte value) {
    return value;
  }

  public static int hashCode(short value) {
    return value;
  }

  public static int hashCode(char value) {
    return value;
  }

  public static int hashCode(int value) {
    return value;
  }

  public static int hashCode(float value) {
    return Float.hashCode(value);
  }

  public static int hashCode(long value) {
    return Long.hashCode(value);
  }

  public static int hashCode(double value) {
    return Double.hashCode(value);
  }

  public static int hashCode(Object value) {
    return Objects.hashCode(value);
  }

  private HashCodeUtils() {
  }
}
