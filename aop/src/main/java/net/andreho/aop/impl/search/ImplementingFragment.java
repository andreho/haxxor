package net.andreho.aop.impl.search;

import net.andreho.haxxor.spec.api.HxType;

import java.util.Collection;
import java.util.List;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 01:34.
 */
public class ImplementingFragment
    extends AbstractFragment<HxType> {

  private final AbstractFragment<HxType>[] implementing;

  public ImplementingFragment(final Collection<AbstractFragment<HxType>> implementing) {
    this(implementing.toArray(new AbstractFragment[0]));
  }

  public ImplementingFragment(final AbstractFragment<HxType>... implementing) {
    this.implementing = implementing;
  }

  private boolean hasImplementing(final List<HxType> interfaces) {
    for(HxType itf : interfaces) {
      if(hasImplementing(itf)) {
        return true;
      }
    }

    for(HxType itf : interfaces) {
      if(test(itf)) {
        return true;
      }
    }

    return false;
  }

  private boolean hasImplementing(final HxType itf) {
    for(AbstractFragment<HxType> fragment : implementing) {
      if(fragment.test(itf)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean test(final HxType type) {
    HxType current = type;
    while (true) {
      if(hasImplementing(current.getInterfaces())) {
        return true;
      }
      if(!current.hasSuperType()) {
        break;
      }
      current = current.getSuperType().get();
    }
    return false;
  }
}
