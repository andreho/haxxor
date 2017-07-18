package net.andreho.args;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 23:52.
 */
public abstract class AbstractArguments
  implements Arguments {

  private static final int MAX_LENGTH = 256;
  private static final ClassValue<Field[]> CACHED_ARGUMENTS_FIELDS = new ClassValue<Field[]>() {
    @Override
    protected Field[] computeValue(final Class<?> type) {
      final Field[] fields = type.getDeclaredFields();
      final Field[] notStaticFields =
        Stream.of(fields)
              .filter(field -> !Modifier.isStatic(field.getModifiers()))
              .toArray(Field[]::new);

      if (notStaticFields.length > MAX_LENGTH) {
        throw new IllegalStateException(
          "Invalid argument's state definition, because of unsupported count of defined field's: " + fields.length);
      }
      Arrays.sort(notStaticFields, Comparator.comparing(Field::getName));
      return notStaticFields;
    }
  };

  private static Field toField(final Class<?> cls, final int idx) {
    return CACHED_ARGUMENTS_FIELDS.get(cls)[idx];
  }

  protected AbstractArguments() {
  }

  protected abstract int info(int idx);

  protected abstract ArgumentsType toSort(int info);

  protected abstract int toFieldsIndex(int index);

  public abstract int length();

  private Field toField(int idx) {
    return toField(getClass(), toFieldsIndex(idx));
  }

  @Override
  public ArgumentsType getType(final int idx) {
    return toSort(info(idx));
  }

  @Override
  public final boolean getBoolean(final int idx) {
    try {
      return toField(idx).getBoolean(this);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to fetch a boolean at: " + idx, e);
    }
  }

  @Override
  public final byte getByte(final int idx) {
    try {
      return toField(idx).getByte(this);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to fetch a byte at: " + idx, e);
    }
  }

  @Override
  public final short getShort(final int idx) {
    try {
      return toField(idx).getShort(this);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to fetch a short at: " + idx, e);
    }
  }

  @Override
  public final char getChar(final int idx) {
    try {
      return toField(idx).getChar(this);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to fetch a char at: " + idx, e);
    }
  }

  @Override
  public final int getInt(final int idx) {
    try {
      return toField(idx).getInt(this);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to fetch a int at: " + idx, e);
    }
  }

  @Override
  public final float getFloat(final int idx) {
    try {
      return toField(idx).getFloat(this);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to fetch a float at: " + idx, e);
    }
  }

  @Override
  public final long getLong(final int idx) {
    try {
      return toField(idx).getLong(this);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to fetch a long at: " + idx, e);
    }
  }

  @Override
  public final double getDouble(final int idx) {
    try {
      return toField(idx).getDouble(this);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to fetch a double at: " + idx, e);
    }
  }

  @Override
  public final <T> T getObject(final int idx) {
    try {
      return (T) toField(idx).get(this);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to fetch an object at: " + idx, e);
    }
  }

  @Override
  public final Arguments setBoolean(final int idx,
                                    final boolean value) {
    try {
      toField(idx).setBoolean(this, value);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to set a boolean at: " + idx, e);
    }
    return this;
  }

  @Override
  public final Arguments setByte(final int idx,
                                 final byte value) {
    try {
      toField(idx).setByte(this, value);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to set a byte at: " + idx, e);
    }
    return this;
  }

  @Override
  public final Arguments setShort(final int idx,
                                  final short value) {
    try {
      toField(idx).setShort(this, value);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to set a short at: " + idx, e);
    }
    return this;
  }

  @Override
  public final Arguments setChar(final int idx,
                                 final char value) {
    try {
      toField(idx).setChar(this, value);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to set a char at: " + idx, e);
    }
    return this;
  }

  @Override
  public final Arguments setInt(final int idx,
                                final int value) {
    try {
      toField(idx).setInt(this, value);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to set an int at: " + idx, e);
    }
    return this;
  }

  @Override
  public final Arguments setFloat(final int idx,
                                  final float value) {
    try {
      toField(idx).setFloat(this, value);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to set a float at: " + idx, e);
    }
    return this;
  }

  @Override
  public final Arguments setLong(final int idx,
                                 final long value) {
    try {
      toField(idx).setLong(this, value);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to set a long at: " + idx, e);
    }
    return this;
  }

  @Override
  public final Arguments setDouble(final int idx,
                                   final double value) {
    try {
      toField(idx).setDouble(this, value);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to set a double at: " + idx, e);
    }
    return this;
  }

  @Override
  public final Arguments setObject(final int idx,
                                   final Object value) {
    try {
      toField(idx).set(this, value);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to set an object at: " + idx, e);
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
  public final ArgumentsIterator iterator() {
    return new ArgumentsIteratorImpl(this);
  }

  @Override
  public boolean isEmpty() {
    return length() == 0;
  }
}
