package net.andreho.aop.spi.impl;

import net.andreho.aop.spi.AspectAttribute;
import net.andreho.haxxor.spec.api.HxType;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 20.06.2017 at 04:43.
 */
public class AspectAttributeImpl implements AspectAttribute {

  private final HxType type;
  private final String name;
  private int index;

  public AspectAttributeImpl(final String name,
                             final HxType type,
                             final int index) {
    this.type = Objects.requireNonNull(type);
    this.name = Objects.requireNonNull(name);
    this.index = index;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public HxType getType() {
    return type;
  }

  @Override
  public int getIndex() {
    return index;
  }

  @Override
  public void setIndex(final int index) {
    if(index < 0) {
      throw new IllegalArgumentException("Invalid index: "+index);
    }
    this.index = index;
  }

  @Override
  public void shift(final int delta) {
    setIndex(getIndex() + delta);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AspectAttribute)) {
      return false;
    }

    final AspectAttribute that = (AspectAttribute) o;

    return name.equals(that.getName());
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public String toString() {
    return "AspectAttribute ("+getName()+")";
  }
}
