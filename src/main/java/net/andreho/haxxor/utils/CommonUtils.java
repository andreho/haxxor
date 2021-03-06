package net.andreho.haxxor.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <br/>Created by a.hofmann on 30.05.2017 at 14:35.
 */
public abstract class CommonUtils {
  private static final int DEFAULT_READ_BUFFER_LENGTH = 2048;
  private static final Class<?> ARRAY_TO_LIST_WRAPPER_CLASS = Arrays.asList().getClass();
  private static final Class<?> SINGLETON_LIST_WRAPPER_CLASS = Collections.singletonList(null).getClass();

  private CommonUtils() {
  }

  /**
   * @param list to check
   * @return <b>true</b> if the given list can only be partially modified, <b>false</b> otherwise
   */
  public static boolean isModifiable(List<?> list) {
    return
      ARRAY_TO_LIST_WRAPPER_CLASS != list.getClass() ||
      SINGLETON_LIST_WRAPPER_CLASS != list.getClass();
  }

  /**
   * Checks the given collection whether it needs to be initialized or not. Initialisation is only needed if the
   * given collection is <b>null</b> or equals to either {@link Collections#EMPTY_LIST} or {@link Collections#EMPTY_SET}
   *
   * @param collection to check
   * @return <b>true</b> if the given collection can't be used and must be initialized, <b>false</b> otherwise
   */
  public static boolean isUninitialized(Collection<?> collection) {
    return
        collection == null ||
        collection == Collections.EMPTY_LIST ||
        collection == Collections.EMPTY_SET;
  }

  /**
   * Checks the given map whether it needs to be initialized or not. Initialisation is only needed if the
   * given collection is <b>null</b> or equals to {@link Collections#EMPTY_MAP}
   *
   * @param map to check
   * @return <b>true</b> if the given map can't be used and must be initialized, <b>false</b> otherwise
   */
  public static boolean isUninitialized(Map<?, ?> map) {
    return
        map == null ||
        map == Collections.EMPTY_MAP;
  }

  /**
   * Reads the content of the given stream into a byte array
   *
   * @param inputStream to read
   * @return content of given stream as byte-array
   * @implNote <b>ATTENTION:</b> input-stream must be closed by method's caller
   */
  public static byte[] toByteArray(InputStream inputStream)
  throws IOException {
    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    int count;
    final byte[] buffer = new byte[DEFAULT_READ_BUFFER_LENGTH];
    while ((count = inputStream.read(buffer)) > -1) {
      outputStream.write(buffer, 0, count);
    }
    return outputStream.toByteArray();
  }

  /**
   * @param array
   * @param <T>
   * @return
   */
  public static final <T> Iterable<T> iterable(final T[] array) {
    return iterable(array, 0, array.length);
  }

  /**
   * Creates an iterable object to be used in a <u>for each</u> loop
   *
   * @param array  to use for iteration
   * @param offset defines getFirst index for iteration
   * @param length defines end index for iteration (exclusive)
   * @param <T>
   * @return an iterable object that always creates an iterator with given array and parameters
   */
  public static final <T> Iterable<T> iterable(final T[] array,
                                               final int offset,
                                               final int length) {
    return () -> iterator(array, offset, length);
  }

  /**
   * Creates an iterator for given array (<u>remove()</u> isn't supported)
   *
   * @param array to iterate with
   * @param <T>
   * @return an iterator that visits all elements of given array (from index 0 to array.length)
   */
  public static final <T> Iterator<T> iterator(final T[] array) {
    return iterator(array, 0, array.length);
  }

  /**
   * Creates an iterator for given array (<u>remove()</u> isn't supported)
   *
   * @param <T>
   * @param array  to iterate with
   * @param offset defines start index for iteration
   * @param length defines end index for iteration (exclusive)
   * @return an iterator that visits elements of given array using given offset and length
   */
  public static final <T> Iterator<T> iterator(final T[] array,
                                               final int offset,
                                               final int length) {
    return new ArrayIterator<>(
      Math.min(array.length, offset),
      Math.min(array.length, length),
      array
    );
  }

  /**
   * @param array to copy without removed element
   * @param idx of element to ignore
   * @param <T>
   * @return
   */
  public static <T> T[] copyWithout(final T[] array, final int idx) {
    final T[] copy = (T[]) Array.newInstance(array.getClass().getComponentType(), array.length - 1);
    for(int i = 0, j = 0; i < array.length; i++) {
      if(i == idx) {
        continue;
      }
      copy[j++] = array[i];
    }
    return copy;
  }


  /**
   * @param first
   * @param second
   * @param <T>
   * @return
   */
  public static <T> T[] asArray(T first, T second) {
    T[] array = (T[]) Array.newInstance(first.getClass(), 2);
    array[0] = first;
    array[1] = second;
    return array;
  }

  /**
   * @param array
   * @param value
   * @param <T>
   * @return
   */
  public static <T> T[] concat(T[] array, T value) {
    final T[] copied = Arrays.copyOf(array, array.length + 1);
    copied[array.length] = value;
    return copied;
  }

  /**
   * @param left
   * @param right
   * @param <T>
   * @return
   */
  public static <T> T[] concat(T[] left, T[] right) {
    final T[] copied = Arrays.copyOf(left, left.length + right.length);
    System.arraycopy(right, 0, copied, left.length, right.length);
    return copied;
  }

  private static class ArrayIterator<T>
    implements Iterator<T> {

    private final T[] array;
    private final int len;
    int idx;

    public ArrayIterator(final int idx,
                         final int len,
                         final T[] array) {
      this.idx = idx;
      this.len = len;
      this.array = array;
    }

    @Override
    public boolean hasNext() {
      return idx < len;
    }

    @Override
    public T next() {
      return array[idx++];
    }
  }
}
