package net.andreho.haxxor.spec.annotation;

import java.util.Objects;

/**
 * Created by a.hofmann on 25.05.2016.
 */
public class StringAnnotationEntry
    extends AbstractAnnotationEntry<String, String> {

  public StringAnnotationEntry(final String name, final String value) {
    super(name, Objects.requireNonNull(value, "Value is null."));
  }
}
