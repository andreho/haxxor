package net.andreho.haxxor.cgen;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.Utils;
import net.andreho.haxxor.cgen.impl.HxLocalVariableImpl;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 14.03.2016.<br/>
 */
public class HxLocals
    implements Iterable<Object> {

  private int length;
  private int maxLength;
  private Object[] slots;

  public HxLocals() {
    this(0);
  }

  public HxLocals(final int capacity) {
    if (capacity < 0) {
      throw new IllegalArgumentException("Invalid capacity: " + capacity);
    }
    this.slots = new HxLocalVariableImpl[capacity];
    this.length = this.maxLength = 0;
  }

  private void ensureCapacity(int index) {
    int length = this.slots.length;
    if (length >= index) {
      int capacity = Math.max(length + 4, length * 2);
      while (capacity < index) {
        capacity = capacity * 2;
      }
      this.slots = Arrays.copyOf(this.slots, capacity);
    }
  }

  private int extraSlotCapacity(Object type) {
    return (Opcodes.LONG == type || Opcodes.DOUBLE == type) ? 1 : 0;
  }

  /**
   * @param index
   * @return
   */
  public Object get(int index) {
    if (this.length > index) {
      return this.slots[index];
    }
    throw new IllegalArgumentException("Operand slot not available: " + index);
  }

  /**
   * @param index
   * @param type
   */
  public void set(int index, Object type) {
    final int extra = extraSlotCapacity(type);
    ensureCapacity(index + extra);
    this.slots[index] = type;
    if (extra > 0) {
      this.slots[index + extra] = Opcodes.TOP;
    }
    this.length = index + extra;
    this.maxLength = Math.max(this.length, index + extra);
  }

  /**
   * @param index to check
   * @return
   */
  public boolean has(int index) {
    return this.length > index && this.slots[index] != null;
  }

  /**
   * @param index to check
   * @param type  is expected type at the given index
   * @return
   */
  public boolean has(int index, Object type) {
    return has(index) && Objects.equals(get(index), type);
  }

  /**
   * @param type
   */
  public HxLocals chop(Object type) {
    this.length -= (1 + extraSlotCapacity(type));
    return this;
  }

  /**
   * @return
   */
  public int length() {
    return this.length;
  }

  /**
   * @return
   */
  public int maxLength() {
    return this.maxLength;
  }

  /**
   * Clears this slot list without to reset the max length attribute
   */
  public void clear() {
    this.length = 0;
  }

  /**
   * Clears this slot list and resets the max length attribute
   */
  public void reset() {
    this.length = this.maxLength = 0;
  }

  @Override
  public Iterator<Object> iterator() {
    return Utils.iterator(this.slots, 0, this.length);
  }
}
