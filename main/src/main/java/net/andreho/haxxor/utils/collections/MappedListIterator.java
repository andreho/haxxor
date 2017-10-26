package net.andreho.haxxor.utils.collections;

import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Function;

/**
 * <br/>Created by a.hofmann on 05.06.2017 at 20:16.
 */
public class MappedListIterator<E,V> implements ListIterator<V> {

  private final ListIterator<E> iterator;
  private final Function<E, V> toValue;
  private final Function<V, E> toElement;

  public MappedListIterator(final ListIterator<E> iterator,
                            final Function<E, V> toValue,
                            final Function<V, E> toElement) {

    this.iterator = Objects.requireNonNull(iterator);
    this.toValue = Objects.requireNonNull(toValue);
    this.toElement = Objects.requireNonNull(toElement);
  }

  private E toElement(V value) {
    return toElement.apply(value);
  }

  private V toValue(E element) {
    return toValue.apply(element);
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public V next() {
    return toValue(iterator.next());
  }

  @Override
  public boolean hasPrevious() {
    return iterator.hasPrevious();
  }

  @Override
  public V previous() {
    return toValue(iterator.previous());
  }

  @Override
  public int nextIndex() {
    return iterator.nextIndex();
  }

  @Override
  public int previousIndex() {
    return iterator.previousIndex();
  }

  @Override
  public void remove() {
    iterator.remove();
  }

  @Override
  public void set(final V v) {
    iterator.set(toElement(v));
  }

  @Override
  public void add(final V v) {
    iterator.add(toElement(v));
  }
}
