package net.andreho.haxxor.api;

import net.andreho.haxxor.Hx;

import java.lang.reflect.Array;
import java.util.Objects;
import java.util.Optional;

/**
 * <br/>Created by a.hofmann on 04.06.2015.<br/>
 */
public final class HxEnum implements HxNamed, HxTyped, HxOrdered {
  private final HxType type;
  private final String name;
  private volatile Enum<?> fetchedEnum;


  public HxEnum(Hx haxxor, Enum e) {
    this(haxxor.reference(e.getClass().getName()), e.name());
  }

  public HxEnum(HxType type, String name) {
    this.type = Objects.requireNonNull(type, "Enum's type can't be null.");
    this.name = Objects.requireNonNull(name, "Enum's name can't be null.");

    if(name.isEmpty()) {
      throw new IllegalArgumentException("Enum's name can't be empty.");
    }
  }

  /**
   * @param haxxor
   * @param enumConstant
   * @param <E>
   * @return
   */
  public static <E extends Enum<E>> HxEnum toHxEnum(Hx haxxor, E enumConstant) {
    return new HxEnum(haxxor, enumConstant);
  }

  /**
   * Constructs an array with requested enum instances
   *
   * @param classLoader that is used for loading
   * @param enumType that is expected
   * @param array    of enum-refs
   * @return array with requested enum instances
   */
  public static <E extends Enum<E>> E[] toEnumArray(ClassLoader classLoader, Class<E> enumType, HxEnum... array) {
    final E[] result = (E[]) Array.newInstance(enumType, array.length);
    for (int i = 0; i < array.length; i++) {
      result[i] = array[i].loadEnum(classLoader);
    }
    return result;
  }

  /**
   * Constructs an array with requested enum instances
   *
   * @param enumType that is expected
   * @param array    of enum-refs
   * @return array with requested enum instances
   */
  public static <E extends Enum<E>> E[] toEnumArray(Class<E> enumType, HxEnum... array) {
    return toEnumArray(enumType.getClassLoader(), enumType, array);
  }

  /**
   * Transforms given enum array to an analogous hx-enum array
   *
   * @param haxxor to use
   * @param enums  to transform
   * @return a hx-enum array that is equal in meaning to the given one
   */
  public static <E extends Enum<E>> HxEnum[] toHxEnumArray(Hx haxxor, E... enums) {
    final HxEnum[] result = new HxEnum[enums.length];
    for (int i = 0; i < enums.length; i++) {
      result[i] = new HxEnum(haxxor, enums[i]);
    }
    return result;
  }

  @Override
  public int getIndex() {
    return getField().map(HxField::getIndex).orElse(-1);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public HxType getType() {
    return type;
  }

  public Optional<HxField> getField() {
    return getType().findField(getName(), getType());
  }

  public <E extends Enum<E>> E loadEnum() {
    return loadEnum(type.getHaxxor().getClassLoader());
  }

  public <E extends Enum<E>> E loadEnum(ClassLoader classLoader) {
    Enum<?> e = this.fetchedEnum;
    if (e != null && e.getClass().getClassLoader() == classLoader) {
      return (E) e;
    }
    try {
      final Class<E> enumClass = (Class<E>) type.toClass(classLoader);
      e = Enum.valueOf(enumClass, name);
    } catch (ClassNotFoundException ex) {
      throw new IllegalStateException("Given class-loader was unable to load the given enum-class: "+getType(), ex);
    }
    this.fetchedEnum = e;
    return (E) e;
  }

  @Override
  public String toString() {
    return type + "." + name;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }

    HxEnum otherEnum = (HxEnum) other;

    if (!Objects.equals(type, otherEnum.type)) {
      return false;
    }
    if (!Objects.equals(name, otherEnum.name)) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = type != null ? type.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }
}
