package net.andreho.aop.spi.impl.matchers;

import net.andreho.aop.spi.AspectMatcher;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 15.06.2017 at 01:42.
 */
public class ClassWithMatcher
    extends AbstractMatcher<HxType> {

  private final AspectMatcher<HxType> modifiers;
  private final AspectMatcher<HxType> value;
  private final AspectMatcher<HxType> named;
  private final AspectMatcher<HxType> annotated;

  public ClassWithMatcher(final AspectMatcher<HxType> modifiers,
                          final AspectMatcher<HxType> value,
                          final AspectMatcher<HxType> named,
                          final AspectMatcher<HxType> annotated) {
    this.modifiers = modifiers;
    this.value = value;
    this.named = named;
    this.annotated = annotated;
  }

  @Override
  public boolean match(final HxType type) {
    return modifiers.match(type) &&
           value.match(type) &&
           named.match(type) &&
           annotated.match(type);
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
