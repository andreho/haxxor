package net.andreho.haxxor.api;

import net.andreho.haxxor.Hx;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

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
   * @return
   */
  RetentionPolicy getRetention();

  /**
   * @return
   */
  ElementType[] getElementTypes();

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
   * @return
   */
  int size();

  //----------------------------------------------------------------------------------------------------------------

  HxAnnotation attribute(final String name, boolean value);

  HxAnnotation attribute(final String name, byte value);

  HxAnnotation attribute(final String name, char value);

  HxAnnotation attribute(final String name, short value);

  HxAnnotation attribute(final String name, int value);

  HxAnnotation attribute(final String name, float value);

  HxAnnotation attribute(final String name, long value);

  HxAnnotation attribute(final String name, double value);

  HxAnnotation attribute(final String name, String value);

  <E extends Enum<E>> HxAnnotation attribute(final String name, E value);

  HxAnnotation attribute(final String name, HxEnum value);

  HxAnnotation attribute(final String name, Class<?> value);

  HxAnnotation attribute(final String name, HxType value);

  HxAnnotation attribute(final String name, HxAnnotation value);

  HxAnnotation attribute(final String name, boolean[] value);

  HxAnnotation attribute(final String name, byte[] value);

  HxAnnotation attribute(final String name, char[] value);

  HxAnnotation attribute(final String name, short[] value);

  HxAnnotation attribute(final String name, int[] value);

  HxAnnotation attribute(final String name, float[] value);

  HxAnnotation attribute(final String name, long[] value);

  HxAnnotation attribute(final String name, double[] value);

  HxAnnotation attribute(final String name, String[] value);

  HxAnnotation attribute(final String name, HxEnum[] value);

  <E extends Enum<E>> HxAnnotation attribute(final String name, E[] value);

  HxAnnotation attribute(final String name, Class<?>[] value);

  HxAnnotation attribute(final String name, HxType[] value);

  HxAnnotation attribute(final String name, HxAnnotation[] value);

  HxAnnotation attribute(final String name, HxConstants.EmptyArray[] value);
}
