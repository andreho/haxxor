package net.andreho.aop.spi.impl.matchers;

import net.andreho.aop.spi.AspectMatcher;
import net.andreho.haxxor.spec.api.HxType;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 01:34.
 */
public class ExtendingMatcher
    extends DisjunctionMatcher<HxType> {

  public ExtendingMatcher(final Collection<AspectMatcher<HxType>> collection) {
    super(collection);
  }

  public ExtendingMatcher(final AspectMatcher<HxType>[] array) {
    super(array);
  }

  @Override
  public boolean match(final HxType type) {
    HxType current = type;

    while (true) {
      if(super.match(current)) {
        return true;
      }
      if(!current.hasSuperType()) {
        break;
      }
      current = current.getSuperType().get();
    }

    return false;
  }

  @Override
  public String toString() {
    return "EXTENDING " + super.toString();
  }
}
