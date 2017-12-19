package net.andreho.haxxor.utils.collections;

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * <br/>Created by a.hofmann on 05.06.2017 at 19:42.
 */
public class MappedSpliterator<E, V>
    implements Spliterator<V> {

  private final Function<E, V> toValue;
  private final Spliterator<E> spliterator;

  public MappedSpliterator(final Spliterator<E> spliterator,
                           final Function<E, V> toValue) {

    this.spliterator = Objects.requireNonNull(spliterator);
    this.toValue = Objects.requireNonNull(toValue);
  }

  private Consumer<E> mappingConsumer(final Consumer<? super V> action) {
    return (e) -> action.accept(toValue.apply(e));
  }

  @Override
  public void forEachRemaining(final Consumer<? super V> action) {
    spliterator.forEachRemaining(mappingConsumer(action));
  }

  @Override
  public boolean tryAdvance(final Consumer<? super V> action) {
    return spliterator.tryAdvance(mappingConsumer(action));
  }

  @Override
  public long getExactSizeIfKnown() {
    return spliterator.getExactSizeIfKnown();
  }

  @Override
  public boolean hasCharacteristics(final int characteristics) {
    return spliterator.hasCharacteristics(characteristics);
  }

  @Override
  public Spliterator<V> trySplit() {
    Spliterator<E> trySplit = spliterator.trySplit();
    if (trySplit == null) {
      return null;
    }
    return new MappedSpliterator<>(trySplit, toValue);
  }

  @Override
  public long estimateSize() {
    return spliterator.estimateSize();
  }

  @Override
  public int characteristics() {
    return spliterator.characteristics();
  }
}
