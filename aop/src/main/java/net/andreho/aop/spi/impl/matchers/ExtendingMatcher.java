package net.andreho.aop.spi.impl.matchers;

import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.api.HxType;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 01:34.
 */
public class ExtendingMatcher
    extends DisjunctionMatcher<HxType> {

  public ExtendingMatcher(final Collection<ElementMatcher<HxType>> collection) {
    super(collection);
  }

  public ExtendingMatcher(final ElementMatcher<HxType>[] array) {
    super(array);
  }

  @Override
  public boolean matches(final HxType type) {
    HxType current = type;

    while (true) {
      if(super.matches(current)) {
        return true;
      }
      if(!current.hasSupertype()) {
        break;
      }
      current = current.getSupertype().get();
    }

    return false;
  }

  @Override
  public String toString() {
    return "EXTENDING " + super.toString();
  }
}
