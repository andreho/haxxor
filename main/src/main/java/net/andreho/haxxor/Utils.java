package net.andreho.haxxor;

import net.andreho.asm.org.objectweb.asm.Type;
import net.andreho.haxxor.spec.api.HxConstants;
import net.andreho.haxxor.spi.HxClassnameNormalizer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
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

  private Utils() {
  }

  public static String toPrimitiveClassname(char c) {
    switch (c) {
      case 'V':
        return "void";
      case 'Z':
        return "boolean";
      case 'B':
        return "byte";
      case 'S':
        return "short";
      case 'C':
        return "char";
      case 'I':
        return "int";
      case 'F':
        return "float";
      case 'J':
        return "long";
      case 'D':
        return "double";
      default:
        throw new IllegalArgumentException("Not a primitive literal {V,Z,B,S,C,I,F,J,D}: " + c);
    }
  }

  /**
   * Extracts names of given classes
   *
   * @param classes to process
   * @return
   */
  public static String[] toClassNames(final Class<?>... classes) {
    final String[] names = new String[classes.length];
    for (int i = 0; i < classes.length; i++) {
      names[i] = classes[i].getName();
    }
    return names;
  }

  /**
   * Extracts names of given classes
   *
   * @param classes to process
   * @return
   */
  public static String[] toClassNames(final HxClassnameNormalizer classNameProvider,
                                      final Class<?>... classes) {
    final String[] names = new String[classes.length];
    for (int i = 0; i < classes.length; i++) {
      names[i] = classNameProvider.toNormalizedClassname(classes[i].getName());
    }
    return names;
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
   * Transforms if needed the given descriptor to a normalized classname of corresponding primitive type
   * @param desc to check and transform
   * @return possibly transformed classname or the same descriptor if not a primitive
   */
  public static String toPrimitiveClassname(String desc) {
    //Both examples like: [I or LI; have length over one.
    if(desc.length() != 1) {
      return desc;
    }
    return toPrimitiveClassname(desc, 0);
  }

  /**
   * Transforms the given descriptor to a normalized classname of corresponding primitive type
   * @param desc to check and transform
   * @param index to look at
   * @return transformed classname of the given primitive type at the given index
   */
  public static String toPrimitiveClassname(String desc, int index) {
    return toPrimitiveClassname(desc.charAt(index));
  }

  /**
   * @param classname
   * @return
   */
  public static String normalizeClassname(String classname) {
    Objects.requireNonNull(classname, "Classname can't be null.");
    String internalForm = classname.replace(HxConstants.JAVA_PACKAGE_SEPARATOR_CHAR, HxConstants.INTERNAL_PACKAGE_SEPARATOR_CHAR);
    if(classname.endsWith(";")) {
      return normalizeType(Type.getType(internalForm));
    }
    return normalizeType(Type.getObjectType(internalForm));
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
   * @param array  to iterate with
   * @param offset defines start index for iteration
   * @param length defines end index for iteration (exclusive)
   * @param <T>
   * @return an iterator that visits elements of given array using given offset and length
   */
  public static final <T> Iterator<T> iterator(final T[] array,
                                               final int offset,
                                               final int length) {
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
}
