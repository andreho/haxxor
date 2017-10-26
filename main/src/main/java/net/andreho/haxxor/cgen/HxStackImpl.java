package net.andreho.haxxor.cgen;

import net.andreho.haxxor.Utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * <br/>Created by a.hofmann on 12.03.2016.<br/>
 */
public class HxStackImpl
    implements HxStack, Iterable<Object> {

  private Object[] stack;
  private int size;

  public HxStackImpl() {
    this(4);
  }

  public HxStackImpl(final int capacity) {
    if (capacity < 0) {
      throw new IllegalArgumentException("Invalid capacity: " + capacity);
    }
    this.stack = new Object[capacity];
    this.size = 0;
  }

  public HxStackImpl(final Object[] stack, final int size) {
    this.stack = stack;
    this.size = size;
  }

  /**
   * @param type to push onto this stack reserving one operand slot only
   */
  @Override
  public HxStack push(Object type) {
    ensureCapacity(1)[this.size++] = type;
    return this;
  }

  /**
   * @param a is the first value to push onto this stack and reserve one operand slot only
   * @param b is the second value to push onto this stack and reserve one operand slot only
   */
  @Override
  public HxStack push(Object a, Object b) {
    final Object[] stack = ensureCapacity(2);
    int size = this.size;
    stack[size++] = a;
    stack[size++] = b;
    this.size = size;
    return this;
  }

  /**
   * @param a is the first value to push onto this stack and reserve one operand slot only
   * @param b is the second value to push onto this stack and reserve one operand slot only
   * @param c is the third value to push onto this stack and reserve one operand slot only
   */
  @Override
  public HxStack push(Object a, Object b, Object c) {
    final Object[] stack = ensureCapacity(3);
    int size = this.size;
    stack[size++] = a;
    stack[size++] = b;
    stack[size++] = c;
    this.size = size;
    return this;
  }

  /**
   * @param a is the first value to push onto this stack and reserve one operand slot only
   * @param b is the second value to push onto this stack and reserve one operand slot only
   * @param c is the third value to push onto this stack and reserve one operand slot only
   * @param c is the fourth value to push onto this stack and reserve one operand slot only
   */
  @Override
  public HxStack push(Object a, Object b, Object c, Object d) {
    final Object[] stack = ensureCapacity(4);
    int size = this.size;
    stack[size++] = a;
    stack[size++] = b;
    stack[size++] = c;
    stack[size++] = d;
    this.size = size;
    return this;
  }

  /**
   * @param pushList to push onto this stack
   */
  @Override
  public HxStack push(List<Object> pushList) {
    ensureCapacity(pushList.size());
    for(Object o : pushList) {
      push(o);
    }
    return this;
  }

  private Object[] ensureCapacity(int count) {
    Object[] stack = this.stack;
    final int capacity = stack.length;
    if (this.size + count >= capacity) {
      this.stack = stack =  Arrays.copyOf(stack, Math.max(4, capacity + (capacity >>> 1)));
    }
    return stack;
  }

  /**
   * @return removes one operand value from this stack
   */
  @Override
  public Object pop() {
    return this.stack[--this.size];
  }

  @Override
  public Object popTwice() {
    return this.stack[this.size -= 2];
  }

  @Override
  public HxStack pop(final int count) {
    int size = this.size -= count;
    if(size < 0) {
      throw new IllegalStateException("Stack-Underflow.");
    }
    return this;
  }

  /**
   * @return peeks one operand value from this stack but doesn't remove it
   */
  @Override
  public Object peek() {
    return peek(0);
  }

  /**
   * @param depth where to peek a stack value; <code>peek(0)</code> is the same as <code>peek()</code>
   * @return peeks one operand value from this stack at the given depth but doesn't remove it
   */
  @Override
  public Object peek(int depth) {
    return this.stack[this.size - (depth + 1)];
  }

  /**
   * @return size of this stack
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Clears this stack
   */
  @Override
  public void clear() {
    this.size = 0;
    Arrays.fill(this.stack, null);
  }

  @Override
  public HxStack copy() {
    return new HxStackImpl(this.stack.clone(), this.size);
  }

  @Override
  public Iterator<Object> iterator() {
    return Utils.iterator(this.stack, 0, this.size);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("[");

    if(size() > 0) {
      Object[] stack = this.stack;
      builder.append(stack[0]);
      for (int i = 1, size = size(); i < size; i++) {
        builder.append(',').append(stack[i]);
      }
    }

    return builder.append("]").toString();
  }

  //----------------------------------------------------------------------------------------------------------------
}
