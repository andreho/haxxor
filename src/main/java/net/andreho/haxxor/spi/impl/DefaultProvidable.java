package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.Hx;
import net.andreho.haxxor.spi.HxProvidable;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 10.12.2017 at 01:05.
 */
public class DefaultProvidable<T> implements HxProvidable<T> {
  private final String name;

  public DefaultProvidable(final String name) {
    this.name = name;
  }

  @Override
  public String name() {
    return null;
  }

  @Override
  public T fetch(final Hx hx) {
    return hx.providePart(this);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof HxProvidable)) {
      return false;
    }
    final HxProvidable<?> that = (HxProvidable<?>) o;
    return Objects.equals(name, that.name());
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "HxProvidable{" +
           "name='" + name + '\'' +
           '}';
  }
}
