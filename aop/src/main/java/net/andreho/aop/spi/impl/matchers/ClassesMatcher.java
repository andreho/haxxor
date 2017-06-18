package net.andreho.aop.spi.impl.matchers;

import net.andreho.aop.spi.AspectMatcher;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 15.06.2017 at 01:42.
 */
public class ClassesMatcher
    extends AbstractMatcher<HxType> {

  private final AspectMatcher<HxType> value;
  private final AspectMatcher<HxType> extending;
  private final AspectMatcher<HxType> implementing;

  public ClassesMatcher(final AspectMatcher<HxType> value,
                        final AspectMatcher<HxType> extending,
                        final AspectMatcher<HxType> implementing) {
    this.value = value;
    this.extending = extending;
    this.implementing = implementing;
  }

  @Override
  public boolean match(final HxType type) {
    return value.match(type) &&
           extending.match(type) &&
           implementing.match(type);
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
