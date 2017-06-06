package net.andreho.haxxor;

import net.andreho.asm.org.objectweb.asm.Type;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 30.05.2017 at 14:35.
 */
public abstract class Utils {
  private static final int DEFAULT_READ_BUFFER_LENGTH = 1028;

  /**
   * Extracts names of given classes
   * @param classes to process
   * @return
   */
  public static String[] toClassNames(Class<?> ... classes) {
    final String[] names = new String[classes.length];
    for (int i = 0; i < classes.length; i++) {
      names[i] = classes[i].getName();
    }
    return names;
  }

  /**
   * Checks the given collection whether it needs to be initialized or not. Initialisation is only needed if the
   * given collection is <b>null</b> or equals to either {@link Collections#EMPTY_LIST} or {@link Collections#EMPTY_SET}
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
   * @param map to check
   * @return <b>true</b> if the given map can't be used and must be initialized, <b>false</b> otherwise
   */
  public static boolean isUninitialized(Map<?,?> map) {
    return
        map == null ||
        map == Collections.EMPTY_MAP;
  }

  /**
   * Reads the content of the given stream into a byte array
   *
   * @param inputStream to read
   * @return content of given stream as byte-array
   * @implNote <b>ATTENTION:</b> input still must be closed by method's caller
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
   * @param classname
   * @return
   */
  public static String normalizeClassname(String classname) {
    Objects.requireNonNull(classname, "Class name is null");
    return normalizeType(Type.getType(classname.replace('.', '/')));
  }

  /**
   * @param signature
   * @return
   */
  public static String[] normalizeSignature(String signature) {
    Type[] types = Type.getArgumentTypes(signature);
    String[] names = new String[types.length];
    for (int i = 0; i < types.length; i++) {
      names[i] = normalizeType(types[i]);
    }
    return names;
  }

  /**
   * @param signature
   * @return
   */
  public static String normalizeReturnType(final String signature) {
    return normalizeType(Type.getReturnType(signature));
  }

  private static String normalizeType(final Type type) {
    return type.getClassName();
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
  public static final <T> Iterable<T> iterable(final T[] array, final int offset, final int length) {
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
   * @param array  to iterate with
   * @param offset defines start index for iteration
   * @param length defines end index for iteration (exclusive)
   * @param <T>
   * @return an iterator that visits elements of given array using given offset and length
   */
  public static final <T> Iterator<T> iterator(final T[] array, final int offset, final int length) {
    return new Iterator<T>() {
      final int len = Math.min(array.length, length);
      int i = Math.min(array.length, offset);

      @Override
      public boolean hasNext() {
        return i < len;
      }

      @Override
      public T next() {
        return array[i++];
      }
    };
  }

  private Utils() {
  }
}
