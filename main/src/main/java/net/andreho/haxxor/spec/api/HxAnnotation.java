package net.andreho.haxxor.spec.api;

import net.andreho.haxxor.Haxxor;

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
            Cloneable {

  /**
   * @return
   */
  @Override
  default Haxxor getHaxxor() {
    return getType().getHaxxor();
  }

//  /**
//   * Creates and stores internally a proxy view of this annotation. Before usage,
//   * ensure that all associated classes are present and
//   * could be loaded by associated class-loader if necessary.<br/>
//   *
//   * @param <A>
//   * @return
//   */
//  default <A extends Annotation> A getView() {
//    return getView(getType().getHaxxor()
//                            .getClassLoader());
//  }

//  /**
//   * Creates and stores internally a proxy view of this annotation. Before usage,
//   * ensure that all associated classes are present and
//   * could be loaded by given class-loader if necessary.<br/>
//   *
//   * @param classLoader
//   * @param <A>
//   * @return
//   */
//  <A extends Annotation> A getView(ClassLoader classLoader);

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
   * @return map with annotation entries
   */
  Map<String, HxAnnotationAttribute<?, ?>> getValues();

  /**
   * @return a new copy instance of this one
   */
  HxAnnotation clone();

  /**
   * Retrieves annotations value by name
   *
   * @param name of attribute
   * @param <V>
   * @return value of the attribute
   * @implNote this method can fetch the referenced annotation in order to get default values for not defined
   * attributes with default values
   */
  <V> V attribute(String name);

  //----------------------------------------------------------------------------------------------------------------

  HxAnnotation attribute(final String name,
                         boolean value);

  HxAnnotation attribute(final String name,
                         byte value);

  HxAnnotation attribute(final String name,
                         char value);

  HxAnnotation attribute(final String name,
                         short value);

  HxAnnotation attribute(final String name,
                         int value);

  HxAnnotation attribute(final String name,
                         float value);

  HxAnnotation attribute(final String name,
                         long value);

  HxAnnotation attribute(final String name,
                         double value);

  HxAnnotation attribute(final String name,
                         String value);

  <E extends Enum<E>> HxAnnotation attribute(final String name,
                                             E value);

  HxAnnotation attribute(final String name,
                         HxEnum value);

  HxAnnotation attribute(final String name,
                         Class<?> value);

  HxAnnotation attribute(final String name,
                         HxType value);

  HxAnnotation attribute(final String name,
                         HxAnnotation value);

  HxAnnotation attribute(final String name,
                         boolean[] value);

  HxAnnotation attribute(final String name,
                         byte[] value);

  HxAnnotation attribute(final String name,
                         char[] value);

  HxAnnotation attribute(final String name,
                         short[] value);

  HxAnnotation attribute(final String name,
                         int[] value);

  HxAnnotation attribute(final String name,
                         float[] value);

  HxAnnotation attribute(final String name,
                         long[] value);

  HxAnnotation attribute(final String name,
                         double[] value);

  HxAnnotation attribute(final String name,
                         String[] value);

  HxAnnotation attribute(final String name,
                         HxEnum[] value);

  <E extends Enum<E>> HxAnnotation attribute(final String name,
                                             E[] value);

  HxAnnotation attribute(final String name,
                         Class<?>[] value);

  HxAnnotation attribute(final String name,
                         HxType[] value);

  HxAnnotation attribute(final String name,
                         HxAnnotation[] value);
}
