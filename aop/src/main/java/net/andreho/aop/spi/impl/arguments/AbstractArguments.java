package net.andreho.aop.spi.impl.arguments;

import net.andreho.aop.spi.Arguments;

import java.lang.reflect.Field;
import java.util.function.Consumer;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 23:52.
 */
public abstract class AbstractArguments implements Arguments {

  private static final int MIN_LENGTH = 0;
  private static final int MAX_LENGTH = 16;
  private static final ClassValue<Field[]> CACHED_ARGUMENTS_FIELDS = new ClassValue<Field[]>() {
    @Override
    protected Field[] computeValue(final Class<?> type) {
      final Field[] fields = type.getDeclaredFields();
      if(fields.length > MAX_LENGTH) {
        throw new IllegalStateException(
          "Invalid argument's state definition, because of unsupported count of defined field's: "+fields.length);
      }
      return fields;
    }
  };

  private final byte[] info;

  protected AbstractArguments(final int length) {
    this.info = new byte[checkArgumentsLength(length)];
  }

  private static int checkArgumentsLength(final int length) {
    if(length < MIN_LENGTH || length > MAX_LENGTH) {
      throw new IllegalArgumentException("Invalid length of argument's list: "+length);
    }
    return length;
  }

  private static Sort toSort(byte value) {
    return Sort.fromCode(value >>> 4);
  }

  private static Field toField(Class<?> cls, int idx) {
    return CACHED_ARGUMENTS_FIELDS.get(cls)[idx];
  }

  private Field toField(int idx) {
    return toField(getClass(), 15 & info(idx));
  }

  protected final byte info(int idx) {
    return info[idx];
  }

  @Override
  public int length() {
    return info.length;
  }

  @Override
  public Sort getSort(final int idx) {
    return toSort(info(idx));
  }

  @Override
  public final boolean getBoolean(final int idx) {
    try {
      return toField(idx).getBoolean(this);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to fetch a boolean at: "+ idx, e);
    }
  }

  @Override
  public final byte getByte(final int idx) {
    try {
      return toField(idx).getByte(this);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to fetch a byte at: "+ idx, e);
    }
  }

  @Override
  public final short getShort(final int idx) {
    try {
      return toField(idx).getShort(this);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to fetch a short at: "+ idx, e);
    }
  }

  @Override
  public final char getChar(final int idx) {
    try {
      return toField(idx).getChar(this);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to fetch a char at: "+ idx, e);
    }
  }

  @Override
  public final int getInt(final int idx) {
    try {
      return toField(idx).getInt(this);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to fetch a int at: "+ idx, e);
    }
  }

  @Override
  public final float getFloat(final int idx) {
    try {
      return toField(idx).getFloat(this);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to fetch a float at: "+ idx, e);
    }
  }

  @Override
  public final long getLong(final int idx) {
    try {
      return toField(idx).getLong(this);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to fetch a long at: "+ idx, e);
    }
  }

  @Override
  public final double getDouble(final int idx) {
    try {
      return toField(idx).getDouble(this);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to fetch a double: "+ idx, e);
    }
  }

  @Override
  public final <T> T getObject(final int idx) {
    try {
      return (T) toField(idx).get(this);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to fetch an object: "+ idx, e);
    }
  }

  @Override
  public final Arguments setBoolean(final int idx,
                              final boolean value) {
    try {
      toField(idx).setBoolean(this, value);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to set a boolean: "+ idx, e);
    }
    return this;
  }

  @Override
  public final Arguments setByte(final int idx,
                           final byte value) {
    try {
      toField(idx).setByte(this, value);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to set a byte: "+ idx, e);
    }
    return this;
  }

  @Override
  public final Arguments setShort(final int idx,
                            final short value) {
    try {
      toField(idx).setShort(this, value);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to set a short: "+ idx, e);
    }
    return this;
  }

  @Override
  public final Arguments setChar(final int idx,
                           final char value) {
    try {
      toField(idx).setChar(this, value);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to set a char: "+ idx, e);
    }
    return this;
  }

  @Override
  public final Arguments setInt(final int idx,
                          final int value) {
    try {
      toField(idx).setInt(this, value);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to set a int: "+ idx, e);
    }
    return this;
  }

  @Override
  public final Arguments setFloat(final int idx,
                            final float value) {
    try {
      toField(idx).setFloat(this, value);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to set a float: "+ idx, e);
    }
    return this;
  }

  @Override
  public final Arguments setLong(final int idx,
                           final long value) {
    try {
      toField(idx).setLong(this, value);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to set a long: "+ idx, e);
    }
    return this;
  }

  @Override
  public final Arguments setDouble(final int idx,
                             final double value) {
    try {
      toField(idx).setDouble(this, value);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to set a double: "+ idx, e);
    }
    return this;
  }

  @Override
  public final Arguments setObject(final int idx,
                             final Object value) {
    try {
      toField(idx).set(this, value);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to set an object: "+ idx, e);
    }
    return this;
  }

  @Override
  public final Object[] toArray() {
    final Object[] array = new Object[length()];
    for (int i = 0; i < array.length; i++) {
      array[i] = getObject(i);
    }
    return array;
  }

  @Override
  public <T> void toConsumer(final Consumer<T> consumer) {
    for (Object o : this) {
      consumer.accept((T) o);
    }
  }

  @Override
  public final Iterator iterator() {
    return new ArgumentsIteratorImpl(this);
  }
}
