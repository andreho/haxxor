package net.andreho.aop.api.injectable.locals.impl;

import net.andreho.aop.api.injectable.locals.Key;

import java.lang.ref.WeakReference;

/**
 * <br/>Created by a.hofmann on 11.10.2017 at 18:12.
 */
public class KeyImpl<V> extends WeakReference<V> implements Key<V> {
  private final int id;

  public KeyImpl(final V referent,
                 final int id) {
    super(referent);
    this.id = id;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public Object getRepresentative() {
    return get();
  }

  @Override
  public int compareTo(final Key<?> other) {
    return Integer.compare(getId(), other.getId());
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Key)) {
      return false;
    }

    final KeyImpl<?> key = (KeyImpl<?>) o;
    return id == key.id;
  }

  @Override
  public int hashCode() {
    return id;
  }

  @Override
  public String toString() {
    return "Key{" + id + "}";
  }
}
