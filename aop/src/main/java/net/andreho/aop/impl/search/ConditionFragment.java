package net.andreho.aop.impl.search;

import net.andreho.aop.spec.query.Op;
import net.andreho.haxxor.spec.api.HxAnnotation;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * <br/>Created by a.hofmann on 15.06.2017 at 04:35.
 */
public class ConditionFragment
    implements Predicate<HxAnnotation> {

  private final String name;
  private final String value;
  private final String[] values;
  private final Pattern pattern;
  private final Op operator;
  private final boolean negate;
  private final boolean undefinedValue;

  public ConditionFragment(final String name,
                           final String[] values,
                           final Op operator,
                           final boolean negate,
                           final boolean undefinedValue) {
    this.name = name;
    this.values = values;
    this.operator = operator;
    this.negate = negate;
    this.undefinedValue = undefinedValue;
    this.value = Arrays.toString(values);
    if(operator == Op.LIKE) {
      pattern = Pattern.compile(values[0]);
    } else {
      pattern = null;
    }
  }

  @Override
  public boolean test(final HxAnnotation annotation) {
    if(values.length == 0 ||
       (!annotation.hasAttribute(name) &&
       !annotation.hasDefaultAttribute(name))) {

       return undefinedValue;
    }

    switch (operator) {
      case EQ: return checkEquality(toString((Object) annotation.attribute(name)));
      case GE: return checkGreaterEqual(annotation.attribute(name));
      case GT: return checkGreaterThan(annotation.attribute(name));
      case LE: return checkLessEqual(annotation.attribute(name));
      case LT: return checkLessThan(annotation.attribute(name));
      case LIKE: return checkLike(toString((Object) annotation.attribute(name)));
      case CONTAINS: return checkContains(toString((Object) annotation.attribute(name)));
      case IS_DEFAULT: return !annotation.hasAttribute(name);
    }

    return false;
  }

  private boolean checkLike(final String[] strings) {
    for(String str : strings) {
      if(pattern.matcher(str).matches()) {
        return true;
      }
    }
    return false;
  }

  private boolean checkGreaterEqual(final Object any) {
    String value = values[0];
    if(any instanceof Number) {
      return Double.parseDouble(value) >= ((Number) any).doubleValue();
    }
    return value.compareTo(any.toString()) >= 0;
  }

  private boolean checkGreaterThan(final Object any) {
    String value = values[0];
    if(any instanceof Number) {
      return Double.parseDouble(value) > ((Number) any).doubleValue();
    }
    return value.compareTo(any.toString()) > 0;
  }

  private boolean checkLessThan(final Object any) {
    String value = values[0];
    if(any instanceof Number) {
      return Double.parseDouble(value) < ((Number) any).doubleValue();
    }
    return value.compareTo(any.toString()) < 0;
  }

  private boolean checkLessEqual(final Object any) {
    String value = values[0];
    if(any instanceof Number) {
      return Double.parseDouble(value) <= ((Number) any).doubleValue();
    }
    return value.compareTo(any.toString()) <= 0;
  }

  private boolean checkEquality(final String[] strings) {
    return Arrays.equals(values, strings);
  }

  private boolean checkContains(final String[] strings) {
    final String[] values = this.values;
    int count = 0;

    for(String str : strings) {
      for(String val : values) {
        if(str.equals(val)) {
          count++;
        }
      }
    }
    return count >= values.length;
  }

  private String[] toString(Object o) {
    if(o == null) {
      return null;
    }

    if(o.getClass().isArray()) {
      if(o instanceof boolean[]) {
        return toString((boolean[]) o);
      }
      if(o instanceof byte[]) {
        return toString((byte[]) o);
      }
      if(o instanceof short[]) {
        return toString((short[]) o);
      }
      if(o instanceof char[]) {
        return toString((char[]) o);
      }
      if(o instanceof int[]) {
        return toString((int[]) o);
      }
      if(o instanceof float[]) {
        return toString((float[]) o);
      }
      if(o instanceof long[]) {
        return toString((long[]) o);
      }
      if(o instanceof double[]) {
        return toString((double[]) o);
      }
      if(o instanceof String[]) {
        return toString((String[]) o);
      }
      return toString((Object[]) o);
    }

    return new String[]{ o.toString() };
  }

  private static String[] toString(boolean[] array) {
    String[] strings = new String[array.length];
    for (int i = 0; i < array.length; i++) {
      strings[i] = String.valueOf(array[i]);
    }
    return strings;
  }

  private static String[] toString(byte[] array) {
    String[] strings = new String[array.length];
    for (int i = 0; i < array.length; i++) {
      strings[i] = String.valueOf(array[i]);
    }
    return strings;
  }

  private static String[] toString(short[] array) {
    String[] strings = new String[array.length];
    for (int i = 0; i < array.length; i++) {
      strings[i] = String.valueOf(array[i]);
    }
    return strings;
  }

  private static String[] toString(char[] array) {
    String[] strings = new String[array.length];
    for (int i = 0; i < array.length; i++) {
      strings[i] = String.valueOf(array[i]);
    }
    return strings;
  }

  private static String[] toString(int[] array) {
    String[] strings = new String[array.length];
    for (int i = 0; i < array.length; i++) {
      strings[i] = String.valueOf(array[i]);
    }
    return strings;
  }

  private static String[] toString(float[] array) {
    String[] strings = new String[array.length];
    for (int i = 0; i < array.length; i++) {
      strings[i] = String.valueOf(array[i]);
    }
    return strings;
  }

  private String[] toString(long[] array) {
    String[] strings = new String[array.length];
    for (int i = 0; i < array.length; i++) {
      strings[i] = String.valueOf(array[i]);
    }
    return strings;
  }

  private static String[] toString(double[] array) {
    String[] strings = new String[array.length];
    for (int i = 0; i < array.length; i++) {
      strings[i] = String.valueOf(array[i]);
    }
    return strings;
  }

  private static String[] toString(Object[] array) {
    String[] strings = new String[array.length];
    for (int i = 0; i < array.length; i++) {
      strings[i] = String.valueOf(array[i]);
    }
    return strings;
  }
}
