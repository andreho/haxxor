package net.andreho.aop.spi.impl.matchers;

import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.api.HxType;

/**
 * <br/>Created by a.hofmann on 15.06.2017 at 01:42.
 */
public class ClassesMatcher
    extends AbstractMatcher<HxType> {

  private final ElementMatcher<HxType> value;
  private final ElementMatcher<HxType> extending;
  private final ElementMatcher<HxType> implementing;

  public ClassesMatcher(final ElementMatcher<HxType> value,
                        final ElementMatcher<HxType> extending,
                        final ElementMatcher<HxType> implementing) {
    this.value = value;
    this.extending = extending;
    this.implementing = implementing;
  }

  @Override
  public boolean matches(final HxType type) {
    return value.matches(type) &&
           extending.matches(type) &&
           implementing.matches(type);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("CLASSES [");

    int added = 0;
    if(!value.isAny()) {
      builder.append("types: ").append(value);
      added++;
    }

    if(!value.isAny()) {
      if(added > 0) {
        builder.append(", ");
      }
      builder.append("extending: ").append(extending);
      added++;
    }

    if(!implementing.isAny()) {
      if(added > 0) {
        builder.append(", ");
      }
      builder.append("implementing: ").append(extending);
      added++;
    }

    if(added == 0) {
      builder.append("ANY");
    }

    return builder.append("]").toString();
  }
}
