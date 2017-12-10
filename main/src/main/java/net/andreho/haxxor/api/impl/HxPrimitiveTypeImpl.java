package net.andreho.haxxor.api.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxSort;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.api.HxTypeReference;

import java.util.Objects;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public class HxPrimitiveTypeImpl
  implements HxType,
             HxTypeReference {

  private static final int PRIMITIVE_MODIFIERS = Modifiers.PUBLIC.toBit() |
                                                 Modifiers.FINAL.toBit() |
                                                 Modifiers.ABSTRACT.toBit();
  private final Hx haxxor;
  private final String name;
  private final HxSort sort;

  public HxPrimitiveTypeImpl(final Haxxor haxxor,
                             final String name) {
    this.haxxor = haxxor;
    this.name = name;
    this.sort = HxSort.fromName(name);
  }

  @Override
  public Hx getHaxxor() {
    return haxxor;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public HxSort getSort() {
    return sort;
  }

  @Override
  public boolean isPrimitive() {
    return true;
  }

  @Override
  public boolean isReference() {
    return true;
  }

  @Override
  public int getModifiers() {
    return PRIMITIVE_MODIFIERS;
  }

  @Override
  public HxType toReference() {
    return this;
  }

  @Override
  public HxType toType() {
    return this;
  }

  @Override
  public Class<?> toClass(final ClassLoader classLoader)
  throws ClassNotFoundException {
    return getSort().getPrimitiveClass();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof HxType)) {
      return false;
    }
    final HxType that = (HxType) o;
    return Objects.equals(name, that.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return getName();
  }
}
