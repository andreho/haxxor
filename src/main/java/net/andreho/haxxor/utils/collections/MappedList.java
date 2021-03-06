package net.andreho.haxxor.utils.collections;

import java.util.AbstractList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Function;

/**
 * <br/>Created by a.hofmann on 05.06.2017 at 19:40.
 */
public class MappedList<E, V>
    extends AbstractList<V>
    implements List<V> {

  private final Function<E, V> toValue;
  private final List<E> list;

  /**
   * @param list
   * @param toValue
   * @param <E>
   * @param <V>
   * @return
   */
  public static <E,V> List<V> create(final List<E> list,
                                     final Function<E, V> toValue) {
    return new MappedList<>(list, toValue);
  }

  private MappedList(final List<E> list,
                    final Function<E, V> toValue) {
    this.list = Objects.requireNonNull(list);
    this.toValue = Objects.requireNonNull(toValue);
  }

  private V toValue(E element) {
    return toValue.apply(element);
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }

  @Override
  public <T> T[] toArray(final T[] a) {
    return list.toArray(a);
  }

  @Override
  public V get(final int index) {
    return toValue(list.get(index));
  }

  @Override
  public void add(final int index,
                  final V value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public V set(final int index,
               final V value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void sort(final Comparator<? super V> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected void removeRange(final int fromIndex,
                             final int toIndex) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<V> subList(final int fromIndex,
                         final int toIndex) {
    return new MappedList<>(list.subList(fromIndex, toIndex), toValue);
  }

  @Override
  public Spliterator<V> spliterator() {
    return new MappedSpliterator<>(list.spliterator(), toValue);
  }
}
