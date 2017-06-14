package net.andreho.haxxor.cgen;

import net.andreho.haxxor.spec.api.HxType;

import java.util.Arrays;

/**
 * <br/>Created by a.hofmann on 13.06.2017 at 04:58.
 */
public class HxArguments {

  private static final Object[] EMPTY_ARRAY = new Object[0];

  private Object[] arguments;
  private int length;

  protected HxArguments() {
    this(0);
  }

  protected HxArguments(int capacity) {
    this.arguments = capacity == 0? EMPTY_ARRAY : new Object[capacity];
  }

  private void ensureCapacity(int capacity) {
    final Object[] arguments = this.arguments;
    final int length = arguments.length;
    if ((length - this.length) < capacity) {
      this.arguments = Arrays.copyOf(arguments, Math.max(length + 4, length + (length >>> 1)));
    }
  }

  private HxArguments addElement(Object arg) {
    ensureCapacity(1);
    arguments[length++] = arg;
    return this;
  }

  public HxArguments add(int val) {
    return addElement(val);
  }

  public HxArguments add(float val) {
    return addElement(val);
  }

  public HxArguments add(long val) {
    return addElement(val);
  }

  public HxArguments add(double val) {
    return addElement(val);
  }

  public HxArguments add(Integer val) {
    return addElement(val);
  }

  public HxArguments add(Float val) {
    return addElement(val);
  }

  public HxArguments add(Long val) {
    return addElement(val);
  }

  public HxArguments add(Double val) {
    return addElement(val);
  }

  public HxArguments add(String str) {
    return addElement(str);
  }

  public HxArguments add(HxType type) {
    return addElement(type);
  }

  public HxArguments add(HxMethodHandle handle) {
    return addElement(handle);
  }

  public HxArguments add(HxMethodType methodType) {
    return addElement(methodType);
  }

  public Object get(int index) {
    return arguments[index];
  }

  public int length() {
    return length;
  }

  public Object[] toArray() {
    final Object[] arguments = this.arguments;
    if (arguments.length == length) {
      return arguments;
    }
    final Object[] array = new Object[length];
    for (int i = 0; i < array.length; i++) {
      array[i] = arguments[i];
    }
    return array;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("[");
    if (length() > 0) {
      builder.append(get(0));
      for (int i = 1; i < length(); i++) {
        builder.append(',')
               .append(get(i));
      }
    }
    return builder.append(']')
                  .toString();
  }

  /**
   * @return
   */
  public static HxArguments create() {
    return new HxArguments();
  }

  /**
   * @param capacity
   * @return
   */
  public static HxArguments create(int capacity) {
    return new HxArguments(capacity);
  }
}
