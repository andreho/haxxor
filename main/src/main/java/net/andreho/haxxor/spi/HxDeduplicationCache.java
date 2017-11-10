package net.andreho.haxxor.spi;

import java.util.Iterator;

/**
 * <br/>Created by a.hofmann on 26.10.2017 at 18:21.
 */
public interface HxDeduplicationCache
  extends Iterable<String> {

  /**
   * @param chars to cache or to deduplicate
   * @param length of the given chars array
   * @return a cached already existing de-duplicated value or
   * a string value of the given value
   */
  String deduplicate(char[] chars, int length);

  /**
   * @param value to evict
   * @return <b>true</b> if the given value was successfully evicted from this cache
   */
  default boolean evict(String value) {
    throw new UnsupportedOperationException();
  }

  /**
   * @param value
   * @return
   */
  default boolean has(String value) {
    return has(value.toCharArray(), value.length());
  }

  /**
   * @param chars
   * @param length
   * @return
   */
  default boolean has(char[] chars, int length) {
    throw new UnsupportedOperationException();
  }

  /**
   * @return the actual size of this cache
   */
  int size();

  /**
   * @return the maximal capacity of this cache
   */
  int capacity();

  /**
   * @return
   */
  @Override
  default Iterator<String> iterator() {
    throw new UnsupportedOperationException();
  }
}
