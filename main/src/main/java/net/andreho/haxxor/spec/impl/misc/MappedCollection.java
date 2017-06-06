package net.andreho.haxxor.spec.impl.misc;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * <br/>Created by a.hofmann on 05.06.2017 at 19:50.
 */
public class MappedCollection<E, V, C extends Collection<E>>
    extends AbstractCollection<V>
    implements Collection<V> {

  protected final Function<E, V> toValue;
  protected final C original;

  public MappedCollection(final C original,
                          final Function<E, V> toValue) {
    this.original = Objects.requireNonNull(original);
    this.toValue = Objects.requireNonNull(toValue);
  }

  protected final V toValue(E element) {
    return toValue.apply(element);
  }

  @Override
  public int size() {
    return original.size();
  }

  @Override
  public boolean isEmpty() {
    return original.isEmpty();
  }

  @Override
  public Iterator<V> iterator() {
    return new MappedIterator<>(toValue, original.iterator());
  }

  public boolean add(final V v) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean remove(final Object o) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Spliterator<V> spliterator() {
    return new MappedSpliterator<>(original.spliterator(), toValue);
  }

  @Override
  public Stream<V> stream() {
    return original.stream()
                   .map(toValue);
  }

  @Override
  public Stream<V> parallelStream() {
    return original.parallelStream()
                   .map(toValue);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Collection)) {
      return false;
    }

    Collection<?> c = (Collection<?>) o;
    if (c.size() != size()) {
      return false;
    }
    try {
      return containsAll(c);
    } catch (ClassCastException unused) {
      return false;
    } catch (NullPointerException unused) {
      return false;
    }
  }

  @Override
  public int hashCode() {
    int h = 0;
    Iterator<V> i = iterator();
    while (i.hasNext()) {
      V obj = i.next();
      if (obj != null) {
        h += obj.hashCode();
      }
    }
    return h;
  }
}
