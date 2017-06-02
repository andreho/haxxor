package net.andreho.haxxor.model;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 02.06.2017 at 13:45.
 */
public class SimpleBean
    extends MinimalBean
    implements InterfaceA {

  private final String value;

  public SimpleBean(final String value) {
    this.value = Objects.requireNonNull(value);
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final SimpleBean that = (SimpleBean) o;

    return value != null ? value.equals(that.value) : that.value == null;
  }

  @Override
  public int hashCode() {
    return value != null ? value.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "OverAnnotatedSimpleBean{" +
           "value='" + value + '\'' +
           '}';
  }
}
