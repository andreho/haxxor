package net.andreho.haxxor.cgen.impl;

import net.andreho.haxxor.cgen.HxInstruction;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 12.12.2017 at 02:23.
 */
public abstract class AbstractInstructionProperty<V> implements HxInstruction.Property<V> {
  private final String name;

  public AbstractInstructionProperty(final String name) {
    this.name = name;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof HxInstruction.Property)) {
      return false;
    }
    final HxInstruction.Property<?> that = (HxInstruction.Property<?>) o;
    return Objects.equals(name, that.name());
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
