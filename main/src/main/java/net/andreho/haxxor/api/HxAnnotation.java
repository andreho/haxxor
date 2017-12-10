package net.andreho.haxxor.api;

import net.andreho.haxxor.Hx;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;
import java.util.Set;

/**
 * Implementation notes: <br/>
 * Implementors of this interface must use its {@link #getType()} as source
 * for {@link Object#hashCode()} and {@link Object#equals(Object)}
 * and never its {@link HxOwned#getDeclaringMember()}.<br/>
 * Created by a.hofmann on 31.05.2015.
 */
public interface HxAnnotation
    extends HxOwned<HxAnnotation>,
            HxProvider,
            HxTyped,
            Cloneable {

  /**
   * @return
   */
  @Override
  default Hx getHaxxor() {
    return getType().getHaxxor();
  }

  /**
   * @return type of this annotation
   */
  HxType getType();

  /**
   * @return <b>true</b> if this annotation refers to an annotation
   * with retention policy equal to {@link RetentionPolicy#RUNTIME}
   */
  boolean isVisible();

  /**
   * @return
   */
  default RetentionPolicy getRetention() {
    //return RetentionPolicy.SOURCE; -> is never stored in the bytecode
    if(isVisible()) {
      return RetentionPolicy.RUNTIME;
    }
    return RetentionPolicy.CLASS;
  }

  /**
   * @return
   */
  Set<ElementType> getElementTypes();

  /**
   * @return map with annotation's attributes
   */
  Map<String, HxAnnotationAttribute<?, ?>> getAttributeMap();

  /**
   * @return a new copy instance of this
   */
  HxAnnotation clone();

  /**
   * Retrieves annotation's value by name
   *
   * @param name of attribute
   * @param <V>
   * @return value of the attribute
   * @apiNote this method can fetch the referenced annotation in order to get default values for not defined
   * attributes with default values
   */
  <V> V getAttribute(String name);

  /**
   * Retrieves annotation's value by name using the provided default value if this attribute wasn't specified
   *
   * @param name of attribute
   * @param defaultValue of attribute
   * @param <V>
   * @return value of the attribute
   * @apiNote this method <u>doesn't fetch</u> the referenced annotation to resolve the default value,
   * because the default value was provided by user
   */
  <V> V getAttribute(String name, V defaultValue);

  /**
   * Retrieves annotation's value by name
   *
   * @param name of attribute
   * @param type of attribute's value (as checked assistance)
   * @param <V>
   * @return value of the attribute
   * @implNote this method can fetch the referenced annotation in order to get default values for not defined
   * attributes with default values
   */
  <V> V getAttribute(String name, Class<V> type);

  /**
   * @param name
   * @return
   */
  boolean hasAttribute(String name);

  /**
   * @param name
   * @return
   */
  boolean hasDefaultAttribute(String name);

  /**
   * @param name
   * @return
   */
  <V> V getDefaultAttribute(String name);

  /**
   * @return is the number of attributes stored in this annotation
   */
  int size();

  /**
   * @return
   */
  default boolean isEmpty() {
    return size() == 0;
  }

  //----------------------------------------------------------------------------------------------------------------

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   */
  HxAnnotation setAttribute(final String name, boolean value);

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   */
  HxAnnotation setAttribute(final String name, byte value);

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   */
  HxAnnotation setAttribute(final String name, char value);

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   */
  HxAnnotation setAttribute(final String name, short value);

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   */
  HxAnnotation setAttribute(final String name, int value);

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   */
  HxAnnotation setAttribute(final String name, float value);

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   */
  HxAnnotation setAttribute(final String name, long value);

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   */
  HxAnnotation setAttribute(final String name, double value);

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   */
  HxAnnotation setAttribute(final String name, String value);

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   */
  <E extends Enum<E>> HxAnnotation setAttribute(final String name, E value);

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   */
  HxAnnotation setAttribute(final String name, HxEnum value);

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   */
  HxAnnotation setAttribute(final String name, Class<?> value);

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   */
  HxAnnotation setAttribute(final String name, HxType value);

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   */
  HxAnnotation setAttribute(final String name, HxAnnotation value);

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   */
  HxAnnotation setAttribute(final String name, boolean[] value);

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   */
  HxAnnotation setAttribute(final String name, byte[] value);

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   */
  HxAnnotation setAttribute(final String name, char[] value);

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   */
  HxAnnotation setAttribute(final String name, short[] value);

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   */
  HxAnnotation setAttribute(final String name, int[] value);

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   */
  HxAnnotation setAttribute(final String name, float[] value);

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   */
  HxAnnotation setAttribute(final String name, long[] value);

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   */
  HxAnnotation setAttribute(final String name, double[] value);

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   */
  HxAnnotation setAttribute(final String name, String[] value);

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   */
  HxAnnotation setAttribute(final String name, HxEnum[] value);

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   */
  <E extends Enum<E>> HxAnnotation setAttribute(final String name, E[] value);

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   */
  HxAnnotation setAttribute(final String name, Class<?>[] value);

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   */
  HxAnnotation setAttribute(final String name, HxType[] value);

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   */
  HxAnnotation setAttribute(final String name, HxAnnotation[] value);

  /**
   * @param name of the annotation's attribute
   * @param value of the annotation's attribute
   * @return this annotation
   * @see HxConstants#EMPTY_ARRAY
   */
  HxAnnotation setAttribute(final String name, HxConstants.EmptyArray[] value);
}
