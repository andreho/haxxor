package net.andreho.haxxor.model;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 02.06.2017 at 13:45.
 */
@AnnotationC("class")
public class OverAnnotatedSimpleBean
    extends @AnnotationC("superClass") MinimalBean
    implements @AnnotationC("interface") InterfaceA {

  @AnnotationC("field")
  private final String value;

  @AnnotationC("<init>")
  public OverAnnotatedSimpleBean(@AnnotationC("parameter") final String value) {
    this.value =
        (@AnnotationC("interface") String)
            Objects.requireNonNull(value);
  }

  @AnnotationC("getter")
  public String getValue() {
    return value;
  }

  @Override
  @AnnotationC("equals")
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    @AnnotationC("that")
    final OverAnnotatedSimpleBean that = (OverAnnotatedSimpleBean) o;

    return value != null ? value.equals(that.value) : that.value == null;
  }

  @Override
  @AnnotationC("hashCode")
  public int hashCode() {
    return value != null ? value.hashCode() : 0;
  }

  @Override
  @AnnotationC("toString")
  public String toString() {
    return "OverAnnotatedSimpleBean{" +
           "value='" + value + '\'' +
           '}';
  }
}
