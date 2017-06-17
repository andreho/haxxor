package net.andreho.aop.impl.search;

import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 01:34.
 */
public class ExtendingFragment
    extends AbstractFragment<HxType> {

  private final AbstractFragment<HxType>[] extending;

  public ExtendingFragment(final AbstractFragment<HxType>... extending) {
    this.extending = extending;
  }

  @Override
  public boolean test(final HxType type) {
    final AbstractFragment<HxType>[] extending = this.extending;
    HxType current = type;
    while (true) {
      for(AbstractFragment<HxType> fragment : extending) {
        if(fragment.test(type)) {
          return true;
        }
      }
      if(!current.hasSuperType()) {
        break;
      }
      current = current.getSuperType().get();
    }
    return false;
  }
}
