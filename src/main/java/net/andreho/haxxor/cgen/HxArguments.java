package net.andreho.haxxor.cgen;

import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.utils.CommonUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 13.06.2017 at 04:58.
 */
public class HxArguments implements Iterable<Object> {
  private static final Object[] EMPTY_ARRAY = new Object[0];
  private static final int FROZEN_BIT = 0x8000_0000;
  private Object[] array;
  private int length;

  /**
   * @return
   */
  public static HxArguments createArguments() {
    return new HxArguments();
  }

  /**
   * @param capacity
   * @return
   */
  public static HxArguments createArguments(int capacity) {
    return new HxArguments(capacity);
  }

  /**
   * @param arguments
   * @return
   */
  public static HxArguments createArguments(HxArguments arguments) {
    return new HxArguments(arguments.array, arguments.length);
  }

  protected HxArguments() {
    this(0);
  }

  protected HxArguments(int capacity) {
    this.array = capacity == 0 ? EMPTY_ARRAY : new Object[capacity];
  }

  protected HxArguments(Object[] arguments, int length) {
    this.array = arguments.clone();
    this.length = length;
  }

  private void ensureCapacity() {

    final Object[] arguments = this.array;
    final int length = arguments.length;
    final int currentLength = this.length;

    if ((length - currentLength) < currentLength) {
      this.array = Arrays.copyOf(arguments, Math.max(length + 4, length + (length >>> 1)));
    }
  }

  private HxArguments addElement(Object arg) {
    if(isFrozen()) {
      throw new IllegalStateException("This arguments' list was frozen.");
    }
    ensureCapacity();
    array[length++] = arg;
    return this;
  }

  public boolean isFrozen() {
    return (this.length & FROZEN_BIT) != 0;
  }

  public HxArguments freeze() {
    this.length |= FROZEN_BIT;
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
    if(index < 0 || index >= length()) {
      throw new IndexOutOfBoundsException("Length="+length()+", Index="+index);
    }
    return array[index];
  }

  public boolean isEmpty() {
    return length() == 0;
  }

  public int length() {
    return Integer.MAX_VALUE & length;
  }

  public Object[] toArray() {
    final Object[] arguments = this.array;
    final int length = length();
    if (arguments.length == length) {
      return arguments.clone();
    }
    return Arrays.copyOf(this.array, length);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final HxArguments that = (HxArguments) o;
    if(length() != that.length()) {
      return false;
    }
    for(int i = 0, len = length(); i < len; i++) {
      if(!Objects.equals(get(i), that.get(i))) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(length());
    for(int i = 0, len = length(); i < len; i++) {
      result = 31 * result + Objects.hashCode(this.array[i]);
    }
    return result;
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

  @Override
  public Iterator<Object> iterator() {
    return CommonUtils.iterator(this.array, 0, length());
  }
}
