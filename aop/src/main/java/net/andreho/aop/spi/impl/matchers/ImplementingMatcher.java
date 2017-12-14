package net.andreho.aop.spi.impl.matchers;

import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.api.HxType;

import java.util.Collection;
import java.util.List;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 01:34.
 */
public class ImplementingMatcher
    extends DisjunctionMatcher<HxType> {

  public ImplementingMatcher(final Collection<ElementMatcher<HxType>> collection) {
    super(collection);
  }

  public ImplementingMatcher(final ElementMatcher<HxType>[] array) {
    super(array);
  }

  private boolean hasImplementing(final List<HxType> interfaces) {
    for(HxType itf : interfaces) {
      if(hasImplementing(itf)) {
        return true;
      }
    }

    for(HxType itf : interfaces) {
      if(matches(itf)) {
        return true;
      }
    }

    return false;
  }

  private boolean hasImplementing(final HxType itf) {
    for(ElementMatcher<HxType> fragment : array) {
      if(fragment.matches(itf)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean matches(final HxType type) {
    HxType current = type;
    while (true) {
      if(hasImplementing(current.getInterfaces())) {
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
    return "IMPLEMENTING " + super.toString();
  }
}
