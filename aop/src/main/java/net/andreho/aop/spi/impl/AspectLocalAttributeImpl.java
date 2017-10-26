package net.andreho.aop.spi.impl;

import net.andreho.aop.spi.AspectLocalAttribute;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.cgen.HxLocalVariable;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 20.06.2017 at 04:43.
 */
public class AspectLocalAttributeImpl
  implements AspectLocalAttribute {

  private final HxLocalVariable hxLocalVariable;
  private final HxType type;
  private boolean handled;

  public AspectLocalAttributeImpl(final HxLocalVariable hxLocalVariable,
                                  final HxType type) {
    this.hxLocalVariable = Objects.requireNonNull(hxLocalVariable);
    this.type = Objects.requireNonNull(type);
  }

  @Override
  public HxLocalVariable getHxLocalVariable() {
    return hxLocalVariable;
  }

  @Override
  public String getName() {
    return hxLocalVariable.getName();
  }

  @Override
  public HxType getType() {
    return type;
  }

  @Override
  public int getIndex() {
    return getHxLocalVariable().getIndex();
  }

  @Override
  public boolean wasHandled() {
    return handled;
  }

  @Override
  public void markAsHandled() {
    this.handled = true;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AspectLocalAttribute)) {
      return false;
    }

    final AspectLocalAttribute that = (AspectLocalAttribute) o;

    return getName().equals(that.getName());
  }

  @Override
  public int hashCode() {
    return getName().hashCode();
  }

  @Override
  public String toString() {
    return "AspectLocalAttribute ("+getName()+")";
  }
}
