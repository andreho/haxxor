package net.andreho.haxxor.spec.impl.annotation;

import net.andreho.haxxor.spec.api.HxAnnotationAttribute;

import java.util.Objects;

/**
 * Created by a.hofmann on 21.05.2016.
 */
public abstract class AbstractAnnotationAttribute<V, T>
    implements HxAnnotationAttribute<V, T> {

  private String name;
  private V value;

  public AbstractAnnotationAttribute(final String name,
                                     final V value) {
    this.name = Objects.requireNonNull(name, "Attribute's name can't be null.");
    setValue(value);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public V getValue() {
    return value;
  }

  @Override
  public void setValue(final V value) {
    this.value = Objects.requireNonNull(value, "None of annotation's entries can be assigned to null.");
  }

  @Override
  public T original(Class<?> type) {
    return (T) getValue();
  }

  @Override
  public abstract HxAnnotationAttribute<V, T> clone();

  @Override
  public String toString() {
    return getName() + ": " + getValue();
  }
}
