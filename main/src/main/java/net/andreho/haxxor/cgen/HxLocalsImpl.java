package net.andreho.haxxor.cgen;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.Utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 14.03.2016.<br/>
 */
public class HxLocalsImpl
    implements HxLocals, Iterable<Object> {

  private int size;
  private Object[] table;

  public HxLocalsImpl() {
    this(2);
  }

  public HxLocalsImpl(final int capacity) {
    if (capacity < 0) {
      throw new IllegalArgumentException("Invalid capacity: " + capacity);
    }
    this.table = new Object[capacity];
    this.size = 0;
  }


  public HxLocalsImpl(final Object[] table, final int size) {
    this.table = table;
    this.size = size;
  }

  private Object[] ensureCapacity(int index) {
    Object[] slots = this.table;
    int length = slots.length;
    if (length < index) {
      int capacity = Math.max(length + 4, length * 2);
      while (capacity < index) {
        capacity = capacity * 2;
      }
      this.table = slots = Arrays.copyOf(slots, capacity);
      this.size = Math.max(this.size, index);
    }
    return slots;
  }

  private int extraSlotCapacity(Object type) {
    return (Opcodes.LONG == type || Opcodes.DOUBLE == type) ? 1 : 0;
  }

  /**
   * @param index
   * @return
   */
  @Override
  public Object get(int index) {
    return this.table[index];
  }

  /**
   * @param index
   * @param type
   * @return this
   */
  @Override
  public HxLocals set(int index, Object type) {
    ensureCapacity(index)[index] = type;
    return this;
  }

  @Override
  public HxLocals set(final int index,
                      final Object type,
                      final Object top) {
    final Object[] slots = ensureCapacity(index + 1);
    slots[index] = type;
    slots[index + 1] = top;
    return this;
  }

  /**
   * @param index to check
   * @return
   */
  @Override
  public boolean has(int index) {
    return this.size > index && this.table[index] != null;
  }

  /**
   * @param index to check
   * @param type  is expected type at the given index
   * @return
   */
  @Override
  public boolean has(int index,
                     Object type) {
    return has(index) && Objects.equals(get(index), type);
  }

//  /**
//   * @param type
//   */
//  public HxLocalsImpl chop(Object type) {
//    this.size -= (1 + extraSlotCapacity(type));
//    return this;
//  }

  /**
   * @return
   */
  @Override
  public int size() {
    return this.size;
  }

  /**
   * Clears this locals' table
   */
  @Override
  public void clear() {
    this.size = 0;
    Arrays.fill(this.table, null);
  }

  @Override
  public HxLocals copy() {
    return new HxLocalsImpl(this.table.clone(), this.size);
  }

  @Override
  public Iterator<Object> iterator() {
    return Utils.iterator(this.table, 0, this.size);
  }
}
