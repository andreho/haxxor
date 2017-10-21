package net.andreho.aop.api.injectable.locals.impl;

import net.andreho.aop.api.injectable.locals.ElementLocal;
import net.andreho.aop.api.injectable.locals.Key;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <br/>Created by a.hofmann on 07.10.2017 at 10:10.
 */
public abstract class AbstractElementLocal<E>
  implements ElementLocal<E> {

  private final E element;
  private final ConcurrentHashMap<Key<?>, Object> storage;

  public AbstractElementLocal(final E element) {
    this.element = Objects.requireNonNull(element);
    this.storage = new ConcurrentHashMap<>(8);
  }

  @Override
  public E getElement() {
    return element;
  }

  @Override
  public <V> V get(final Key<V> key) {
    return (V) storage.get(key);
  }

  @Override
  public <V> void set(final Key<V> key,
                      final V value) {
    storage.put(key, value);
  }

  @Override
  public <V> boolean compareAndSwap(final Key<V> key,
                                    final V expected,
                                    final V update) {
    return storage.replace(key, expected, update);
  }
}
