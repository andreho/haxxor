package net.andreho.haxxor.cgen;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * <br/>Created by a.hofmann on 12.03.2016.<br/>
 */
public class HxStack
    implements Iterable<Object> {

  private Object[] stack;
  private int length;
  private int max;

  public HxStack() {
    this(4);
  }

  public HxStack(final int capacity) {
    if (capacity < 0) {
      throw new IllegalArgumentException("Invalid capacity: " + capacity);
    }
    this.stack = new Object[capacity];
    this.length = this.max = 0;
  }

  /**
   * @param type to push onto this stack reserving one operand slot only
   */
  public HxStack push(Object type) {
    ensureCapacity();
    this.stack[this.length++] = type;
    this.max = Math.max(this.max, this.length);
    return this;
  }

  /**
   * @param a is the first value to push onto this stack and reserve one operand slot only
   * @param b is the second value to push onto this stack and reserve one operand slot only
   */
  public HxStack push(Object a, Object b) {
    return push(a).push(b);
  }

  /**
   * @param a is the first value to push onto this stack and reserve one operand slot only
   * @param b is the second value to push onto this stack and reserve one operand slot only
   * @param c is the third value to push onto this stack and reserve one operand slot only
   */
  public HxStack push(Object a, Object b, Object c) {
    return push(a).push(b).push(c);
  }

  /**
   * @param a is the first value to push onto this stack and reserve one operand slot only
   * @param b is the second value to push onto this stack and reserve one operand slot only
   * @param c is the third value to push onto this stack and reserve one operand slot only
   * @param c is the fourth value to push onto this stack and reserve one operand slot only
   */
  public HxStack push(Object a, Object b, Object c, Object d) {
    return push(a).push(b).push(c).push(d);
  }

  /**
   * @param pushList to push onto this stack
   */
  public HxStack push(List<Object> pushList) {
    for(Object o : pushList) {
      push(o);
    }
    return this;
  }

  private void ensureCapacity() {
    final int length = this.stack.length;
    if (length >= this.length) {
      this.stack = Arrays.copyOf(this.stack, Math.max(4, length + (length >>> 1)));
    }
  }

  /**
   * @return removes one operand value from this stack
   */
  public Object pop() {
    return this.stack[--this.length];
  }

  /**
   * @param count of the elements to pop from this stack
   */
  public void pop(int count) {
    if(count == 0) {
      return;
    } else if(count > this.length) {
      throw new IllegalStateException("Stack underflow, with size: "+this.length + " and pop-count: " + count);
    }
    this.length -= count;
  }

  /**
   * @return peeks one operand value from this stack but doesn't remove it
   */
  public Object peek() {
    return peek(0);
  }

  /**
   * @param depth where to peek a stack value; <code>peek(0)</code> is the same as <code>peek()</code>
   * @return peeks one operand value from this stack at the given depth but doesn't remove it
   */
  public Object peek(int depth) {
    return this.stack[this.length - (depth + 1)];
  }

  /**
   * @return length of this stack
   */
  public int length() {
    return length;
  }

  /**
   * @return maximal reached stack length
   */
  public int max() {
    return max;
  }

  /**
   * Clears this stack without to reset the max length attribute
   */
  public void clear() {
    this.length = 0;
  }

  /**
   * Clears this stack and resets the max length attribute
   */
  public void reset() {
    this.length = this.max = 0;
  }

  @Override
  public Iterator<Object> iterator() {
    return null; //ArrayUtils.iterator(this.stack, 0, this.length);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("[");

    if(length() > 0) {
      Object[] stack = this.stack;
      builder.append(stack[0]);
      for (int i = 1, len = length(); i < len; i++) {
        builder.append(',').append(stack[i]);
      }
    }

    return builder.append("]").toString();
  }

  //----------------------------------------------------------------------------------------------------------------
}
