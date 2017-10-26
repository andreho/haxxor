package net.andreho.aop.spi.impl.matchers;

import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.api.HxType;

/**
 * <br/>Created by a.hofmann on 15.06.2017 at 01:42.
 */
public class ClassWithMatcher
    extends AbstractMatcher<HxType> {

  private final ElementMatcher<HxType> modifiers;
  private final ElementMatcher<HxType> value;
  private final ElementMatcher<HxType> named;
  private final ElementMatcher<HxType> annotated;

  public ClassWithMatcher(final ElementMatcher<HxType> modifiers,
                          final ElementMatcher<HxType> value,
                          final ElementMatcher<HxType> named,
                          final ElementMatcher<HxType> annotated) {
    this.modifiers = modifiers;
    this.value = value;
    this.named = named;
    this.annotated = annotated;
  }

  @Override
  public boolean matches(final HxType type) {
    return modifiers.matches(type) &&
           value.matches(type) &&
           named.matches(type) &&
           annotated.matches(type);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("WITH [");
    int added = 0;
    if(!modifiers.isAny()) {
      builder.append("modifiers: ").append(modifiers);
      added++;
    }

    if(!value.isAny()) {
      if(added > 0) {
        builder.append(", ");
      }
      builder.append("types: ").append(value);
      added++;
    }

    if(!named.isAny()) {
      if(added > 0) {
        builder.append(", ");
      }
      builder.append("named: ").append(named);
      added++;
    }

    if(!annotated.isAny()) {
      if(added > 0) {
        builder.append(", ");
      }
      builder.append("annotated: ").append(annotated);
      added++;
    }

    if(added == 0) {
      builder.append("ANY");
    }

    return builder.append("]").toString();
  }
}
