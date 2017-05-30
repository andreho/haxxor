package net.andreho.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Function;

/**
 * <br/>Created by a.hofmann on 28.05.2015.<br/>
 */
public class ArrayUtils {
   public static boolean[] array(boolean... elements) {
      return elements;
   }

   public static byte[] array(byte... elements) {
      return elements;
   }

   public static char[] array(char... elements) {
      return elements;
   }

   public static short[] array(short... elements) {
      return elements;
   }

   public static int[] array(int... elements) {
      return elements;
   }

   public static float[] array(float... elements) {
      return elements;
   }

   public static long[] array(long... elements) {
      return elements;
   }

   public static double[] array(double... elements) {
      return elements;
   }

   public static Class<?>[] array(Class<?>... elements) {
      return elements;
   }

   public static String[] array(String... elements) {
      return elements;
   }

   public static <T> T[] array(T... elements) {
      return elements;
   }

   public static <T> T[] typedArray(Class<T> type, T... elements) {
      if (!type.isAssignableFrom(elements.getClass().getComponentType())) {
         throw new IllegalArgumentException("Incompatible array type: " + type.getName());
      }
      return elements;
   }

   public static Boolean[] wrap(boolean... elements) {
      Boolean[] wrapped = new Boolean[elements.length];

      for (int i = 0; i < elements.length; i++) {
         wrapped[i] = elements[i] ? Boolean.TRUE : Boolean.FALSE;
      }

      return wrapped;
   }

   //----------------------------------------------------------------------------------------------------------------

   public static Byte[] wrap(byte... elements) {
      Byte[] wrapped = new Byte[elements.length];

      for (int i = 0; i < elements.length; i++) {
         wrapped[i] = Byte.valueOf(elements[i]);
      }

      return wrapped;
   }

   public static Character[] wrap(char... elements) {
      Character[] wrapped = new Character[elements.length];

      for (int i = 0; i < elements.length; i++) {
         wrapped[i] = Character.valueOf(elements[i]);
      }

      return wrapped;
   }

   public static Short[] wrap(short... elements) {
      Short[] wrapped = new Short[elements.length];

      for (int i = 0; i < elements.length; i++) {
         wrapped[i] = Short.valueOf(elements[i]);
      }

      return wrapped;
   }

   public static Integer[] wrap(int... elements) {
      Integer[] wrapped = new Integer[elements.length];

      for (int i = 0; i < elements.length; i++) {
         wrapped[i] = Integer.valueOf(elements[i]);
      }

      return wrapped;
   }

   public static Float[] wrap(float... elements) {
      Float[] wrapped = new Float[elements.length];

      for (int i = 0; i < elements.length; i++) {
         wrapped[i] = Float.valueOf(elements[i]);
      }

      return wrapped;
   }

   public static Long[] wrap(long... elements) {
      Long[] wrapped = new Long[elements.length];

      for (int i = 0; i < elements.length; i++) {
         wrapped[i] = Long.valueOf(elements[i]);
      }

      return wrapped;
   }

   public static Double[] wrap(double... elements) {
      Double[] wrapped = new Double[elements.length];

      for (int i = 0; i < elements.length; i++) {
         wrapped[i] = Double.valueOf(elements[i]);
      }

      return wrapped;
   }

   /**
    * Appends given value to a expanded copy of the given array and returns that copy as result
    *
    * @param array is the source array
    * @param value to append
    * @param <T>
    * @return a <b>new</b> array containing all previous elements and given value at last position
    */
   public static final <T> T[] concat(final T[] array, final T value) {
      final T[] result = Arrays.copyOf(array, array.length + 1);
      result[array.length] = value;
      return result;
   }

   /**
    * Merges two array to one containing content of both (concat array B to array A)
    *
    * @param a
    * @param b
    * @param <T>
    * @return
    */
   public static <T> T[] concatArrays(final T[] a, final T[] b) {
      if (a != null && b != null) {
         final T[] newArray = (T[]) Array.newInstance(a.getClass().getComponentType(), a.length + b.length);
         System.arraycopy(a, 0, newArray, 0, a.length);
         System.arraycopy(b, 0, newArray, a.length, b.length);
         return newArray;
      }
      return a != null? a : b;
   }

   //----------------------------------------------------------------------------------------------------------------

   /**
    * @param array
    * @param <T>
    * @return
    */
   public static final <T> Iterable<T> iterable(final T[] array) {
      return iterable(array, 0, array.length);
   }

   //----------------------------------------------------------------------------------------------------------------

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

   //----------------------------------------------------------------------------------------------------------------

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

   /**
    * @param <T>
    * @param <K>
    * @param key
    * @param array
    * @param extractor
    * @return
    */
   public static <T, K extends Comparable<K>> T binarySearch(final K key,
                                                             final T[] array,
                                                             final Function<T, K> extractor) {
      return binarySearch(key, array, extractor, Comparator.naturalOrder());
   }

   /**
    * @param <T>
    * @param <K>
    * @param key
    * @param array
    * @param extractor
    * @param comparator
    * @return
    */
   public static <T, K> T binarySearch(final K key,
                                       final T[] array,
                                       final Function<T, K> extractor,
                                       final Comparator<K> comparator) {
      return binarySearch(key, array, extractor, comparator, 0, array.length);
   }

   /**
    * @param <T>
    * @param <K>
    * @param key
    * @param array
    * @param extractor
    * @param comparator
    * @param fromIndex
    * @param toIndex
    * @return
    */
   public static <T, K> T binarySearch(
      final K key,
      final T[] array,
      final Function<T, K> extractor,
      final Comparator<K> comparator,
      final int fromIndex,
      final int toIndex) {

      int low = fromIndex;
      int high = toIndex - 1;

      while (low <= high) {
         final int mid = (low + high) >>> 1;
         final T target = array[mid];
         final K midVal = extractor.apply(target);

         int cmp = comparator.compare(midVal, key);
         if (cmp < 0) {
            low = mid + 1;
         } else if (cmp > 0) {
            high = mid - 1;
         } else {
            return target; // key found
         }
      }
      return null;
   }

   public static class Constants {
      public static final boolean[] BOOLEAN_ARRAY = new boolean[0];
      public static final byte[] BYTE_ARRAY = new byte[0];
      public static final char[] CHAR_ARRAY = new char[0];
      public static final short[] SHORT_ARRAY = new short[0];
      public static final int[] INT_ARRAY = new int[0];
      public static final float[] FLOAT_ARRAY = new float[0];
      public static final long[] LONG_ARRAY = new long[0];
      public static final double[] DOUBLE_ARRAY = new double[0];
      public static final Object[] OBJECT_ARRAY = new Object[0];
      public static final String[] STRING_ARRAY = new String[0];
      public static final Class[] CLASS_ARRAY = new Class[0];
      public static final Constructor[] CONSTRUCTOR_ARRAY = new Constructor[0];
      public static final Method[] METHOD_ARRAY = new Method[0];
      public static final Field[] FIELD_ARRAY = new Field[0];
      public static final Parameter[] PARAMETER_ARRAY = new Parameter[0];
      public static final Annotation[] ANNOTATION_ARRAY = new Annotation[0];
   }

   //----------------------------------------------------------------------------------------------------------------
}
