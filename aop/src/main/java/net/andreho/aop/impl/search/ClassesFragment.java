package net.andreho.aop.impl.search;

import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 15.06.2017 at 01:42.
 */
public class ClassesFragment
    extends AbstractFragment<HxType> {

  private final AbstractFragment<HxType> value;
  private final AbstractFragment<HxType> extending;
  private final AbstractFragment<HxType> implementing;

  public ClassesFragment(final AbstractFragment<HxType> value,
                         final AbstractFragment<HxType> extending,
                         final AbstractFragment<HxType> implementing) {
    this.value = value;
    this.extending = extending;
    this.implementing = implementing;
  }

  @Override
  public boolean test(final HxType type) {
    return value.test(type) &&
           extending.test(type) &&
           implementing.test(type);
  }
}
