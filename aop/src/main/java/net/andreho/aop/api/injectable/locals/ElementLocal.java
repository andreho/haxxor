package net.andreho.aop.api.injectable.locals;

/**
 * <br/>Created by a.hofmann on 06.10.2017 at 15:14.
 */
public interface ElementLocal<E> {

  /**
   * @return
   */
  E getElement();

  /**
   * @param key
   * @param <V>
   * @return
   */
  <V> V get(Key<V> key);

  /**
   * @param key
   * @param value
   * @param <V>
   * @return
   */
  <V> void set(Key<V> key, V value);

  /**
   * @param key
   * @param expected
   * @param update
   * @return
   */
  <V> boolean compareAndSwap(Key<V> key, V expected, V update);
}
