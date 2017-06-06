package net.andreho.haxxor.spec.impl.misc;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;

/**
 * <br/>Created by a.hofmann on 05.06.2017 at 19:42.
 */
public class MappedIterator<E,V>
    implements Iterator<V> {

  private final Function<E, V> mapping;
  private final Iterator<E> iterator;

  public MappedIterator(final Function<E, V> mapping,
                        final Iterator<E> iterator) {
    this.mapping = Objects.requireNonNull(mapping);
    this.iterator = Objects.requireNonNull(iterator);
  }

  private V map(E e) {
    return mapping.apply(e);
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public V next() {
    return map(iterator.next());
  }

  @Override
  public void remove() {
    iterator.remove();
  }
}
