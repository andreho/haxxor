package net.andreho.haxxor.spec.api;

/**
 * <br/>Created by andreho on 4/4/16 at 10:42 PM.<br/>
 */
public interface HxAnnotationAttribute<V, T> extends Cloneable {

  /**
   * @return name of annotation's attribute
   */
  String getName();

  /**
   * @return value of annotation's attribute
   */
  V getValue();

  /**
   * @param value is new value of this attribute
   */
  void setValue(V value);

  /**
   * Transforms if needed the value of this attribute to the given type
   * @param type is the corresponding annotation's attribute-type
   * @return value that is compatible with attribute's type of
   * the referenced {@link java.lang.annotation.Annotation annotation}
   * @see HxEnum
   * @see HxType
   */
  T original(Class<?> type);

  /**
   * @param o
   * @return
   */
  boolean hasValue(Object o);

  /**
   * @return a new copy of this annotation's attribute
   */
  HxAnnotationAttribute<V, T> clone();
}
